package com.nbcedu.function.elec.biz.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.biz.ECClasshourBiz;
import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.biz.ECSignBiz;
import com.nbcedu.function.elec.biz.ECTermBiz;
import com.nbcedu.function.elec.dao.ECCourseDao;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBizImpl;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.model.ECTerm;
import com.nbcedu.function.elec.vo.ECCourseVO;

/**
 * 课程管理
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
@Service
public class ECCourseBizImpl extends ElecBaseBizImpl<ECCourse> implements ECCourseBiz {
	private ECCourseDao ecCourseDao;

	@Resource
	private ECClasshourBiz eCClasshourBiz;
	@Resource
	private ECTermBiz ecTermBiz;
	@Resource(name="elecSignBiz")
	private ECSignBiz elecSignBiz;
	@Resource
	public void setTestDao(ECCourseDao ecCourseDao) {
		this.ecCourseDao = ecCourseDao;
		super.setElecHibernateBaseDao(ecCourseDao);
	}
	
	/**
	 * 条件分页，page为null时，不分页
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ECCourseVO> findPage(ECCourseVO ecCourseVO, Page page) {
		StringBuilder sql = new StringBuilder("select distinct " );
									sql.append(" ec.id as id, ");
									sql.append(" ec.term_id as termId, ");
									sql.append(" ec.type_id as typeId, ");
									sql.append(" ec.gradeIds as gradeIds, ");
									sql.append(" ec.gradeNames as gradeNames, ");
									sql.append(" ec.name as name, ");
									sql.append(" ec.maxStudentNum as maxStudentNum, ");
									sql.append(" ec.teacherNames as teacherNames, ");
									sql.append(" ec.class_startDate as classStartDate, ");
									sql.append(" ec.class_endDate as classEndDate, ");
									sql.append(" ec.start_palce as startPlaceId, ");
									sql.append(" sp.name as startPlaceName, ");
									sql.append(" ec.club_state as clubState, ");
									sql.append(" ec.sign_startDate as signStartDate, ");
									sql.append(" ec.teacherIds as teacherIds, ");
									sql.append(" ec.sign_endDate as signEndDate, ");
									sql.append(" ec.classhourRequire as classhourRequire ");
									
		StringBuilder countSql = new StringBuilder("select count(distinct ec.id) ");
		
		StringBuilder fromSql = new StringBuilder(" from t_elec_course ec left join t_elec_place sp on ec.start_palce=sp.id ");
		if(ecCourseVO != null){
			if(ecCourseVO.getWeekIndex() != null){
				fromSql.append(" left join t_elec_classhour ch on ec.id=ch.course_id  ");
			}
		}
		
		StringBuilder whereSql = new StringBuilder(" where 1=1 ");
		
		List<Object> cond = new ArrayList<Object>();
		if(ecCourseVO!=null){
			if(!StringUtils.isBlank(ecCourseVO.getId())){
				whereSql.append(" and ec.id!=? ");
				cond.add(ecCourseVO.getId());
			}
			if(!StringUtils.isBlank(ecCourseVO.getTermId())){
				whereSql.append(" and ec.term_id=? ");
				cond.add(ecCourseVO.getTermId());
			}
			if(!StringUtils.isBlank(ecCourseVO.getTypeId())){
				whereSql.append(" and ec.type_id=? ");
				cond.add(ecCourseVO.getTypeId());
			}
			if(ecCourseVO.getClubState() != null){
				whereSql.append(" and ec.club_state=? ");
				cond.add(ecCourseVO.getClubState());
			}
			if(ecCourseVO.getWeekIndex() != null){
				whereSql.append(" and ch.weekIndex=? ");
				cond.add(ecCourseVO.getWeekIndex());
			}
			if(ecCourseVO.getCondGradeIds()!=null && ecCourseVO.getCondGradeIds().length>0){
				whereSql.append(" and ( ");
				if(ecCourseVO.getCondGradeIds()!=null && ecCourseVO.getCondGradeIds().length>0){
					for(String gradeId : ecCourseVO.getCondGradeIds()){
						whereSql.append(" (FIND_IN_SET(?, ec.gradeIds) != 0) or");
						cond.add(gradeId);
					}
				}
				whereSql.delete(whereSql.length()-2, whereSql.length());
				whereSql.append(" )");
			}
			if((ecCourseVO.getTeacherIdsArr()!=null && ecCourseVO.getTeacherIdsArr().length>0) ||
				(!StringUtils.isBlank(ecCourseVO.getStartPlaceId()))){
				whereSql.append(" and ( ");
				
				if(ecCourseVO.getTeacherIdsArr()!=null && ecCourseVO.getTeacherIdsArr().length>0){
					for(String teacherId : ecCourseVO.getTeacherIdsArr()){
						whereSql.append(" (FIND_IN_SET(?, ec.teacherIds) != 0) or");
						cond.add(teacherId);
					}
				}
				if(!StringUtils.isBlank(ecCourseVO.getStartPlaceId())){
					whereSql.append(" (ec.start_palce = ?) or");
					cond.add(ecCourseVO.getStartPlaceId());
				}
				
				whereSql.delete(whereSql.length()-2, whereSql.length());
				whereSql.append(" )");
			}
			if(!StringUtils.isBlank(ecCourseVO.getName())){
				whereSql.append(" and ec.name like ? ");
				cond.add("%"+ecCourseVO.getName()+"%");
			}
		}
		StringBuilder orderSql = new StringBuilder(" order by ec.operatorDate DESC ");
		
		List<Object[]> datas = null;
		
		if(page == null){
			datas = ecCourseDao.findBySql(sql.append(fromSql).append(whereSql).append(orderSql).toString(), 
					cond.toArray());
		} else {
			ecCourseDao.findPageBySQL(page, 
					sql.append(fromSql).append(whereSql).append(orderSql).toString(), 
					countSql.append(fromSql).append(whereSql).append(orderSql).toString(), 
					cond.toArray());
		}
		
		if(page != null){
			datas = (List<Object[]>) page.getDatas();
		}
		
		List<ECCourseVO> resultList = new ArrayList<ECCourseVO>();
		if(datas!=null && datas.size()>0){
			ECCourseVO vo = null;
			for(Object[] arr_elem : datas){
				vo = new ECCourseVO(
						(String)arr_elem[0],
						(String)arr_elem[1],
						(String)arr_elem[2],
						(String)arr_elem[3],
						(String)arr_elem[4],
						(String)arr_elem[5],
						(Integer)arr_elem[6],
						(String)arr_elem[7],
						(Date)arr_elem[8],
						(Date)arr_elem[9],
						(String)arr_elem[10],
						(String)arr_elem[11],
						(Integer)arr_elem[12],
						(Date)arr_elem[13],
		                (String)arr_elem[14],
		                (Date)arr_elem[15],
		                (Integer)arr_elem[16]
					);
				
				//课时
				vo.setClasshourList(eCClasshourBiz.findBy(vo.getId()));
				
				//已选人数
				vo.setCurrentStudentNum(elecSignBiz.findByCourseId(vo.getId()).size());
				
				resultList.add(vo);
			}
		}
		
		if(page != null){
			page.setDatas(resultList);
		}
		return resultList;
	}
	
	/**
	 * 根据课程id，查看
	 * @return
	 */
	@Override
	public ECCourseVO getCourseById(String id){
		ECCourse po = this.findById(id);
		ECCourseVO vo = new ECCourseVO();
		BeanUtils.copyProperties(po, vo);
		//已选人数
		vo.setCurrentStudentNum(elecSignBiz.findByCourseId(vo.getId()).size());
		//是否出现：编辑、删除
		ECTerm ecTerm = null;
		ECTerm currentTerm = ecTermBiz.findCurrentTerm();
		String currentTermId = currentTerm.getId();
		if(!currentTermId.equals(vo.getTermId())){
			ecTerm = ecTermBiz.findById(vo.getTermId());
		}else{
			ecTerm = currentTerm;
		}
		vo.setEcTerm(ecTerm);
		vo.setEdit(true);
		if(ecTerm.getOpenDateStart().getTime() <= new Date().getTime() || 
				!vo.getTermId().equals(currentTermId)){
			vo.setEdit(false);
		}
		
		return vo;
	}
	
	
	/**
	 * 校验课程名重复. true-重复 false-不重复
	 * @return
	 */
	@Override
	public boolean checkName(String name, String termId, String... courseID) {
		List<Object> cond = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("from ECCourse ec where ec.name=? and ec.termId=? ");
		cond.add(name);
		cond.add(termId);
		
		if(courseID!=null && courseID.length>0){
			sb.append(" and ec.id!=? ");
			cond.add(courseID[0]);
		}
		
		return ecCourseDao.countHql(sb.toString(), cond.toArray()) > 0;
	}

	@Override
	public int findCountByTerm(final String termId) {
		return (Integer)this.ecCourseDao.find(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public List doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cri = session.createCriteria(ECCourse.class);
				cri.add(Restrictions.eq("termId", termId));
				cri.setProjection(Projections.count("id"));
				Integer result = (Integer) cri.uniqueResult();
				return Arrays.asList(result==null?0:result);
			}
		}).get(0);
	}
	/**
	 * 删除课程
	 */
	@Override
	public void removeALL(Collection<String> ids) {
		for (String string : ids) {
			this.removeById(string);
		}
	}
	/**
	 * 删除课程里添加的学生
	 */
	@Override
	public void removeById(Serializable id) {
		super.removeById(id);
		this.elecSignBiz.removeByCourseId(id.toString());
	}
}

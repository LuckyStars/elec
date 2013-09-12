package com.nbcedu.function.elec.biz.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nbcedu.function.elec.biz.ECClasshourBiz;
import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.dao.ECClasshourDao;
import com.nbcedu.function.elec.dao.ECSignDao;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBizImpl;
import com.nbcedu.function.elec.model.ECClasshour;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.model.ECSign;
import com.nbcedu.function.elec.util.DateUtil;
import com.nbcedu.function.elec.util.WeekType;
import com.nbcedu.function.elec.vo.ECClasshourVO;
import com.nbcedu.function.elec.vo.ECCourseVO;

/**
 * 课时
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
@Service
public class ECClasshourBizImpl extends ElecBaseBizImpl<ECClasshour> implements ECClasshourBiz {
	private ECClasshourDao ecClasshourDao;

	@Resource
	private ECCourseBiz ecCourseBiz;
	@Resource(name="elecSignDao")
	private ECSignDao elecSignDao;
	@Resource
	public void setTestDao(ECClasshourDao ecClasshourDao) {
		this.ecClasshourDao = ecClasshourDao;
		super.setElecHibernateBaseDao(ecClasshourDao);
	}
	
	/**
	 * （只适用于史家）
	 * 1、判断课时人数是否达到上限；<br/>
	 * 2、校验老师后台录入学生报名记录冲突; <br/>
	 * @return 返回数据库中导致冲突的报名记录，没有返回null
	 */
	public ECSign validateConflict(String stuId, String courseId, List<String> pageClasshourIdList){
		List<ECSign> dbSignList = elecSignDao.findByStudentId(stuId);
		if(dbSignList!=null && dbSignList.size()>0){
			ECCourse pageCourse = ecCourseBiz.findById(courseId);
			
			List<ECClasshour> pageSelList = new ArrayList<ECClasshour>(); //页面选中课时
			for(ECClasshour ch : pageCourse.getClasshourList()){
				if(pageClasshourIdList.contains(ch.getId())){
					pageSelList.add(ch);
				}
			}
			
			ECCourse dbCourse = null;
			List<ECClasshour> dbChList = null;
			List<ECClasshour> dbSelList = new ArrayList<ECClasshour>(); //db选中课时
			for(ECSign dbSign : dbSignList){
				dbSelList.clear();
				
				dbCourse = ecCourseBiz.findById(dbSign.getCourseId());
				
				List<String> dbChidList = Collections.emptyList();
				if(StringUtils.isNotBlank(dbSign.getClasshourIds())){
					dbChidList = Arrays.asList(dbSign.getClasshourIds().split(",")); //
				}
				
				//1、判断课时人数是否达到上限
				for(int i = 0; i < pageSelList.size(); i++){
					double chMaxNum = Math.floor(pageCourse.getMaxStudentNum()*pageCourse.getClasshourRequire()/pageCourse.getClasshourNum()); //要求的最大课时人数
					int dbCurrentNum = elecSignDao.getcurrentChNum(pageSelList.get(i).getId());
					if(dbCurrentNum >= chMaxNum){
						return dbSign;
					}
				}
				
				//2、若开课日期存在冲突时，才需要判断
				if((pageCourse.getClassEndDate().getTime() > dbCourse.getClassStartDate().getTime()) &&
				   (pageCourse.getClassStartDate().getTime() < dbCourse.getClassEndDate().getTime())){
					
					dbChList = dbCourse.getClasshourList();
					for(ECClasshour ch : dbChList){
						if(dbChidList.contains(ch.getId())){
							dbSelList.add(ch);
						}
					}
					
					for(int i = 0; i < pageSelList.size(); i++){
						for(int j = 0; j < dbSelList.size(); j++){
							if(checkClasshourConflick(pageSelList.get(i), dbSelList.get(j))){
								return dbSign; 
							}
						}
					}
		        }
				
			}
		}
		
		return null;
	}
	
	
	/**
	 * 根据课程查询课时列表
	 * @param courseId
	 * @return
	 */
	@Override
	public List<ECClasshour> findBy(String courseId) {
		ECCourse ec = new ECCourse();
		ec.setId(courseId);
		return ecClasshourDao.find(" from ECClasshour ch where ch.eCCourse=? ", ec);
	}
	
	
	/**
	 * 校验课时： <br/>
	 * 1、一门课：同时选周一时，时间不能冲突<br/>
	 * 2、任课老师：同一时间，只能任教一门课 <br/>
	 * 3、上课地点：同一时间，不能重复
	 */
	public Map<String, Object> checkClassHour(String teacherIds, String teacherNames, String startPlaceId, String termId,
			String classStartDateStr, String classEndDateStr, String[] weekIndexs, String[] weekStartTimes, String[] weekEndTimes, String courseID)
			throws Exception {
		Date pageClassStartDate = DateUtil.str2Date(classStartDateStr, "yyyy-MM-dd");
		Date pageClassEndDate = DateUtil.str2Date(classEndDateStr, "yyyy-MM-dd");
		
		String[] teacherIdArr = teacherIds.split(","); 
		String[] teacherNameArr = teacherNames.split(","); 
		
		Map<String, String> pageTeacherMap = new HashMap<String, String>();
		for(int i=0; i<teacherIdArr.length; i++){
			pageTeacherMap.put(teacherIdArr[i], teacherNameArr[i].trim());
		}
		
		List<ECClasshourVO> pageChList = new ArrayList<ECClasshourVO>();
		ECClasshourVO ch = null;
		for(int i=0; i<weekIndexs.length; i++){
			String weekIndex = weekIndexs[i];
			String weekStartTime = weekStartTimes[i];
			String weekEndTime = weekEndTimes[i];
			
			ch = new ECClasshourVO(i+1, 
						Integer.parseInt(weekIndex), 
						DateUtil.str2Date(weekStartTime, "HH:mm:ss"),
						DateUtil.str2Date(weekEndTime, "HH:mm:ss"));
			
			pageChList.add(ch);
		}
		Collections.sort(pageChList, new Comparator<ECClasshourVO>() {
			@Override
			public int compare(ECClasshourVO o1, ECClasshourVO o2) {
				return o1.getWeekIndex() - o2.getWeekIndex();
			}
		});
		
		
		Map<String, Object> resultMap = buildResultMap(1, null, null); //保存验证结果
		
		//1、一门课：同时选周一时，时间不能冲突
		HashMap<Integer, List<ECClasshourVO>> map = new HashMap<Integer, List<ECClasshourVO>>();
		for(ECClasshourVO vo : pageChList){
			List<ECClasshourVO> list = map.get(vo.getWeekIndex());
			if(list == null){
				list = new ArrayList<ECClasshourVO>();
			}
			list.add(vo);
			map.put(vo.getWeekIndex(), list);
		}
		Set<Integer> keys = map.keySet();
		for(Integer key : keys){
			List<ECClasshourVO> list = map.get(key);
			if(list.size() > 0){
				for(int i = 0; i < list.size(); i++){
				    for(int j = i+1; j < list.size(); j++){
				        if(checkClasshourConflick(vo2po(list.get(i)), vo2po(list.get(j)))){
				        	resultMap = buildResultMap(0, list.get(i).getPxOrder(), WeekType.getById(list.get(i).getWeekIndex()).getName() +"，上课时间冲突！");
				        	return resultMap;
						}
				    }
				}
			}
		}
		
		//2、3 公用
		ECCourseVO vo = new ECCourseVO();
		vo.setTermId(termId);
		vo.setTeacherIdsArr(teacherIdArr);
		vo.setStartPlaceId(startPlaceId);
		if(!StringUtils.isBlank(courseID)){
			vo.setId(courseID);
		}
		List<ECCourseVO> ecVoList = ecCourseBiz.findPage(vo, null);
		
		//2、任课老师：同一时间，只能任教一门课
		HashMap<String, Set<ECCourseVO>> dbTeacherMap = new HashMap<String, Set<ECCourseVO>>();
		for(String teacherId : teacherIdArr){
			dbTeacherMap.put(teacherId, new LinkedHashSet<ECCourseVO>());
		}
		
		if(ecVoList!=null && ecVoList.size()>0){
			for(ECCourseVO ecVO : ecVoList){
				String[] dbTeacherIdArr = ecVO.getTeacherIds().split(",");
				Set<ECCourseVO> set = null;
				for(String teacherId : dbTeacherIdArr){
					set = dbTeacherMap.get(teacherId);
					
					if(set != null){
						set.add(ecVO);
					}
				}
			}
		}
		
		Set<String> teaKeys = dbTeacherMap.keySet();
		for(String teacherId : teaKeys){
			Set<ECCourseVO> set = dbTeacherMap.get(teacherId);
			if(set.size() > 0){
				for(ECCourseVO dbvo : set){
					//若开课日期存在冲突时，才需要判断
					if((pageClassEndDate.getTime() > dbvo.getClassStartDate().getTime()) &&
					   (pageClassStartDate.getTime() < dbvo.getClassEndDate().getTime())){
						List<ECClasshour> dbChList = dbvo.getClasshourList();
						for(int i = 0; i < pageChList.size(); i++){
							for(int j = 0; j < dbChList.size(); j++){
						        if(checkClasshourConflick(vo2po(pageChList.get(i)), dbChList.get(j))){
						        	resultMap = buildResultMap(2, pageChList.get(i).getPxOrder(), "任课老师"+ pageTeacherMap.get(teacherId) +"，"
											+ WeekType.getById(pageChList.get(i).getWeekIndex()).getName() +"上课时间冲突！");
						        	return resultMap; 
						        }
							}
						}
			        }
				}
			}
		}
		
		
		//3、上课地点：同一时间，不能重复
		if(ecVoList!=null && ecVoList.size()>0){
			List<ECCourseVO> list = new ArrayList<ECCourseVO>();
			for(ECCourseVO ecVO : ecVoList){
				String dbStartPlaceId = ecVO.getStartPlaceId();
				if(dbStartPlaceId.equals(startPlaceId)){
					list.add(ecVO);
				}
			}
			
			if(list.size() > 0){
				for(ECCourseVO dbvo : list){
					//若开课日期存在冲突时，才需要判断
					if((pageClassEndDate.getTime() > dbvo.getClassStartDate().getTime()) &&
					   (pageClassStartDate.getTime() < dbvo.getClassEndDate().getTime())){
						List<ECClasshour> dbChList = dbvo.getClasshourList();
						for(int i = 0; i < pageChList.size(); i++){
							for(int j = 0; j < dbChList.size(); j++){
						        if(checkClasshourConflick(vo2po(pageChList.get(i)), dbChList.get(j))){
						        	resultMap = buildResultMap(2, 8, "上课地点已有课程占用！");
						        	return resultMap;
						        }
							}
						}
			        }
				}
			}
			
		}
		
		return resultMap;
	}
	
	/**
	 * 判断2个课时是否冲突
	 * @return true-冲突 false-不冲突
	 */
	private boolean checkClasshourConflick(ECClasshour src1, ECClasshour src2){
		return (src1.getWeekIndex().intValue() == src2.getWeekIndex().intValue() ) &&
			   (src1.getWeekEndTime().getTime() > src2.getWeekStartTime().getTime()) && 
			   (src1.getWeekStartTime().getTime() < src2.getWeekEndTime().getTime());
	}
	
	/**
	 * vo --> po
	 */
	private ECClasshour vo2po(ECClasshourVO vo){
		ECClasshour po = new ECClasshour();
		po.setWeekIndex(vo.getWeekIndex());
		po.setWeekStartTime(vo.getWeekStartTime());
		po.setWeekEndTime(vo.getWeekEndTime());
		return po;
	}
	
	/**
	 * 构建验证结果
	 * @return
	 */
	private Map<String, Object> buildResultMap(Integer success, Integer pxOrder, String msg){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", success); // 1- 成功 0-失败(不允许) 2-失败(允许)
		resultMap.put("pxOrder",pxOrder); // 序号,其中8代表地点
		resultMap.put("msg", msg); // 错误提示内容
		
		return resultMap;
	}
	
}

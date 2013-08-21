package com.nbcedu.function.elec.biz.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.biz.ECSignBiz;
import com.nbcedu.function.elec.dao.ECSignDao;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBizImpl;
import com.nbcedu.function.elec.model.ECSign;
import com.nbcedu.function.elec.util.DateUtil;
@Repository("elecSignBiz")
public class ECSignBizImpl extends ElecBaseBizImpl<ECSign> implements ECSignBiz{
	private static final Log log = LogFactory.getLog(ECSignBizImpl.class);
	private ECSignDao signDao;
	@SuppressWarnings("unchecked")
	@Override
	public List<ECSign> findByCourseId(final String courseId) {
		return (List<ECSign>) this.signDao.find(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cri = session.createCriteria(ECSign.class);
				cri.add(Restrictions.eq("courseId", courseId));
				return cri.list();
			}
		});
	}
	@Override
	public Page findPageByCourseId(Page page, String id) {
		String hql = "FROM ECSign s where s.courseId = ?";
		return this.signDao.findPage(page, hql, id);
	}
	
	@Override
	public void removeECSignTimer(final Long signCreateTime) {
		String messageTime=DateUtil.date2Str(new Date(),"HH:mm:ss");
		 try {
			 List<ECSign> ecSigns=this.signDao.find("FROM ECSign s where s.classhourIds is null and 0=? ",0);
			 if (ecSigns!=null&&ecSigns.size()>0) {
				 long nowDate=new Date().getTime();
				for (ECSign ecSign : ecSigns) {
					if (signCreateTime<=nowDate-ecSign.getCreateDate().getTime()) {
						signDao.removeById(ecSign.getId());
					}
				}
			}
			 log.info(messageTime+"测试定时器任务执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage()+messageTime+"执行失败!");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ECSign> findByTermId(final String termId) {
		return (List<ECSign>) this.signDao.find(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				StringBuffer hql = new StringBuffer(
						"FROM ECSign s WHERE s.courseId in (SELECT id FROM ECCourse c WHERE c.termId=:termId)");
				return session.createQuery(hql.toString()).setString("termId", termId).list();
			}
		});
	}
	
	@Override
	public void addAll(List<ECSign> signList) {
		for (ECSign ecSign : signList) {
			this.signDao.saveEle(ecSign);
		}
	}
	
	
	@Override
	public List<ECSign> findByStudentId(final String studentId) {
		return signDao.findByStudentId(studentId);
	}
	@Override
	public ECSign findByStudentIdAndCourseId(final String studentId,final String courseId) {
		return signDao.findUnique("FROM ECSign s WHERE s.stuId=? and s.courseId=?", studentId,courseId);
	}
	
	@Override
	public Long findByStudentIdAndTermId(final String studentId,final String termId) {
		return signDao.countHql("SELECT count(s.id) FROM ECSign s WHERE s.stuId=? and s.courseId in (SELECT id FROM ECCourse c WHERE c.termId=?)", studentId,termId);
	}
	@Override
	public Long findByClassDateCount(final String classDateId) {
		return signDao.countHql("SELECT count(s.id) FROM ECSign s WHERE classhourIds like ?", "%"+classDateId+"%");
	}
	@Override
	public String addSignPro(final ECSign ecSign) {
		String msg;
		try {
			msg=signDao.addSignPro(ecSign);
		} catch (Exception e) {
			msg="系统繁忙请稍后!";
			e.printStackTrace();
		}
		return msg;
	}
	@Override
	public boolean removeSign(String id) {
		try {
			this.signDao.removeById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean updateSign(String id) {
		ECSign ecSign=null;
		try {
			ECSign sign=this.findById(id);
			sign.setClasshourIds(null);
			ecSign = this.signDao.saveOrUpdateEle(sign);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return ecSign!=null&&ecSign.getId()!=null;
	}
	///////////////////////////
	/////GETTERS&SETTERS///////
	//////////////////////////
	@Autowired
	public void setSignDao(ECSignDao signDao) {
		this.signDao = signDao;
		this.setElecHibernateBaseDao(signDao);
	}
	@Override
	public void removeByCourseId(String id) {
		this.signDao.createQuery("DELETE FROM ECSign s WHERE s.courseId=?", id).executeUpdate();
	}
	
}

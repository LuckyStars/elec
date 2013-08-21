package com.nbcedu.function.elec.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.nbcedu.function.elec.dao.ECSignDao;
import com.nbcedu.function.elec.dao.ECTermDao;
import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDaoImpl;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.model.ECSign;
import com.nbcedu.function.elec.model.ECTerm;
@Repository("elecSignDao")
public class ECSignDaoImpl extends ElecHibernateBaseDaoImpl<ECSign> implements ECSignDao {
	@Resource(type=ECTermDaoImpl.class)
	private ECTermDao termDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ECSign> findByStudentId(final String uid) {
		final ECTerm curTerm =termDao.findCurrentTerm();
		if(curTerm==null){
			return Collections.emptyList();
		}
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cri = session.createCriteria(ECCourse.class);
				cri.add(Restrictions.eq("termId", curTerm.getId()));
				List<ECCourse> courseList = cri.list();
				if(courseList==null||courseList.isEmpty()){
					return Collections.emptyList();
				}
				List<String> courseIds = new ArrayList<String>();
				for (ECCourse course : courseList) {
					courseIds.add(course.getId());
				}
				Criteria criSign = session.createCriteria(ECSign.class);
				criSign.add(Restrictions.and(
							Restrictions.in("courseId",courseIds.toArray()),
							Restrictions.eq("stuId", uid)));
				
				return criSign.list();
			}
		});
	}

	@Override
	public Integer getcurrentChNum(final String classhourId) {
		return  (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cri = session.createCriteria(ECSign.class);
				cri.add(Restrictions.like("classhourIds", "%" +classhourId + "%"));
				cri.setProjection(Projections.count("id"));
				Integer count =  (Integer) cri.uniqueResult();
				return count == null?0:count;
			}
		});
	}

	@Override
	public String addSignPro(final ECSign ecSign) {
		return (String) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Connection connection=session.connection();
				 CallableStatement cstmt;
					cstmt=connection.prepareCall("{CALL addSign(?,?,?,?)}");
					cstmt.setString(1, ecSign.getStuId());
					cstmt.setString(2, ecSign.getCourseId());
					cstmt.setString(3, ecSign.getClasshourIds());
					cstmt.registerOutParameter(4, Types.VARCHAR);
					cstmt.executeQuery();
				return cstmt.getString(4);
			}
		});
	}
	
}

package com.nbcedu.function.elec.dao;

import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDao;
import com.nbcedu.function.elec.model.ECCourse;

/**
 * 课程管理
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
public interface ECCourseDao extends ElecHibernateBaseDao<ECCourse> {
	
	/**
	 * 判断当前学期指定地点是否使用. false-已占用 true-未占用
	 * @return
	 */
	public boolean checkPlaceUsed(String placeId);

}
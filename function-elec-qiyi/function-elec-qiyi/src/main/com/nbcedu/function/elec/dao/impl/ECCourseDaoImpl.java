package com.nbcedu.function.elec.dao.impl;

import org.springframework.stereotype.Repository;

import com.nbcedu.function.elec.dao.ECCourseDao;
import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDaoImpl;
import com.nbcedu.function.elec.model.ECCourse;

/**
 * 课程管理
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
@Repository
public class ECCourseDaoImpl extends ElecHibernateBaseDaoImpl<ECCourse> implements ECCourseDao {
	
	/**
	 * 判断当前学期指定地点是否使用. false-已占用 true-未占用
	 * @return
	 */
	@Override
	public boolean checkPlaceUsed(String placeId){
		return this.getCountSql("select count(ec.id) from t_elec_course ec left join t_elec_term et on ec.term_id = et.id where et.current_term=1 and (ec.start_palce=? or ec.end_place=?) ", 
				placeId, placeId) <= 0;
	}
	
	
}

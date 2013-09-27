package com.nbcedu.function.booksite.dao.impl;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.nbcedu.function.booksite.dao.ActivityLevelDao;
import com.nbcedu.function.booksite.model.ActivityLevel;

public class ActivityLevelDaoImpl extends HibernateDaoSupport implements ActivityLevelDao {
	@Override
	public void createLevel(ActivityLevel level) {
		getHibernateTemplate().save(level);
	}

	public void deleteLevel(String id){
		getHibernateTemplate().delete(getHibernateTemplate().get(ActivityLevel.class, id));
	}
	
	public void updateLevel(ActivityLevel level){
		getHibernateTemplate().update(level);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityLevel> retrieveAllLevel() {
		return getHibernateTemplate().find("from ActivityLevel a where a.siteStatus.siteStatus_checkId in (1,2,4) order by a.siteStatus.siteStatus_checkId asc");
	}

	@Override
	public ActivityLevel retrieveById(String id) {
		return (ActivityLevel)getHibernateTemplate().get(ActivityLevel.class, id);
	}
	/**
	 * 根据状态查询级别
	 */
	@SuppressWarnings("unchecked")
	public List<ActivityLevel> retrieveAllLevelByStatusId(Integer id) {
		return getHibernateTemplate().find("from ActivityLevel a where a.siteStatus.siteStatus_checkId=" + id  + " order by a.siteStatus.siteStatus_checkId asc");
		
	}
}

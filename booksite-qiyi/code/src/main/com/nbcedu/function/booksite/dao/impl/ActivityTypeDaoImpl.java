package com.nbcedu.function.booksite.dao.impl;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.nbcedu.function.booksite.dao.ActivityTypeDao;
import com.nbcedu.function.booksite.model.ActivityType;

public class ActivityTypeDaoImpl extends HibernateDaoSupport implements ActivityTypeDao {
	
	@Override
	public void createActivityType(ActivityType type) {
		getHibernateTemplate().save(type);
	}
	
	@Override
	public void updateActivityType(ActivityType type) {
		getHibernateTemplate().update(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityType> retrieveActivityType() {
		return getHibernateTemplate().find("from ActivityType a where a.siteStatus.siteStatus_checkId in (1,2,4) order by a.siteStatus.siteStatus_checkId asc");
	}
	/**
	 * 删除
	 */
	@Override
	public void deleteEntity(String id) {
		getHibernateTemplate().delete(getHibernateTemplate().get(ActivityType.class, id));
	}
	@Override
	public ActivityType retrieveById(String id) {
		return (ActivityType) getHibernateTemplate().get(ActivityType.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityType> retrieveAllTypeByStatusId(Integer id) {
		return getHibernateTemplate().find("from ActivityType a where a.siteStatus.siteStatus_checkId=" + id + " order by a.siteStatus.siteStatus_checkId asc");
	}

}

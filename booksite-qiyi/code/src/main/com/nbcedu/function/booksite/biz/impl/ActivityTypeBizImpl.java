package com.nbcedu.function.booksite.biz.impl;

import java.util.List;

import com.nbcedu.function.booksite.biz.ActivityTypeBiz;
import com.nbcedu.function.booksite.dao.ActivityTypeDao;
import com.nbcedu.function.booksite.model.ActivityType;

public class ActivityTypeBizImpl implements ActivityTypeBiz {
	private ActivityTypeDao activityTypeDao;
	public void setActivityTypeDao(ActivityTypeDao activityTypeDao) {
		this.activityTypeDao = activityTypeDao;
	}
	@Override
	public void addActivityType(ActivityType type) {
		activityTypeDao.createActivityType(type);
	}
	/**
	 * 删除
	 */
	public void removeEntity(String id) {
		activityTypeDao.deleteEntity(id);
	}
	@Override
	public void modifyActivityType(ActivityType type) {
		activityTypeDao.updateActivityType(type);
	}

	@Override
	public List<ActivityType> findActivityType() {
		return activityTypeDao.retrieveActivityType();
	}

	@Override
	public ActivityType findById(String id) {
		return activityTypeDao.retrieveById(id);
	}

	@Override
	public List<ActivityType> findAllTypeByStatusId(Integer id) {
		return activityTypeDao.retrieveAllTypeByStatusId(id);
	}

}

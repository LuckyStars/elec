package com.nbcedu.function.booksite.biz.impl;

import java.util.List;

import com.nbcedu.function.booksite.biz.ActivityLevelBiz;
import com.nbcedu.function.booksite.dao.ActivityLevelDao;
import com.nbcedu.function.booksite.model.ActivityLevel;

public class ActivityLevelBizImpl implements ActivityLevelBiz {
	private ActivityLevelDao activityLevelDao;
	public void setActivityLevelDao(ActivityLevelDao activityLevelDao) {
		this.activityLevelDao = activityLevelDao;
	}
	@Override
	public void addLevel(ActivityLevel level) {
		activityLevelDao.createLevel(level);
	}
	@Override
	public void removeLevel(String id) {
		activityLevelDao.deleteLevel(id);
	}
	@Override
	public void modifyLevel(ActivityLevel level) {
		activityLevelDao.updateLevel(level);
	}
	@Override
	public List<ActivityLevel> findAllLevel() {
		return activityLevelDao.retrieveAllLevel();
	}
	@Override
	public ActivityLevel findById(String id) {
		return activityLevelDao.retrieveById(id);
	}
	@Override
	public List<ActivityLevel> findAllLevelByStatusId(Integer id) {
		return activityLevelDao.retrieveAllLevelByStatusId(id);
	}
}

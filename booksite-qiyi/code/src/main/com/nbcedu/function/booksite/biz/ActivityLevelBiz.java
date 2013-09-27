package com.nbcedu.function.booksite.biz;

import java.util.List;

import com.nbcedu.function.booksite.model.ActivityLevel;

public interface ActivityLevelBiz {

	/**
	 * 增加
	 * @param level
	 */
	public void addLevel(ActivityLevel level);
	/**
	 * 删除
	 */
	public void removeLevel(String id);
	/**
	 * 更新
	 */
	public void modifyLevel(ActivityLevel level);
	/**
	 * 获取所有
	 */
	public List<ActivityLevel> findAllLevel();
	/**
	 * 根据id查询
	 */
	public ActivityLevel findById(String id);
	/**
	 * 根据状态查询级别
	 */
	public List<ActivityLevel> findAllLevelByStatusId(Integer id);
}

package com.nbcedu.function.booksite.dao;

import java.util.List;

import com.nbcedu.function.booksite.model.ActivityLevel;

public interface ActivityLevelDao {

	/**
	 * 增加
	 * @param level
	 */
	public void createLevel(ActivityLevel level);
	/**
	 * 删除
	 */
	public void deleteLevel(String id);
	/**
	 * 更新
	 */
	public void updateLevel(ActivityLevel level);
	/**
	 * 查询所有级别
	 */
	public List<ActivityLevel> retrieveAllLevel();
	/**
	 * 根据id查询
	 */
	public ActivityLevel retrieveById(String id);
	/**
	 * 根据状态查询级别
	 */
	public List<ActivityLevel> retrieveAllLevelByStatusId(Integer id);
}

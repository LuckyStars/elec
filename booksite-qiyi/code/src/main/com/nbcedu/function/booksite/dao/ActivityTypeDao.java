package com.nbcedu.function.booksite.dao;

import java.util.List;
import com.nbcedu.function.booksite.model.ActivityType;

public interface ActivityTypeDao {

	/**
	 * 增加
	 * @param type
	 */
	public void createActivityType(ActivityType type);
	/**
	 * 删除
	 */
	public void deleteEntity(String id);
	/**
	 * update
	 */
	public void updateActivityType(ActivityType type);
	/**
	 * 查询所有
	 */
	public List<ActivityType> retrieveActivityType();
	/**
	 * 根据id查询
	 */
	public ActivityType retrieveById(String id);
	/**
	 * 根据状态查询类型
	 */
	public List<ActivityType> retrieveAllTypeByStatusId(Integer id);
}

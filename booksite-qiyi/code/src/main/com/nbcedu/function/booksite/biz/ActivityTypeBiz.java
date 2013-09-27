package com.nbcedu.function.booksite.biz;

import java.util.List;

import com.nbcedu.function.booksite.model.ActivityType;

public interface ActivityTypeBiz {
	
	/**
	 * 增加
	 * @param type
	 */
	public void addActivityType(ActivityType type);
	/**
	 * 删除
	 */
	public void removeEntity(String id);
	/**
	 * 更新
	 */
	public void modifyActivityType(ActivityType type);
	/**
	 * 查询所有
	 */
	public List<ActivityType> findActivityType();
	/**
	 * 根据id查询
	 */
	public ActivityType findById(String id);
	/**
	 * 根据状态查询类型
	 */
	public List<ActivityType> findAllTypeByStatusId(Integer id);
}

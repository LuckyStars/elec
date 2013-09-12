package com.nbcedu.function.elec.dao;

import java.util.List;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.model.ECPlace;

public interface ECPlaceDao {
	
	/**
	 * 查询所有地点
	 * @return
	 */
	public List<ECPlace> findAllPlace(Page page  );
	
	/**
	 * 按课程类型Id去查询地点（给秦愿预留的接口）
	 * @param typeId
	 * @return
	 */
	public List<ECPlace> findPlace(String typeId);
	
	/**
	 * 查询单个地点
	 * @param ecPlace
	 * @return
	 */
	public ECPlace findPlace(ECPlace ecPlace);
	

	/**
	 * 按照名字查找地点
	 * @param name
	 * @return
	 */
	public List<ECPlace> findByName(String name);
	
	/**
	 * 新增地点
	 * @param ecPlace
	 */
	public void addPlace(ECPlace ecPlace);
	
	/**
	 *  开启或关闭地点
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean modifyPlace(String id , Integer status);
	
	/**
	 * 编辑地点
	 * @param ecPlace
	 */
	public void updatePlace(ECPlace ecPlace);
	
}

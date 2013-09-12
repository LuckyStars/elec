package com.nbcedu.function.elec.biz;

import java.util.List;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBiz;
import com.nbcedu.function.elec.model.ECPlace;

public interface ECPlaceBiz extends ElecBaseBiz<ECPlace> {
	
	/**
	 * 查询所有地点
	 * @return
	 */
	public List<ECPlace> findAllPlace( Page page  );
	
	/**
	 * 按课程类别ID去查询所有地点  （给秦愿预留的接口）
	 * @param typeId
	 * @return
	 */
	public List<ECPlace> findAllPlace(String typeId );
	
	/**
	 * 查询单个地点
	 * @param ecPlace
	 * @return
	 */
	public ECPlace findPlace(ECPlace ecPlace);
	
	/**
	 * 新增地点
	 * @param ecPlace
	 * @return
	 */
	public void addPlace(ECPlace ecPlace);
	
	/**
	 * 编辑地点
	 * @param ecPlace
	 * @return
	 */
	public ECPlace updatePlace(ECPlace ecPlace);
	
	/**
	 * 判断地点名是否重复
	 * @param name
	 * @return
	 */
	public boolean checkPlaceName(ECPlace ep , String flag);
	
	/**
	 * 启用或关闭地点
	 * @param id
	 * @param flag
	 * @return
	 */
	public boolean modifyPlace(String id , String flag);
	
}

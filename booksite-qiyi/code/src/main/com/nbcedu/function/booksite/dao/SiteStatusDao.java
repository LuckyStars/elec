package com.nbcedu.function.booksite.dao;

import java.util.List;

import com.nbcedu.function.booksite.model.SiteStatus;

public interface SiteStatusDao {
	/**
	 * 增加
	 * @param siteStatus
	 */
	public void createSiteStatus(SiteStatus siteStatus);
	/**
	 * 更新
	 */
	public void updateSiteStatusById(SiteStatus siteStatus);
	/**
	 * 根据id查询
	 */
	public SiteStatus retrieveById(String id);
	/**
	 * 根据其他实体id查询状态
	 * @param id
	 * @return
	 */
	public SiteStatus retrieveForOtherById(Integer id);
	/**
	 * 查询所有状态
	 * @return
	 */
	public List<SiteStatus> retrieveAllSiteStatus();
}

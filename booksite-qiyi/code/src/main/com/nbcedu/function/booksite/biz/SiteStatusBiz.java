package com.nbcedu.function.booksite.biz;

import java.util.List;

import com.nbcedu.function.booksite.model.SiteStatus;

public interface SiteStatusBiz {
	/**
	 * add one siteStatus
	 * @param siteStatus
	 */
	public void addSiteStatus(SiteStatus siteStatus);
	/**
	 * update siteStatus
	 */
	public void modifySiteStatusById(SiteStatus siteStatus);
	/**
	 * get sitestatus by id for myself
	 */
	public SiteStatus findById(String id);
	/**
	 * get one  Object by id  for other object
	 * @param id
	 * @return
	 */
	public SiteStatus findForOtherById(Integer id);
	/**
	 * get all status
	 * @return
	 */
	public List<SiteStatus> findAllSiteStatus();
}

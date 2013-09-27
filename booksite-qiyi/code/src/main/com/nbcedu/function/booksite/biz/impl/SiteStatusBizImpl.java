package com.nbcedu.function.booksite.biz.impl;

import java.util.List;

import com.nbcedu.function.booksite.biz.SiteStatusBiz;
import com.nbcedu.function.booksite.dao.SiteStatusDao;
import com.nbcedu.function.booksite.model.SiteStatus;

public class SiteStatusBizImpl implements SiteStatusBiz {
	private SiteStatusDao siteStatusDao;
	public void setSiteStatusDao(SiteStatusDao siteStatusDao) {
		this.siteStatusDao = siteStatusDao;
	}

	@Override
	public void addSiteStatus(SiteStatus siteStatus) {
		siteStatusDao.createSiteStatus(siteStatus);
	}

	@Override
	public void modifySiteStatusById(SiteStatus siteStatus) {
		siteStatusDao.updateSiteStatusById(siteStatus);
	}

	@Override
	public SiteStatus findById(String id) {
		return siteStatusDao.retrieveById(id);
	}

	@Override
	public List<SiteStatus> findAllSiteStatus() {
		return siteStatusDao.retrieveAllSiteStatus();
	}

	@Override
	public SiteStatus findForOtherById(Integer id) {
		return siteStatusDao.retrieveForOtherById(id);
	}
}

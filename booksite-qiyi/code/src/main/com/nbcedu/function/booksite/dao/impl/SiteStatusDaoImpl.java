package com.nbcedu.function.booksite.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.nbcedu.function.booksite.dao.SiteStatusDao;
import com.nbcedu.function.booksite.model.SiteStatus;

public class SiteStatusDaoImpl extends HibernateDaoSupport implements SiteStatusDao  {
	
	@Override
	public void createSiteStatus(SiteStatus siteStatus) {
		getHibernateTemplate().merge(siteStatus);
	}

	@Override
	public void updateSiteStatusById(SiteStatus siteStatus) {
		getHibernateTemplate().update(siteStatus);		
	}

	@Override
	public SiteStatus retrieveById(String id) {
		
		return (SiteStatus) getHibernateTemplate().get(SiteStatus.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SiteStatus> retrieveAllSiteStatus() {	
		return getHibernateTemplate().find("from SiteStatus");
	}

	@SuppressWarnings("unchecked")
	@Override
	public SiteStatus retrieveForOtherById(Integer id) {
		List<SiteStatus> list = getHibernateTemplate().find("from SiteStatus s where s.siteStatus_checkId=" + id);
		if (list.size() != 0) {
			return (SiteStatus) getHibernateTemplate().find("from SiteStatus s where s.siteStatus_checkId=" + id).get(0);
		} else {
			return null;
		}
	}
}

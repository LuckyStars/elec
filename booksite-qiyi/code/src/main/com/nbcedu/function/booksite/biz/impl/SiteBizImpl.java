package com.nbcedu.function.booksite.biz.impl;

import java.util.List;

import com.nbcedu.function.booksite.biz.SiteBiz;
import com.nbcedu.function.booksite.dao.SiteDao;
import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.website.biz.impl.WsBaseBizImpl;
import com.nbcedu.function.booksite.website.pager.PagerModel;
import com.nbcedu.function.booksite.website.pager.SystemContext;

public class SiteBizImpl  extends WsBaseBizImpl<Site>  implements SiteBiz {
	private SiteDao siteDao;
	
	public void setSiteDao(SiteDao siteDao) {
		super.setDao(siteDao);
		this.siteDao = siteDao;
	}
	@Override
	public void addSite(Site site) {
		siteDao.createSite(site);
	}
	@Override
	public List<Site> findAllSite() {
		return siteDao.retrieveAllSite();
	}
	@Override
	public void modifySite(Site site) {
		siteDao.updateSite(site);
	}

	@Override
	public Site findSiteById(String id) {

		return siteDao.retrieveSiteById(id);
	}
	/**
	 * 根据状态id查询场馆
	 */
	public List<Site> findByStatusId(Integer id) {
		return siteDao.retrieveByStatusId(id);
	}
	/**
	 * 根据状态id场馆状态
	 */
	public List<Site> findByStatus(Integer id) {
		return siteDao.retrieveByStatus(id);
	}
	/**
	 * 查询开启场馆
	 */
	public List<Site> findOpenByStatusId(Integer id){
		return siteDao.retrieveOpenByStatusId(id);
	}
	@Override
	public List<Site> findAll(int start, int pageSize, Integer status_id) {
		return siteDao.retrieveAll(start, pageSize, status_id);
	}

	@Override
	public int[] findStartAndPageSize(int pageSize, Integer status_id) {
		return siteDao.retrieveStartAndPageSize(pageSize, status_id);
	}

	@Override
	public int findTotalPage(int start, int pageSize, Integer status_id) {
		return siteDao.retrieveTotalPage(start, pageSize, status_id);
	}

	@Override
	public int findTotalRecord(int start, int pageSize, Integer status_id) {
		return siteDao.retrieveTotalRecord(start, pageSize, status_id);
	}

	@Override
	public List<Site> findByLevel(String levelId) {
		return siteDao.retrieveByLevel(levelId);
	}

	@Override
	public List<Site> findByType(String typeId) {
		return siteDao.retrieveByType(typeId);
	}

	@Override
	public PagerModel findAllByPm() {
		return siteDao.searchPaginated("from Site s where s.siteStatus.siteStatus_checkId in (1,2,4) order by s.siteStatus.siteStatus_checkId asc",SystemContext.getOffset(),12);
	}

	@Override
	public PagerModel findAllByPmForStatusId(Integer id) {
		return siteDao.searchPaginated("from Site s where s.siteStatus.siteStatus_checkId = " + id + " order by s.siteStatus.siteStatus_checkId asc",SystemContext.getOffset(),12);
	}
	@Override
	public List<Site> findSiteByUserId(String userId) {
		// TODO Auto-generated method stub
		return siteDao.find("from Site s where s.siteController like '%"+userId+"%'");
	}

}

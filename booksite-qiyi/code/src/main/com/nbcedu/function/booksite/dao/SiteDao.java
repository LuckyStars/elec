package com.nbcedu.function.booksite.dao;

import java.util.List;

import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.website.dao.WsBaseDAO;

public interface SiteDao extends WsBaseDAO<Site>{

	
	/**
	 * 增加场馆
	 */
	public void createSite(Site site);
	/**
	 * 更新场馆
	 */
	public void updateSite(Site site);
	/**
	 * 获取所有场馆
	 * @return
	 */
	public List<Site> retrieveAllSite();
	/**
	 * 根据id查询场馆
	 */
	public Site retrieveSiteById(String id);
	/**
	 * 查询开启场馆
	 */
	public List<Site> retrieveOpenByStatusId(Integer id);
	/**
	 * 根据状态id查询场馆
	 */
	public List<Site> retrieveByStatusId(Integer id);
	public List<Site> retrieveByStatus(Integer id);
	
	/**
	 * 根据级别查询场馆
	 */
	public List<Site> retrieveByLevel(String levelId);
	/**
	 * 根据类型查询场馆
	 */
	public List<Site> retrieveByType(String typeId);
	
	/**
	 * 根据状态获取所有
	 */
	public List<Site> retrieveAll(final int start, final int pageSize,final Integer status_id);
	/**
	 * 总页数
	 */
	public int retrieveTotalPage(int start,int pageSize,Integer status_id);
	/**
	 * 总记录数
	 */
	public int retrieveTotalRecord(int start,int pageSize,Integer status_id);
	/**
	 * 进入分页方法
	 */
	public int[] retrieveStartAndPageSize(int pageSize,Integer status_id);
}

package com.nbcedu.function.booksite.biz;

import java.util.List;

import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.website.biz.WsBaseBiz;
import com.nbcedu.function.booksite.website.pager.PagerModel;

public interface SiteBiz extends WsBaseBiz<Site> {
	/**
	 * 查找所有场馆
	 * 
	 * @return
	 */
	public List<Site> findAllSite();

	/**
	 * 增加场馆
	 */
	public void addSite(Site site);

	/**
	 * 更新场馆
	 */
	public void modifySite(Site site);

	/**
	 * 根据id查询场馆
	 */
	public Site findSiteById(String id);

	/**
	 * 根据状态id查询场馆
	 */
	public List<Site> findByStatusId(Integer id);

	public List<Site> findByStatus(Integer id);

	/**
	 * 根据级别查询场馆
	 */
	public List<Site> findByLevel(String levelId);

	/**
	 * 根据类型查询场馆
	 */
	public List<Site> findByType(String typeId);

	/**
	 * 查询开启场馆
	 */
	public List<Site> findOpenByStatusId(Integer id);

	public PagerModel findAllByPmForStatusId(Integer id);

	/**
	 * 更具状态获取所有
	 */
	public List<Site> findAll(final int start, final int pageSize, final Integer status_id);

	/**
	 *  返回pm分页方法
	 * @return
	 */
	public PagerModel findAllByPm();
	
	/**
	 * 总页数
	 */
	public int findTotalPage(int start, int pageSize, Integer status_id);

	/**
	 * 总记录数
	 */
	public int findTotalRecord(int start, int pageSize, Integer status_id);

	/**
	 * 进入分页方法
	 */
	public int[] findStartAndPageSize(int pageSize, Integer status_id);
	/**
	 * 查场馆负责人
	 * @param userId
	 * @return
	 */
	public List<Site> findSiteByUserId(String userId);
	
}

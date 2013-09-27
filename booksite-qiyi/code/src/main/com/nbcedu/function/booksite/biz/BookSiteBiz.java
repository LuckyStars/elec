package com.nbcedu.function.booksite.biz;

import java.util.List;

import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.website.biz.WsBaseBiz;
import com.nbcedu.function.booksite.website.pager.PagerModel;
import com.nbcedu.function.booksite.website.exception.DBException;

public interface BookSiteBiz  extends WsBaseBiz<BookSite>{ 
	/**
	 * 增加预定信息
	 * @param bookSite
	 */
	public String addBookSite(BookSite bookSite);
	/**
	 * 删除
	 * @param id
	 */
	public void removeBookSite(String id);
	/**
	 * 更新预定信息
	 * @param bookSite
	 */
	public void modifyBookSite(BookSite bookSite);
	/**
	 * 
	 * 查询所有预定信息
	 */
	public List<BookSite> findAllBookSite();

	/**
	 * 根据id查询预定信息
	 */
	public BookSite findBookSiteById(String id);

	/**
	 * 查询某个场馆的所有预定信息
	 */
	public List<BookSite> findBookSiteInfoBySiteId(String id,Integer statusId);
	/**
	 * 根据条件查询预订信息
	 */
	public List<BookSite> findAll(String param);

	/**
	 * search booksite by statusid
	 * @param id
	 * @return
	 */
	public List<BookSite> findBookSiteByStatusId(Integer id);
	/**
	 * search booksite by beginTime and endTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<BookSite> findBookSiteByTime(String id, Integer statusId, String beginTime, String endTime);

	/**
	 * 根据状态获取所有
	 */
	public List<BookSite> findAll(final int start, final int pageSize, final Integer status_id);
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
	 * 返回PM分页方法
	 */
	public PagerModel findAllByPm();
	/**
	 * 返回PM分页方法,我的预定
	 */
	public PagerModel findAllByPmForAll(String statusId,String  userId, String beginTime,String  endTime,String  valueId, String typeId, String siteId,String  opId);
	/**
	 * 查看我的预定(根据userid查询)
	 */
	public List<BookSite> findBookSiteByUserId(String id,Integer statusId,String beginTime,String endTime, String levelId, String typeId, String siteId, String optionId);
	public List<BookSite> findAll(final int start, final int pageSize, final Integer status_id, final String user_id, final String beginTime, final String endTime, final String levelId, final String typeId, final String siteId, final String optionId);
	/**
	 * 总页数
	 */
	public int findTotalPage(int start,int pageSize, Integer status_id, String user_id, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId);
	/**
	 * 总记录数
	 */
	public int findTotalRecord(int start, int pageSize, Integer status_id, String user_id, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId);
	/**
	 * 进入分页方法
	 */
	public int[] findStartAndPageSize(int pageSize, Integer status_id, String user_id, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId);
	/**
	 * 根据场馆预定次数进行统计
	 */
	public int findBySiteCount(String siteId, Integer statusId, String beginTime, String endTime);
	/**
	 * 根据级别进行统计
	 */
	public int findByLevelCount(String levelId, Integer statusId, String beginTime, String endTime);
	/**
	 * 场馆统计
	 * @author 黎青春
	 * @param beginDate
	 * @param endDate
	 */
	@SuppressWarnings("unchecked")
	public List findSiteStatistics(String beginDate, String endDate) throws DBException;
	/**
	 * 活动统计 
	 * @author 黎青春
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findActivityStatistics(String beginDate, String endDate) throws DBException;
	/**
	 * 我的预定
	 * @author 黎青春
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public PagerModel search(String userId,String beginTime, String endTime,int state);
	/**
	 * 我的预定搜索
	 * @author 黎青春
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public PagerModel search(String userId,String beginDate, String endDate,
							String typeId,String levelId,String siteId,int state) ;
	/**
	 * 修改预定信息
	 * @author 黎青春
	 * @param bookSite
	 */
	public void modify(BookSite bookSite);
	/**
	 * 查找时间段预定信息
	 * @author 黎青春
	 * @param siteId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<BookSite> findBookSiteByTime(String siteId, String beginTime, String endTime);
	
	public PagerModel findAudit(String beginTime, String endTime, int state,String type);
	
	public PagerModel findBookSite(String userId,String beginTime,
						String endTime, int state,String type);
	
}
package com.nbcedu.function.booksite.dao;

import java.util.List;
import java.util.Map;

import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.website.dao.WsBaseDAO;
import com.nbcedu.function.booksite.website.exception.DBException;

public interface BookSiteDao extends WsBaseDAO<BookSite>{
	/**
	 * 增加预定信息
	 * @param bookSite
	 */
	public String createBookSite(BookSite bookSite);
	/**
	 * 删除
	 */
	public void deleteBookSite(String id);
	/**
	 * 更新预定信息
	 * @param bookSite
	 */
	public void updateBookSite(BookSite bookSite);
	/**
	 * 
	 * 查询所有预定信息
	 */
	public List<BookSite> retrieveAllBookSite();
	/**
	 * 根据条件查询预订信息
	 */
	public List<BookSite> retrieveAll(String param);

	/**
	 * 根据id查询预定信息
	 */
	public BookSite retrieveBookSiteById(String id);

	/**
	 * 查询某个场馆的预定信息
	 */
	public List<BookSite> retrieveBookSiteInfoBySiteId(String id, Integer statusId);
	
	/**
	 * search booksite by statusid
	 * @param id
	 * @return
	 */
	public List<BookSite> retrieveBookSiteByStatusId(Integer id);
	/**
	 * 根据场馆和时间断查询冲突
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<BookSite> retrieveBookSiteByTime(String id, Integer statusId, String beginTime, String endTime);

	/**
	 * 根据状态获取所有
	 */
	public List<BookSite> retrieveAll(final int start, final int pageSize, final Integer status_id);
	/**
	 * 总页数
	 */
	public int retrieveTotalPage(int start, int pageSize, Integer status_id);
	/**
	 * 总记录数
	 */
	public int retrieveTotalRecord(int start, int pageSize, Integer status_id);
	/**
	 * 进入分页方法
	 */
	public int[] retrieveStartAndPageSize(int pageSize, Integer status_id);
	/**
	 * 我的预定分页
	 */
	/**
	 * 查看我的预定(根据userid查询)
	 */
	public List<BookSite> retrieveBookSiteByUserId(String id, Integer statusId, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId);
	public List<BookSite> retrieveAll(final int start, final int pageSize, final Integer status_id, final String user_id, String beginTime,String endTime, String levelId, String typeId, String siteId, String optionId);
	/**
	 * 总页数
	 */
	public int retrieveTotalPage(int start, int pageSize, Integer status_id, String user_id, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId);
	/**
	 * 总记录数
	 */
	public int retrieveTotalRecord(int start, int pageSize, Integer status_id, String user_id, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId);
	/**
	 * 进入分页方法
	 */
	public int[] retrieveStartAndPageSize(int pageSize, Integer status_id, String user_id, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId);
	/**
	 * 根据场馆预定次数进行统计
	 */
	public int retrieveBySiteCount(String siteId, Integer statusId, String beginTime, String endTime);
	/**
	 * 根据级别进行统计
	 */
	public int retrieveByLevelCount(String levelId, Integer statusId, String beginTime, String endTime);
	/**
	 * 场馆统计
	 * @author 黎青春
	 * @param beginDate
	 * @param endDate
	 */
	public List findSiteStatistics(String beginDate, String endDate)throws DBException ;
	/**
	 * 活动统计
	 * @author 黎青春
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findActivityStatistics(String beginDate, String endDate)throws DBException ;
	
	public Map<String, String> getLevelJsonData();
}

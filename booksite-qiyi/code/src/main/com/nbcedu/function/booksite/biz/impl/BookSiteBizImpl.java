package com.nbcedu.function.booksite.biz.impl;

import java.util.List;

import com.nbcedu.function.booksite.biz.BookSiteBiz;
import com.nbcedu.function.booksite.dao.BookSiteDao;
import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.website.biz.impl.WsBaseBizImpl;
import com.nbcedu.function.booksite.website.pager.PagerModel;
import com.nbcedu.function.booksite.website.exception.DBException;

public class BookSiteBizImpl extends WsBaseBizImpl<BookSite>  implements BookSiteBiz {
	private BookSiteDao bookSiteDao;

	public void setBookSiteDao(BookSiteDao bookSiteDao) {
		super.setDao(bookSiteDao);
		this.bookSiteDao = bookSiteDao;
	}

	@Override
	public String addBookSite(BookSite bookSite) {
		return bookSiteDao.createBookSite(bookSite);
		
	}
	public void removeBookSite(String id){
		bookSiteDao.deleteBookSite(id);
	}
	@Override
	public List<BookSite> findAllBookSite() {		
		return bookSiteDao.retrieveAllBookSite();
	}

	@Override
	public List<BookSite> findBookSiteByStatusId(Integer id) {
		return bookSiteDao.retrieveBookSiteByStatusId(id);
	}

	@Override
	public List<BookSite> findBookSiteByTime(String id, Integer statusId, String beginTime, String endTime) {
		return bookSiteDao.retrieveBookSiteByTime(id, statusId, beginTime, endTime);
	}

	@Override
	public List<BookSite> findBookSiteInfoBySiteId(String id,Integer statusId) {
		return bookSiteDao.retrieveBookSiteInfoBySiteId(id,statusId);
	}

	@Override
	public BookSite findBookSiteById(String id) {
		return bookSiteDao.retrieveBookSiteById(id);
	}

	@Override
	public void modifyBookSite(BookSite bookSite) {
		bookSiteDao.updateBookSite(bookSite);
	}

	@Override
	public List<BookSite> findAll(int start, int pageSize, Integer status_id) {
		return bookSiteDao.retrieveAll(start, pageSize, status_id);
	}

	@Override
	public int[] findStartAndPageSize(int pageSize, Integer status_id) {
		return bookSiteDao.retrieveStartAndPageSize(pageSize, status_id);
	}

	@Override
	public int findTotalPage(int start, int pageSize, Integer status_id) {
		return bookSiteDao.retrieveTotalPage(start, pageSize, status_id);
	}

	@Override
	public int findTotalRecord(int start, int pageSize, Integer status_id) {
		return bookSiteDao.retrieveTotalRecord(start, pageSize, status_id);
	}

	@Override
	public List<BookSite> findBookSiteByUserId(String id,Integer statusId,String beginTime,String endTime, String levelId, String typeId, String siteId, String optionId) {
		return bookSiteDao.retrieveBookSiteByUserId(id, statusId, beginTime, endTime, levelId, typeId, siteId, optionId);
	}

	@Override
	public List<BookSite> findAll(int start, int pageSize, Integer statusId, String userId, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId) {
		return bookSiteDao.retrieveAll(start, pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId, optionId);
	}

	@Override
	public int[] findStartAndPageSize(int pageSize, Integer statusId, String userId,String beginTime,String endTime, String levelId, String typeId, String siteId, String optionId) {
		return bookSiteDao.retrieveStartAndPageSize(pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId, optionId);
	}

	@Override
	public int findTotalPage(int start, int pageSize, Integer statusId, String userId, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId) {
		return bookSiteDao.retrieveTotalPage(start, pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId, optionId);
	}

	@Override
	public int findTotalRecord(int start, int pageSize, Integer statusId, String userId, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId) {
		return bookSiteDao.retrieveTotalRecord(start, pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId, optionId);
	}

	@Override
	public int findBySiteCount(String siteId, Integer statusId, String beginTime, String endTime) {
		return bookSiteDao.retrieveBySiteCount(siteId, statusId, beginTime, endTime);
	}

	@Override
	public int findByLevelCount(String levelId, Integer statusId, String beginTime,String endTime) {
		return bookSiteDao.retrieveByLevelCount(levelId, statusId, beginTime, endTime);
	}
	/**
	 * 根据条件查询预订信息
	 */
	public List<BookSite> findAll(String param) {
		return bookSiteDao.retrieveAll(param);
	}

	@Override
	public PagerModel findAllByPm() {
		return this.bookSiteDao.searchPaginated("from BookSite b where b.bookSite_statusId in (0,1) and b.site.siteStatus.siteStatus_checkId=1 order by b.bookSite_beginTime desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findSiteStatistics(String beginDate, String endDate) throws DBException {
		return bookSiteDao.findSiteStatistics(beginDate,endDate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findActivityStatistics(String beginDate, String endDate) throws DBException {
		return bookSiteDao.findActivityStatistics(beginDate,endDate);
	}

	@Override
	public PagerModel findAllByPmForAll(String statusIdS, String userId, String beginTime, String endTime,
			String valueId, String typeId, String siteId, String opId) {
		int statusId = Integer.valueOf(statusIdS);
		if (opId.equals("1")) {
			if (beginTime != null && endTime != null) {
				if (statusId == 0 || statusId == 2) {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId in (0,2) and (b.bookSite_beginTime between '" + beginTime + "' and '" + endTime + "' or b.bookSite_endTime between '" + beginTime + "' and '" + endTime + "') order by b.bookSite_beginTime desc");
				} else {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId='" + statusId + "' and (b.bookSite_beginTime between '" + beginTime + "' and '" + endTime + "' or b.bookSite_endTime between '" + beginTime + "' and '" + endTime + "') order by b.bookSite_beginTime desc");
				}
			} else {
				if (statusId == 0 || statusId == 2) {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId in (0,2) order by b.bookSite_beginTime desc");
				} else {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId='" + statusId + "' order by b.bookSite_beginTime desc");
				}
			}
		} else if(opId.equals("2")) {
			if (valueId != null) {
				if (statusId == 0 || statusId == 2) {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId in (0,2) and b.activityLevel.activityLevel_id='" + valueId + "' order by b.bookSite_beginTime desc");
				} else {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId='" + statusId + "' and b.activityLevel.activityLevel_id='" + valueId + "' order by b.bookSite_beginTime desc");
				}
			} else {
				if (statusId == 0 || statusId == 2) {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId in (0,2) and b.activityLevel.activityLevel_id='" + valueId + "' order by b.bookSite_beginTime desc");
				} else {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId='" + statusId + "' order by b.bookSite_beginTime desc");
				}
			}
		} else if(opId.equals("3")){
			if(typeId != null) {
				if (statusId == 0 || statusId == 2) {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId in (0,2) and b.activityType.activityType_id='" + typeId + "' order by b.bookSite_beginTime desc");
				} else {
					return bookSiteDao.searchPaginated("from BookSite b where userId='" + userId + "' and b.bookSite_statusId='" + statusId + "' and b.activityType.activityType_id='" + typeId + "' order by b.bookSite_beginTime desc");
				}
			} else {
				return bookSiteDao.searchPaginated("from BookSite b where b.user.id='" + userId + "' and b.bookSite_statusId='" + statusId + "' order by b.bookSite_beginTime desc");
			}
		} else {
			if(siteId != null) {
				if (statusId == 0 || statusId == 2) {
					return bookSiteDao.searchPaginated("from BookSite b where b.user.id='" + userId + "' and b.bookSite_statusId in (0,2) and b.site.site_id='" + siteId + "' order by b.bookSite_beginTime desc");
				} else {
					return bookSiteDao.searchPaginated("from BookSite b where b.user.id='" + userId + "' and b.bookSite_statusId='" + statusId + "' and b.site.site_id='" + siteId + "' order by b.bookSite_beginTime desc");
				}
			} else {
				return bookSiteDao.searchPaginated("from BookSite b where b.user.id='" + userId + "' and b.bookSite_statusId='" + statusId + "' order by b.bookSite_beginTime desc");
			}
		}
	}

	@Override
	public PagerModel search(String userId,String beginTime, String endTime,int state) {
		return search(userId,beginTime,endTime,null,null,null,state);
	}
	
	public PagerModel search(String userId,String beginDate, String endDate,String typeId,String levelId,String siteId,int state) {
		StringBuffer hql = new StringBuffer(" from BookSite where bookSite_id != null");
		if(userId!=null && !"".equals(userId)){//申请人
			hql.append(" and userId = '");
			hql.append(userId);
			hql.append("'");
		}
		if(beginDate!=null && !"".equals(beginDate) && endDate!=null && !"".equals(endDate)){	//开始结束时间都选择了
			hql.append(" AND (");
			// 开始时间 在 请假时间范围内
			hql.append(" (bookSite_beginTime <='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime>='");
			hql.append(beginDate);
			hql.append("') ");	
			
			hql.append(" OR ");
			// 结束时间 在 请假时间范围内
			hql.append(" (bookSite_beginTime<='");
			hql.append(endDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime>='");
			hql.append(endDate);
			hql.append("') ");	
			hql.append(" OR ");
			// 开始结束时间 在 请假时间范围内包含
			hql.append(" (bookSite_beginTime>='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime<='");
			hql.append(endDate);
			hql.append("') ");	
			
			hql.append(") ");
		}else if(beginDate!=null && !"".equals(beginDate)){	//开始时间 （只选择开始时间）
			// 开始时间 在 请假时间范围内
			hql.append(" AND (bookSite_beginTime>='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" OR ");	
			hql.append(" bookSite_endTime>='");
			hql.append(beginDate);
			hql.append("') ");	
		}else if(endDate!=null && !"".equals(endDate)){		//结束时间  （只选择结束时间）
			// 结束时间 在 请假时间范围内
			hql.append(" AND (bookSite_beginTime<='");
			hql.append(endDate);
			hql.append("' ");	
			hql.append(" OR ");	
			hql.append(" bookSite_endTime<='");
			hql.append(endDate);
			hql.append("') ");	
		}
		if(typeId!=null && !"".equals(typeId)){
			hql.append(" and activityType.activityType_id='");
			hql.append(typeId);
			hql.append("'");
		}
		if(levelId!=null && !"".equals(levelId)){
			hql.append(" and activityLevel.activityLevel_id='");
			hql.append(levelId);
			hql.append("'");
		}
		if(siteId!=null && !"".equals(siteId)){
			hql.append(" and site.site_id='");
			hql.append(siteId);
			hql.append("'");
		}
		if(state!=-1){
			hql.append(" and bookSite_statusId=");
			hql.append(state);
		}
		hql.append(" ORDER BY bookSite_beginTime DESC , site.site_name desc");
		return bookSiteDao.searchPaginated(hql.toString());
	}
	@Override
	public void modify(BookSite bookSite) {
		update(bookSite);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BookSite> findBookSiteByTime(String siteId, String beginDate, String endDate){
		StringBuffer hql = new StringBuffer(" from BookSite where (bookSite_statusId <2)");
		if(beginDate!=null && !"".equals(beginDate) && endDate!=null && !"".equals(endDate)){	//开始结束时间都选择了
			hql.append(" AND (");
			// 开始时间 在 请假时间范围内
			hql.append(" (bookSite_beginTime <='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime>='");
			hql.append(beginDate);
			hql.append("') ");	
			
			hql.append(" OR ");
			// 结束时间 在 请假时间范围内
			hql.append(" (bookSite_beginTime<='");
			hql.append(endDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime>='");
			hql.append(endDate);
			hql.append("') ");	
			
			hql.append(" OR ");
			// 开始结束时间 在 请假时间范围内包含
			hql.append(" (bookSite_beginTime>='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime<='");
			hql.append(endDate);
			hql.append("') ");	
			
			hql.append(") ");
		}else if(beginDate!=null && !"".equals(beginDate)){	//开始时间 （只选择开始时间）
			// 开始时间 在 请假时间范围内
			hql.append(" AND (bookSite_beginTime>='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" OR ");	
			hql.append(" bookSite_endTime>='");
			hql.append(beginDate);
			hql.append("') ");	
		}else if(endDate!=null && !"".equals(endDate)){		//结束时间  （只选择结束时间）
			// 结束时间 在 请假时间范围内
			hql.append(" AND (bookSite_beginTime<='");
			hql.append(endDate);
			hql.append("' ");	
			hql.append(" OR ");	
			hql.append(" bookSite_endTime<='");
			hql.append(endDate);
			hql.append("') ");	
		}
		if(siteId!=null && !"".equals(siteId)){
			hql.append(" and site.site_id='");
			hql.append(siteId);
			hql.append("'");
		}
		
		return bookSiteDao.createQuery(hql.toString()).setMaxResults(1).list();
	}
	
	@Override
	public PagerModel findAudit(String beginDate, String endDate,int state,String type) {
		return findBookSite(null,beginDate,endDate,state,type);
	}
	
	@Override
	public PagerModel findBookSite(String userId,String beginDate, String endDate,int state,String type) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(" from BookSite where bookSite_statusId != 2");
		if(beginDate!=null && !"".equals(beginDate) && endDate!=null && !"".equals(endDate)){	//开始结束时间都选择了
			hql.append(" AND (");
			// 开始时间 在 请假时间范围内
			hql.append(" (bookSite_beginTime <='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime>='");
			hql.append(beginDate);
			hql.append("') ");	
			
			hql.append(" OR ");
			// 结束时间 在 请假时间范围内
			hql.append(" (bookSite_beginTime<='");
			hql.append(endDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime>='");
			hql.append(endDate);
			hql.append("') ");	
			
			hql.append(" OR ");
			// 开始结束时间 在 请假时间范围内包含
			hql.append(" (bookSite_beginTime>='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" AND ");	
			hql.append(" bookSite_endTime<='");
			hql.append(endDate);
			hql.append("') ");	
			
			hql.append(") ");
		}else if(beginDate!=null && !"".equals(beginDate)){	//开始时间 （只选择开始时间）
			// 开始时间 在 请假时间范围内
			hql.append(" AND (bookSite_beginTime>='");
			hql.append(beginDate);
			hql.append("' ");	
			hql.append(" OR ");	
			hql.append(" bookSite_endTime>='");
			hql.append(beginDate);
			hql.append("') ");	
		}else if(endDate!=null && !"".equals(endDate)){		//结束时间  （只选择结束时间）
			// 结束时间 在 请假时间范围内
			hql.append(" AND (bookSite_beginTime<='");
			hql.append(endDate);
			hql.append("' ");	
			hql.append(" OR ");	
			hql.append(" bookSite_endTime<='");
			hql.append(endDate);
			hql.append("') ");	
		}
		if(state!=-1){
			hql.append(" and bookSite_statusId=");
			hql.append(state);
		}
		if(userId!=null && !"".equals(userId)){
			hql.append(" and site.siteController like '%"+userId+"%'");
		}
		
		if(type!=null && !"".equals(type)){
			hql.append(" and site.siteType=");
			hql.append(type);
		}else{
			
		}
		return bookSiteDao.searchPaginated(hql.toString());
	}
	
}

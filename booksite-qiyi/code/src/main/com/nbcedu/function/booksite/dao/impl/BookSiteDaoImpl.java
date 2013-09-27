package com.nbcedu.function.booksite.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.nbcedu.function.booksite.dao.BookSiteDao;
import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.website.dao.impl.WsSimpleBaseDAOImpl;
import com.nbcedu.function.booksite.website.exception.DBException;
import com.opensymphony.xwork2.ActionContext;

public class BookSiteDaoImpl extends WsSimpleBaseDAOImpl<BookSite> implements BookSiteDao {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	@Override
	public String createBookSite(BookSite bookSite) {
		return getHibernateTemplate().save(bookSite).toString();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookSite> retrieveAllBookSite() {
		return getHibernateTemplate().find("from BookSite b order by b.bookSite_beginTime desc");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BookSite> retrieveBookSiteByStatusId(Integer id) {
		return getHibernateTemplate().find("from BookSite b where b.bookSite_statusId in (0,1)  and b.site.siteStatus.siteStatus_checkId=1 order by b.bookSite_beginTime desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	//以前只是查询未审核的，现在需要是审核过的，但还没有开，也有可能冲突
	public List<BookSite> retrieveBookSiteByTime(String id, Integer statusId, String beginTime, String endTime) {
//		return getHibernateTemplate().find("from BookSite b where b.site.site_id='" + id + "'and b.bookSite_statusId='" + statusId + 
//				"' and ((b.bookSite_beginTime between '" + beginTime + "' and '" + endTime + 
//				"' or b.bookSite_endTime between '" + beginTime + "' and '" + endTime + "') or (b.bookSite_beginTime <'" + beginTime + "' and b.bookSite_endTime > '" + endTime + "'))" +
//				" order by b.bookSite_beginTime desc");
		Date date = new Date();
		return getHibernateTemplate().find("from BookSite b where b.site.site_id='" + id + "'and b.bookSite_statusId in (0,1) and b.bookSite_endTime > '" + df.format(date) + "'" +
				" and ((b.bookSite_beginTime between '" + beginTime + "' and '" + endTime + 
				"' or b.bookSite_endTime between '" + beginTime + "' and '" + endTime + "') or (b.bookSite_beginTime <'" + beginTime + "' and b.bookSite_endTime > '" + endTime + "'))" +
				" order by b.bookSite_beginTime desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookSite> retrieveBookSiteInfoBySiteId(String id,Integer statusId) {
		if (statusId == 3) {
			return getHibernateTemplate().find("from BookSite b where b.site.site_id='" + id + "' order by b.bookSite_beginTime desc");
		} else {
			return getHibernateTemplate().find("from BookSite b where b.site.site_id='" + id + "' and b.bookSite_statusId<3 order by b.bookSite_beginTime desc");
		}
	}

	@Override
	public BookSite retrieveBookSiteById(String id) {
		return (BookSite) getHibernateTemplate().get(BookSite.class, id);
	}
	public void deleteBookSite(String id){
		getHibernateTemplate().delete(getHibernateTemplate().get(BookSite.class, id));
	}
	@Override
	public void updateBookSite(BookSite bookSite) {
		getHibernateTemplate().update(bookSite);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookSite> retrieveAll(final int start, final int pageSize, final Integer status_id) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("from BookSite b where b.bookSite_statusId in (0,1) and b.site.siteStatus.siteStatus_checkId=1 order by b.bookSite_beginTime desc");
				query.setFirstResult(start);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}
	
	/**
	 * 进入分页方法
	 */
	@SuppressWarnings("unchecked")
	public int[] retrieveStartAndPageSize(int pageSize, Integer status_id) {
		ActionContext context = ActionContext.getContext();
		Map p = context.getParameters();
		String [] count = (String[]) p.get("count");
		String [] pages = (String[]) p.get("pages");
		// 每页显示的条数
		int pagesSize = pageSize;
		// 当前页
		int currentPage = 1;
		if (count!=null) {
			currentPage = Integer.parseInt(count[0]);
		} else if (pages!=null) {
			if (pages.length == 0 || pages[0].equals("")) { //跳转框没有输入,默认为第一页
				currentPage = 1;
			} else { 
				 //跳转框有输入
				if (Integer.parseInt(pages[0]) >= 1 && Integer.parseInt(pages[0]) <= retrieveTotalPage(0, pageSize, status_id)) {  
					currentPage = Integer.parseInt(pages[0]);
				} else if (Integer.parseInt(pages[0]) < 1) {  //输入小于1
					currentPage = 1;
				} else {																		//如果输入的跳转页数字大于最大页数，则显示最后一页
					currentPage = retrieveTotalPage(0, pageSize, status_id);
				}
			}
		}
		// 起始位置
		int start = (currentPage - 1) * pageSize;
		// 取总页数
		int totalPages = retrieveTotalPage(start, pageSize, status_id);
		// 把start和pageSize放在 数组中
		int[] size = new int[2];
		size[0] = start;
		size[1] = pagesSize;
		// 当前页传递到页面
		context.getSession().put("currentPage", currentPage);
		context.getSession().put("totalPages", totalPages);
		return size;
	}

	/**
	 * 获取所有页数
	 */
	public int retrieveTotalPage(int start, int pageSize, Integer status_id) {
		int totalPage = retrieveTotalRecord(start, pageSize, status_id) / pageSize;
		int totalPage1 = retrieveTotalRecord(start, pageSize, status_id) % pageSize;
		if (totalPage1 != 0) {
			totalPage = totalPage + 1;
		}
		return totalPage;
	}

	/**
	 * 获取所有记录
	 */
	public int retrieveTotalRecord(int start, int pageSize, Integer status_id) {
		int result = retrieveBookSiteByStatusId(status_id).size();
		return result;
	}

	@SuppressWarnings("unchecked")
	//我的预定
	public List<BookSite> retrieveAll(final int start, final int pageSize, final Integer statusId,
			final String userId, final String beginTime, final String endTime, final String levelId,
			final String typeId, final String siteId, final String optionId) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (optionId.equals("1")) {
					if (beginTime != null && endTime != null) {

						if (statusId == 0 || statusId == 2) {
							Query query = session
									.createQuery("from BookSite b where userId='"
											+ userId
											+ "' and b.bookSite_statusId in (0,2) and (b.bookSite_beginTime between '"
											+ beginTime + "' and '" + endTime
											+ "' or b.bookSite_endTime between '" + beginTime + "' and '"
											+ endTime + "') order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						} else {
							Query query = session.createQuery("from BookSite b where userId='" + userId
									+ "' and b.bookSite_statusId='" + statusId
									+ "' and (b.bookSite_beginTime between '" + beginTime + "' and '"
									+ endTime + "' or b.bookSite_endTime between '" + beginTime + "' and '"
									+ endTime + "') order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						}
					} else {
						if (statusId == 0 || statusId == 2) {
							Query query = session
									.createQuery("from BookSite b where userId='"
											+ userId
											+ "' and b.bookSite_statusId in (0,2) order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						} else {
							Query query = session.createQuery("from BookSite b where userId='" + userId
									+ "' and b.bookSite_statusId='" + statusId
									+ "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						}
					}
				} else if (optionId.equals("2")) {
					if (levelId != null) {
						if (statusId == 0 || statusId == 2) {
							Query query = session
									.createQuery("from BookSite b where userId='"
											+ userId
											+ "' and b.bookSite_statusId in (0,2) and b.activityLevel.activityLevel_id='"
											+ levelId + "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						} else {
							Query query = session.createQuery("from BookSite b where userId='" + userId
									+ "' and b.bookSite_statusId='" + statusId
									+ "' and b.activityLevel.activityLevel_id='" + levelId
									+ "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						}
					} else {
						if (statusId == 0 || statusId == 2) {
							Query query = session
									.createQuery("from BookSite b where userId='"
											+ userId
											+ "' and b.bookSite_statusId in (0,2) and b.activityLevel.activityLevel_id='"
											+ levelId + "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						} else {
							Query query = session.createQuery("from BookSite b where userId='" + userId
									+ "' and b.bookSite_statusId='" + statusId
									+ "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						}
					}
				} else if (optionId.equals("3")) {
					if (typeId != null) {
						if (statusId == 0 || statusId == 2) {
							Query query = session
									.createQuery("from BookSite b where userId='"
											+ userId
											+ "' and b.bookSite_statusId in (0,2) and b.activityType.activityType_id='"
											+ typeId + "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						} else {
							Query query = session.createQuery("from BookSite b where userId='" + userId
									+ "' and b.bookSite_statusId='" + statusId
									+ "' and b.activityType.activityType_id='" + typeId
									+ "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						}
					} else {
						Query query = session.createQuery("from BookSite b where userId='" + userId
								+ "' and b.bookSite_statusId='" + statusId
								+ "' order by b.bookSite_beginTime desc");
						query.setFirstResult(start);
						query.setMaxResults(pageSize);
						return query.list();
					}
				} else {
					if (siteId != null) {
						if (statusId == 0 || statusId == 2) {
							Query query = session.createQuery("from BookSite b where userId='" + userId
									+ "' and b.bookSite_statusId in (0,2) and b.site.site_id='" + siteId
									+ "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						} else {
							Query query = session.createQuery("from BookSite b where userId='" + userId
									+ "' and b.bookSite_statusId='" + statusId + "' and b.site.site_id='"
									+ siteId + "' order by b.bookSite_beginTime desc");
							query.setFirstResult(start);
							query.setMaxResults(pageSize);
							return query.list();
						}
					} else {
						Query query = session.createQuery("from BookSite b where userId='" + userId
								+ "' and b.bookSite_statusId='" + statusId
								+ "' order by b.bookSite_beginTime desc");
						query.setFirstResult(start);
						query.setMaxResults(pageSize);
						return query.list();
					}
				}
			}
		});
	}
	/**
	 * 查看我的预定(根据userid查询)
	 */
	@SuppressWarnings("unchecked")
	public List<BookSite> retrieveBookSiteByUserId(String id, Integer statusId, String beginTime,
			String endTime, String levelId, String typeId, String siteId, String optionId) {
		List<BookSite> list = null;
		if (optionId.equals("1")) {
			if (beginTime != null && endTime != null) {
				if (statusId == 0 || statusId == 2) {
					list = getHibernateTemplate()
							.find(
									"from BookSite b where userId='"
											+ id
											+ "' and b.bookSite_statusId in (0,2) and (b.bookSite_beginTime between '"
											+ beginTime + "' and '" + endTime
											+ "' or b.bookSite_endTime between '" + beginTime + "' and '"
											+ endTime + "') order by b.bookSite_beginTime desc");
				} else {
					list = getHibernateTemplate().find(
							"from BookSite b where userId='" + id + "' and b.bookSite_statusId='" + statusId
									+ "' and (b.bookSite_beginTime between '" + beginTime + "' and '"
									+ endTime + "' or b.bookSite_endTime between '" + beginTime + "' and '"
									+ endTime + "') order by b.bookSite_beginTime desc");
				}
			} else {
				if (statusId == 0 || statusId == 2) {
					list = getHibernateTemplate()
							.find(
									"from BookSite b where userId='"
											+ id
											+ "' and b.bookSite_statusId in (0,2) order by b.bookSite_beginTime desc");
				} else {
					list = getHibernateTemplate().find(
							"from BookSite b where userId='" + id + "' and b.bookSite_statusId='" + statusId
									+ "' order by b.bookSite_beginTime desc");
				}

			}
		} else if (optionId.equals("2")) {
			if (levelId != null) {
				if (statusId == 0 || statusId == 2) {
					list = getHibernateTemplate()
							.find(
									"from BookSite b where userId ='"
											+ id
											+ "' and b.bookSite_statusId in (0,2) and b.activityLevel.activityLevel_id ='"
											+ levelId + "' order by b.bookSite_beginTime desc");
				} else {
					list = getHibernateTemplate().find(
							"from BookSite b where userId ='" + id + "' and b.bookSite_statusId ='"
									+ statusId + "' and b.activityLevel.activityLevel_id ='" + levelId
									+ "' order by b.bookSite_beginTime desc");
				}
			} else {
				if (statusId == 0 || statusId == 2) {
					list = getHibernateTemplate()
							.find(
									"from BookSite b where userId ='"
											+ id
											+ "' and b.bookSite_statusId in (0,2) order by b.bookSite_beginTime desc");
				} else {
					list = getHibernateTemplate().find(
							"from BookSite b where userId ='" + id + "' and b.bookSite_statusId ='"
									+ statusId + "' order by b.bookSite_beginTime desc");
				}
			}
		} else if (optionId.equals("3")) {
			if (typeId != null) {
				if (statusId == 0 || statusId == 2) {
					list = getHibernateTemplate()
							.find(
									"from BookSite b where userId ='"
											+ id
											+ "' and b.bookSite_statusId in (0,2) and b.activityType.activityType_id ='"
											+ typeId + "' order by b.bookSite_beginTime desc");
				} else {
					list = getHibernateTemplate().find(
							"from BookSite b where userId ='" + id + "' and b.bookSite_statusId ='"
									+ statusId + "' and b.activityType.activityType_id ='" + typeId
									+ "' order by b.bookSite_beginTime desc");
				}
			} else {
				if (statusId == 0 || statusId == 2) {
					list = getHibernateTemplate()
							.find(
									"from BookSite b where userId ='"
											+ id
											+ "' and b.bookSite_statusId in (0,2) order by b.bookSite_beginTime desc");
				} else {
					list = getHibernateTemplate().find(
							"from BookSite b where userId ='" + id + "' and b.bookSite_statusId ='"
									+ statusId + "' order by b.bookSite_beginTime desc");
				}
			}
		} else if (optionId.equals("4")) {
			if (siteId != null) {
				if (statusId == 0 || statusId == 2) {
					list = getHibernateTemplate().find(
							"from BookSite b where userId ='" + id
									+ "' and b.bookSite_statusId in (0,2) and b.site.site_id ='" + siteId
									+ "' order by b.bookSite_beginTime desc");
				} else {
					list = getHibernateTemplate().find(
							"from BookSite b where userId ='" + id + "' and b.bookSite_statusId ='"
									+ statusId + "' and b.site.site_id ='" + siteId
									+ "' order by b.bookSite_beginTime desc");
				}
			} else {
				if (statusId == 0 || statusId == 2) {
					list = getHibernateTemplate()
							.find(
									"from BookSite b where userId ='"
											+ id
											+ "' and b.bookSite_statusId in (0,2) order by b.bookSite_beginTime desc");
				} else {
					list = getHibernateTemplate().find(
							"from BookSite b where userId ='" + id + "' and b.bookSite_statusId ='"
									+ statusId + "' order by b.bookSite_beginTime desc");
				}
			}
		}
		return list;
	}
	//我的预定进入分页方法，传值
	@SuppressWarnings("unchecked")
	public int[] retrieveStartAndPageSize(int pageSize, Integer statusId, String userId, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId) {
		ActionContext context = ActionContext.getContext();
		Map p = context.getParameters();
		String [] count = (String[]) p.get("count");
		String [] pages = (String[]) p.get("pages");
		// 每页显示的条数
		int pagesSize = pageSize;
		// 当前页
		int currentPage = 1;
		if (count != null) {
			currentPage = Integer.parseInt(count[0]);
		} else if (pages != null) {
			if (pages.length == 0 || pages[0].equals("")) {  //跳转框没有输入,默认为第一页
				currentPage = 1;
			} else { 
				//跳转框有输入
				if (Integer.parseInt(pages[0]) >= 1 && Integer.parseInt(pages[0]) <= retrieveTotalPage(0, pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId, optionId)) {  
					currentPage = Integer.parseInt(pages[0]);
				} else if (Integer.parseInt(pages[0]) < 1) {  //输入小于1
					currentPage = 1;
				} else {																		//如果输入的跳转页数字大于最大页数，则显示最后一页
					currentPage = retrieveTotalPage(0, pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId, optionId);
				}
			}
		}
		// 起始位置
		int start = (currentPage - 1) * pageSize;
		// 取总页数
		int totalPages = retrieveTotalPage(start, pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId, optionId);
		// 把start和pageSize放在 数组中
		int[] size = new int[2];
		size[0] = start;
		size[1] = pagesSize;
		// 当前页传递到页面
		context.getSession().put("currentPage", currentPage);
		context.getSession().put("totalPages", totalPages);

		return size;
	}

	//我的预定总记录数
	public int retrieveTotalRecord(int start, int pageSize, Integer statusId, String userId,String beginTime,String endTime, String levelId, String typeId, String siteId, String optionId) {
		int totalRecord = retrieveBookSiteByUserId(userId, statusId, beginTime, endTime, levelId, typeId, siteId, optionId).size();
		return totalRecord;
	}

	//我的预定总页数
	public int retrieveTotalPage(int start, int pageSize, Integer statusId, String userId, String beginTime, String endTime, String levelId, String typeId, String siteId, String optionId) {
		int totalPage = retrieveTotalRecord(start, pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId,  optionId) / pageSize;
		int totalPage1 = retrieveTotalRecord(start, pageSize, statusId, userId, beginTime, endTime, levelId, typeId, siteId, optionId) % pageSize;
		if (totalPage1 != 0) {
			totalPage += 1;
		}
		return totalPage;
	}
	/**
	 * 根据场馆预定次数进行统计
	 */
	@SuppressWarnings("unchecked")
	public int retrieveBySiteCount(String siteId, Integer statusId, String beginTime,String endTime) {
		List<BookSite> list = null;
		if (beginTime != null && endTime != null) {
			list = getHibernateTemplate().find("from BookSite b where b.site.site_id='" + siteId + "' and b.bookSite_statusId='" + statusId + "' and ((b.bookSite_beginTime between '" + beginTime + "' and '" + endTime + "' or b.bookSite_endTime between '" + beginTime + "' and '" + endTime + "') or (b.bookSite_beginTime >'" + beginTime + "' and b.bookSite_endTime < '" + endTime + "'))");
			return list.size();
		} else {
			return getHibernateTemplate().find("from BookSite b where b.site.site_id='" + siteId + "' and b.bookSite_statusId='" + statusId + "'").size();
		}
	}
	/**
	 * 根据级别进行统计
	 */
	@SuppressWarnings("unchecked")
	public int retrieveByLevelCount(String levelId, Integer statusId, String beginTime,String endTime) {
		List<BookSite> list = null;
		if (beginTime != null && endTime != null) {
			list = getHibernateTemplate().find("from BookSite b where b.activityLevel.activityLevel_id='" + levelId + "' and b.bookSite_statusId='" + statusId + "' and ((b.bookSite_beginTime between '" + beginTime + "' and '" + endTime + "' or b.bookSite_endTime between '" + beginTime + "' and '" + endTime + "') or (b.bookSite_beginTime >'" + beginTime + "' and b.bookSite_endTime < '" + endTime + "'))");
			return list.size();
		} else {
			return getHibernateTemplate().find("from BookSite b where b.activityLevel.activityLevel_id='" + levelId + "' and b.bookSite_statusId='" + statusId + "'").size();
		}
	}
	/**
	 * 根据条件查询预订信息
	 */
	@SuppressWarnings("unchecked")
	public List<BookSite> retrieveAll(String param) {
		return getHibernateTemplate().find(param);
	}

	@Override
	public List findSiteStatistics(String beginDate, String endDate) throws DBException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select PK_SITE_ID as ID,SITE_NAME as NAME, ");
		sql.append(" (select count(pk_BOOKSITE_ID) FROM t_booksite_booksite");
		
		//from Site s where s.siteStatus.siteStatus_checkId = " + id 
		if (beginDate != null && !"".equals(beginDate) && endDate != null && !"".equals(endDate)) { // 开始结束时间都选择了
			sql.append(" AND (");
			// 开始时间 在 请假时间范围内
			sql.append(" (bookSite_beginTime <='");
			sql.append(beginDate);
			sql.append("' ");
			sql.append(" AND ");
			sql.append(" bookSite_endTime>='");
			sql.append(beginDate);
			sql.append("') ");

			sql.append(" OR ");
			// 结束时间 在 请假时间范围内
			sql.append(" (bookSite_beginTime<='");
			sql.append(endDate);
			sql.append("' ");
			sql.append(" AND ");
			sql.append(" bookSite_endTime>='");
			sql.append(endDate);
			sql.append("') ");

			sql.append(" OR ");
			// 开始结束时间 在 请假时间范围内包含
			sql.append(" (bookSite_beginTime>='");
			sql.append(beginDate);
			sql.append("' ");
			sql.append(" AND ");
			sql.append(" bookSite_endTime<='");
			sql.append(endDate);
			sql.append("') ");

			sql.append(") ");
		} else if (beginDate != null && !"".equals(beginDate)) { // 开始时间 （只选择开始时间）
			// 开始时间 在 请假时间范围内
			sql.append(" AND (bookSite_beginTime>='");
			sql.append(beginDate);
			sql.append("' ");
			sql.append(" OR ");
			sql.append(" bookSite_endTime>='");
			sql.append(beginDate);
			sql.append("') ");
		} else if (endDate != null && !"".equals(endDate)) { // 结束时间 （只选择结束时间）
			// 结束时间 在 请假时间范围内
			sql.append(" AND (bookSite_beginTime<='");
			sql.append(endDate);
			sql.append("' ");
			sql.append(" OR ");
			sql.append(" bookSite_endTime<='");
			sql.append(endDate);
			sql.append("') ");
		}
		//sql.append(" )");
		sql.append(" WHERE ");
		sql.append(" FK_SITE_NO=site.PK_SITE_ID )");
		sql.append("  as COUNT ");
		sql.append(" FROM  t_booksite_site site,");
		sql.append(" (SELECT pk_SITESTATUS_ID AS ID ");
		sql.append(" FROM t_booksite_sitestatus sta ");
		sql.append(" WHERE sta.siteStatus_checkId=1) statu ");
		sql.append(" WHERE site.FK_SITESTATUS_NO = statu.ID");
		return queryForList(sql.toString());
	}
	
	@Override
	public List findActivityStatistics(String beginDate, String endDate) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select PK_activityLevel_ID as ID,activityLevel_NAME as NAME, ");
		sql.append("(select count(*) from t_booksite_booksite where FK_ACTIVITYLEVEL_NO=ac.PK_activityLevel_ID  ");
		if(beginDate!=null && !"".equals(beginDate) && endDate!=null && !"".equals(endDate)){	//开始结束时间都选择了
			sql.append(" AND (");
			// 开始时间 在 请假时间范围内
			sql.append(" (bookSite_beginTime <='");
			sql.append(beginDate);
			sql.append("' ");	
			sql.append(" AND ");	
			sql.append(" bookSite_endTime>='");
			sql.append(beginDate);
			sql.append("') ");	
			
			sql.append(" OR ");
			// 结束时间 在 请假时间范围内
			sql.append(" (bookSite_beginTime<='");
			sql.append(endDate);
			sql.append("' ");	
			sql.append(" AND ");	
			sql.append(" bookSite_endTime>='");
			sql.append(endDate);
			sql.append("') ");	
			
			sql.append(" OR ");
			// 开始结束时间 在 请假时间范围内包含
			sql.append(" (bookSite_beginTime>='");
			sql.append(beginDate);
			sql.append("' ");	
			sql.append(" AND ");	
			sql.append(" bookSite_endTime<='");
			sql.append(endDate);
			sql.append("') ");	
			
			sql.append(") ");
		}else if(beginDate!=null && !"".equals(beginDate)){	//开始时间 （只选择开始时间）
			// 开始时间 在 请假时间范围内
			sql.append(" AND (bookSite_beginTime>='");
			sql.append(beginDate);
			sql.append("' ");	
			sql.append(" OR ");	
			sql.append(" bookSite_endTime>='");
			sql.append(beginDate);
			sql.append("') ");	
		}else if(endDate!=null && !"".equals(endDate)){		//结束时间  （只选择结束时间）
			// 结束时间 在 请假时间范围内
			sql.append(" AND (bookSite_beginTime<='");
			sql.append(endDate);
			sql.append("' ");	
			sql.append(" OR ");	
			sql.append(" bookSite_endTime<='");
			sql.append(endDate);
			sql.append("') ");	
		}
		sql.append(" )");
		sql.append("  as COUNT  from t_booksite_activitylevel as ac ");
		return queryForList(sql.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getLevelJsonData() {
		return (Map<String, String>)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Map<String, String> result = new HashMap<String, String>();
				StringBuffer sql = new StringBuffer("");
				sql.append("SELECT ");  
					sql.append("COUNT(bs.pk_BOOKSITE_ID) as numcount,lv.activityLevel_NAME lvname ");
				sql.append("FROM ");
					sql.append("t_booksite_booksite bs,t_booksite_activitylevel lv ");
				sql.append("WHERE ");
					sql.append("bs.FK_ACTIVITYLEVEL_NO=lv.PK_activityLevel_ID ");
				sql.append("GROUP BY ");
					sql.append("bs.FK_ACTIVITYLEVEL_NO ");
				Query q = session.createSQLQuery(sql.toString());
				List<Object[]> resultSet = q.list();
				if(resultSet!=null&& resultSet.size() >0){
					for (Object[] obj : resultSet) {
						result.put(obj[1].toString(), obj[0].toString());
					}
				}
				return result;
			}
		});
	}
}

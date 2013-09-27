package com.nbcedu.function.booksite.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.nbcedu.function.booksite.dao.SiteDao;
import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.website.dao.impl.WsSimpleBaseDAOImpl;
import com.opensymphony.xwork2.ActionContext;

public class SiteDaoImpl  extends WsSimpleBaseDAOImpl<Site>  implements SiteDao {
	@Override
	public void createSite(Site site) {
		getHibernateTemplate().save(site);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Site> retrieveAllSite() {
		return getHibernateTemplate().find("from Site");
	}

	@Override
	public void updateSite(Site site) {
		getHibernateTemplate().merge(site);
	}

	@Override
	public Site retrieveSiteById(String id) {
		return (Site)getHibernateTemplate().get(Site.class, id);
	}
	/**
	 * 根据状态id查询场馆
	 */
	@SuppressWarnings("unchecked")
	public List<Site> retrieveByStatusId(Integer id) {
		if (id == 1 || id == 2 || id == 4) {
			return getHibernateTemplate().find("from Site s where s.siteStatus.siteStatus_checkId in (1,2,4) order by s.siteStatus.siteStatus_checkId asc");
		} else {

			return getHibernateTemplate().find("from Site s where s.siteStatus.siteStatus_checkId = " + id + " order by s.siteStatus.siteStatus_checkId asc");
		}
	}
	@SuppressWarnings("unchecked")
	public List<Site> retrieveByStatus(Integer id){
		return getHibernateTemplate().find("from Site s where s.siteStatus.siteStatus_checkId='" + id + "' order by s.siteStatus.siteStatus_checkId asc");
	}
	@SuppressWarnings("unchecked")
	public List<Site> retrieveOpenByStatusId(Integer id){
		return getHibernateTemplate().find("from Site s where s.siteStatus.siteStatus_checkId = " + id + " order by s.siteStatus.siteStatus_checkId asc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Site> retrieveAll(final int start, final int pageSize, final Integer statusId) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (statusId == 3) {
					Query query = session.createQuery("from Site s where s.siteStatus.siteStatus_checkId = 3 order by s.siteStatus.siteStatus_checkId asc");
					query.setFirstResult(start);
					query.setMaxResults(pageSize);
					return query.list();
				} else {
					Query query = session.createQuery("from Site s where s.siteStatus.siteStatus_checkId in (1,2,4) order by s.siteStatus.siteStatus_checkId asc");
					query.setFirstResult(start);
					query.setMaxResults(pageSize);
					return query.list();
				}
			}
		});
	}
	/**
	 * 总页数
	 */
	@Override
	public int retrieveTotalPage(int start, int pageSize , Integer status_id) {
		int totalPage = retrieveTotalRecord(start, pageSize, status_id) / pageSize; 
		int totalPage1 = retrieveTotalRecord(start, pageSize, status_id) % pageSize; 
		
		int pages = 0;

		if (totalPage1 != 0) {
			pages = totalPage + 1;
		} else {
			pages = totalPage;
		}
		
	
		return pages;
	}
	/**
	 * 总记录数
	 */
	@Override
	public int retrieveTotalRecord(int start, int pageSize, Integer status_id) {
		int result;
		result = retrieveByStatusId(status_id).size();
		return result;
	}
	/**
	 * 进入分页方法
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int[] retrieveStartAndPageSize(int pageSize, Integer status_id) {
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
		} else if (context.getSession().get("currentPage") != null) {
			currentPage = (Integer)context.getSession().get("currentPage");
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Site> retrieveByLevel(String levelId) {
		return getHibernateTemplate().find("select s from Site s inner join s.activityLevels a where a.activityLevel_id = '" + levelId + "'");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Site> retrieveByType(String typeId) {
		return getHibernateTemplate().find("select s from Site s inner join s.activityTypes a where a.activityType_id = '" + typeId + "'");
	}

}

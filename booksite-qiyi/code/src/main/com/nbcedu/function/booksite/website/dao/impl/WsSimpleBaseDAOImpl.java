package com.nbcedu.function.booksite.website.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.nbcedu.function.booksite.website.comment.BasicTransformerAdapter;
import com.nbcedu.function.booksite.website.comment.Constants;
import com.nbcedu.function.booksite.website.exception.DBException;
import com.nbcedu.function.booksite.website.pager.PagerModel;
import com.nbcedu.function.booksite.website.pager.SystemContext;


@SuppressWarnings("unchecked")
public class WsSimpleBaseDAOImpl<T extends Serializable> extends WsBaseDAOImpl<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	public List queryForList(final String queryString, final int begin, final int end) throws DBException {
		List result = null;
		try {
			result = (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					if ((begin == Constants.ALL_POS) && (end == Constants.ALL_POS)) {
						return session.createSQLQuery(queryString).setResultTransformer(
								(ResultTransformer) new UpperCasedAliasToEntityMapResultTransformer()).list();
					} else {
						return session
								.createSQLQuery(queryString)
								.setFirstResult(begin)
								.setMaxResults(end)
								.setResultTransformer(
										(ResultTransformer) new UpperCasedAliasToEntityMapResultTransformer())
								.list();
					}
				}
			});
		} catch (Exception e) {
			logger.error("查询数据错误");
			new DBException(e.getMessage());
		}
		return result;
	}

	/**
	 * 通过SQL查询得到一个保存MAP对象的集合LIST 例如：SELECT NAME,SEX FROM USER
	 * 
	 * @param sql
	 * @param begin
	 * @param end
	 * @return
	 * @throws DBException
	 */
	public List<Map> getListBySql(String sql, int begin, int end) throws DBException {
		return this.queryForList(sql, begin, end);
	}

	/**
	 * 以系统内部设置的默认start,pagesize来查询列表
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	public List<Map> getListBySql(String sql) throws DBException {
		return this.getListBySql(sql, SystemContext.getOffset(), SystemContext.getPagesize());
	}

	/**
	 * 更加SQL操作获取单一个对象，并组合成MAP返回
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 */

	public Map getOneBySql(final String sql) throws DBException {
		Map result = new HashMap();
		try {
			result = (Map) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					return session.createSQLQuery(sql).setResultTransformer(
							(ResultTransformer) new UpperCasedAliasToEntityMapResultTransformer())
							.uniqueResult();
				}
			});
		} catch (Exception e) {
			logger.error("查询数据错误");
			new DBException(e.getMessage());
		}
		return result;
	}

	public List queryForList(String sql) throws DBException {
		return queryForList(sql, Constants.ALL_POS, Constants.ALL_POS);
	}

	public Object queryForObject(String sql) throws DBException {
		return this.uniqueResultBySQL(sql);
	}

	@SuppressWarnings("serial")
	private static class UpperCasedAliasToEntityMapResultTransformer extends BasicTransformerAdapter
			implements Serializable {
		public Object transformTuple(Object[] tuple, String[] aliases) {
			Map result = new HashMap(tuple.length);
			for (int i = 0; i < tuple.length; i++) {
				String alias = aliases[i];
				if (alias != null) {
					result.put(alias.toUpperCase(), tuple[i]);
				}
			}
			return result;
		}
	}

	// 来自于website的分页技术 begin
	public PagerModel searchPaginated(String hql) {
		return searchPaginated(hql, null);
	}

	public PagerModel searchPaginated(List list) {
		return this.searchPaginated(list, SystemContext.getOffset(), SystemContext.getPagesize());
	}

	public PagerModel searchPaginated(String hql, Object param) {
		return searchPaginated(hql, new Object[] {param});
	}

	public PagerModel searchPaginated(String hql, Object[] params) {
		return searchPaginated(hql, params, SystemContext.getOffset(), SystemContext.getPagesize());
	}

	public PagerModel searchPaginated(String hql, int offset, int pagesize) {
		return searchPaginated(hql, null, offset, pagesize);
	}

	public PagerModel searchPaginated(String hql, Object param, int offset, int pagesize) {
		return searchPaginated(hql, new Object[] {param}, offset, pagesize);
	}

	public PagerModel searchPaginated(String hql, Object[] params, int offset, int pagesize) {
		// 获取记录总数
		String countHql = getCountQuery(hql);
		Query query = getSession().createQuery(countHql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		int total = ((Long) query.uniqueResult()).intValue();

		// 获取结果集
		query = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		query.setFirstResult(offset);
		query.setMaxResults(pagesize);
		List datas = query.list();

		// 返回PagerModel
		PagerModel pm = new PagerModel();
		pm.setDatas(datas);
		pm.setTotal(total);
		pm.setTotalPageNo(total % pagesize == 0 ? total / pagesize : total/pagesize+1);
		return pm;
	}

	public PagerModel searchPaginated(Criteria criteria) {
		return searchPaginated(criteria, SystemContext.getOffset(), SystemContext.getPagesize());
	}

	public PagerModel searchPaginated(Criteria criteria, int offset, int pagesize) {
		Assert.notNull(criteria);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		Projection projection = impl.getProjection();

		Integer totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();

		criteria.setProjection(projection);
		criteria.setFirstResult(offset);
		criteria.setMaxResults(pagesize);
		List datas = criteria.list();
		// 返回PagerModel
		PagerModel pm = new PagerModel();
		pm.setDatas(datas);
		pm.setTotal(totalCount.intValue());
		return pm;
	}

	public void searchPaginated(Map map) {
		map.put("start", SystemContext.getOffset());
		map.put("pagesize", SystemContext.getPagesize());
	}

	/**
	 * 根据HQL语句，获得查询总记录数的HQL语句 如： select ... from Organization o where o.parent is null 经过转换，可以得到： select
	 * count(*) from Organziation o where o.parent is null
	 * 
	 * @param hql
	 * @return
	 */
	private String getCountQuery(String hql) {
		return parseCountSql(hql);
		/*
		 * int index = hql.indexOf("from"); if(index != -1){ return "select count(*) " + hql.substring(index);
		 * } throw new SystemException("无效的HQL查询语句【"+hql+"】");
		 */
	}

	public PagerModel searchPaginated(List list, int offset, int pagesize) {

		PagerModel pagerModel = new PagerModel();
		if (list != null && list.size() > 0) {
			// 查询总的记录数
			int total = list.size();
			// 显示结果
			List l = new ArrayList();
			if (list.size() > offset + pagesize) {
				l = list.subList(offset, offset + pagesize);
			} else {
				l = list.subList(offset, list.size());
			}

			pagerModel.setTotal(total);
			pagerModel.setDatas(l);
		}

		return pagerModel;
	}

}

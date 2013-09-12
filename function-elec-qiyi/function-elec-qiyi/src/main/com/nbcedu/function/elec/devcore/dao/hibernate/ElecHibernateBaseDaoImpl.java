package com.nbcedu.function.elec.devcore.dao.hibernate;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.nbcedu.core.dao.HibernateBaseDaoImpl;
import com.nbcedu.core.page.Page;

/**
 * BaseDao
 * 
 * @author qinyuan
 * @since 2013-2-26
 */
public class ElecHibernateBaseDaoImpl<T extends Serializable> extends HibernateBaseDaoImpl<T> implements ElecHibernateBaseDao<T> {

	@Resource
	public void setSessionFactory1(SessionFactory sessionFactory1) {
		super.setSessionFactory(sessionFactory1);
	}
	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public T saveEle(T entity) {
		getHibernateTemplate().save(entity);
		return entity;
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 */
	public T updateEle(T entity) {
		getHibernateTemplate().update(entity);
		return entity;
	}

	/**
	 * 修改或者保存
	 * 
	 * @param entity
	 * @return
	 */
	public T saveOrUpdateEle(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}
	
	/**
	 * 批量删除
	 * @param entities
	 */
	public void batchDelete(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}
	
	/**
	 * 根据原生SQL分页
	 * @param page
	 * @param sql
	 * @param values
	 * @return
	 */
	public Page findPageBySQL(Page page, String sql, String countSql, Object... values) {
		page.setCount(this.getCountSql(countSql, values));
		Query query = this.setPageParameter(this.createSqlQuery(sql, values), page);
		page.setDatas(query.list());
		return page;
	}
	
	/**
	 * 根据传入原生SQL，查询总数
	 * @param countSql
	 * @param values
	 * @return
	 */
	public long getCountSql(String countSql, Object... values) {
		return ((Number) this.createSqlQuery(countSql, values).uniqueResult()).longValue();
	}

}

package com.nbcedu.function.elec.devcore.dao.hibernate;

import java.io.Serializable;
import java.util.Collection;

import com.nbcedu.core.dao.HibernateBaseDao;
import com.nbcedu.core.page.Page;

/**
 * BaseDao
 * 
 * @author qinyuan
 * @since 2013-2-26
 */
public interface ElecHibernateBaseDao<T extends Serializable> extends HibernateBaseDao<T> {
	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	T saveEle(T entity);

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 */
	T updateEle(T entity);

	/**
	 * 修改或者保存
	 * 
	 * @param entity
	 * @return
	 */
	T saveOrUpdateEle(T entity);
	
	/**
	 * 批量删除
	 * @param entities
	 */
	public void batchDelete(Collection<T> entities);
	
	/**
	 * 根据原生SQL分页
	 * @param page
	 * @param sql
	 * @param values
	 * @return
	 */
	public Page findPageBySQL(Page page, String sql, String countSql, Object... values);
	
	/**
	 * 根据传入原生SQL，查询总数
	 * @param countSql
	 * @param values
	 * @return
	 */
	public long getCountSql(String countSql, Object... values);
}

package com.nbcedu.function.booksite.website.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;

import com.nbcedu.function.booksite.website.exception.DBException;
import com.nbcedu.function.booksite.website.pager.PagerModel;


@SuppressWarnings("unchecked")
public interface WsBaseDAO<T extends Serializable> {

	public T load(Serializable id, boolean lock);

	public T get(Serializable id);

	public T load(Serializable id);

	public Object update(T entity);

	public Object save(T entity);

	public Object saveOrUpdate(T entity);
	
	public Object merge(T entity);
	
	public void remove(T entity);
	
	public T removeById(Serializable id);
	
	public T removeById(String id);
	
	public List<T> getAll(String orderBy, boolean isAsc);
	
	public List<T> getAll();
	
	
	public void flush() ;
	
	public void clear();
	
	public void refresh(T entity);
	
	
	public List<T> find(String hql, Object... values);
	
	public List<T> find(final String hql,final int begin,final int maxSiz);
	
	public List<T> findBy(String propertyName, Object value);
	
	public  List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc);
	
	public  T findUniqueBy(String propertyName, Object value);
	
	//public  boolean isUnique(Object entity, String uniquePropertyNames);
	
	public T createNewEntiey();
	
	//public Serializable getId(Object entity)throws NoSuchMethodException, IllegalAccessException,InvocationTargetException ;
	
	public String getIdName() ;
	
	
	public Query createQuery(String hql, Object... values);
	
	public Query createSqlQuery(String sql, Object... values);
	
	public  Criteria createCriteria(Criterion... criterions);
	
	public  Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions);
	
	
	
	
	public int getRecordCountBySQL(String sql) throws DBException;
	
	public Object uniqueResultBySQL(String sql) throws DBException;
	
	public Object uniqueResultByHQL(String hql) throws DBException ;
	
	public List<Object[]> findBySQL(String sql, int beginIndex, int size) throws DBException;
	
	public List<Object[]> findBySQL(String sql) throws DBException;
	
	public void updateBySql(String sql,Object... params) throws DBException;
	
	public void updateBySql(String sql) throws DBException;
	
	public void updateByHql(String hql) throws DBException;
	
	public void updateByHql(String hql,Object... params) throws DBException;
	
	public long countAll();
	
	public int countByProperty(String property, Object value);
	
	public List queryForList(final String queryString,final int begin, final int end) throws DBException;
	
	public List queryForList(String sql)throws DBException;
	
	public PagerModel searchPaginated(String hql);
	
	public PagerModel searchPaginated(List<Object[]> list, int offset, int pagesize);
	
/*	public PaginatedListImpl getPagedObject(PaginatedListImpl page,Criterion... criterions);
	
	public PaginatedListImpl getPagedObject(PaginatedListImpl page,String orderBy, boolean isAsc,
			   Criterion... criterions);*/
	
	public PagerModel searchPaginated(String hql,Object param);
	
	public PagerModel searchPaginated(String hql,Object[] params);
	
	public PagerModel searchPaginated(String hql,int offset,int pagesize);
	
	public PagerModel searchPaginated(String hql,Object param,int offset,int pagesize);
	
	public PagerModel searchPaginated(String hql,Object[] params,int offset,int pagesize);
	
	public PagerModel searchPaginated(Criteria criteria);
	
	public PagerModel searchPaginated(Criteria criteria, int offset, int pagesize);
	
	public List<Map> getListBySql(String sql,int begin,int end) throws DBException;
	
	public Map getOneBySql(final String sql) throws DBException;
	
	public List<Map> getListBySql(String sql) throws DBException;
}

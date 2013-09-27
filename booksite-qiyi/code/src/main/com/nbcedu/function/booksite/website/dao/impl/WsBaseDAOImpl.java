package com.nbcedu.function.booksite.website.dao.impl;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.nbcedu.function.booksite.website.dao.WsBaseDAO;
import com.nbcedu.function.booksite.website.exception.DBException;


@Repository
public abstract class WsBaseDAOImpl<T extends Serializable> extends HibernateDaoSupport implements WsBaseDAO<T> {

	/**
	 * LOG4J日志
	 */
	protected final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 当前泛型实际实体类的CLASS对象
	 */
	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public WsBaseDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 */
	@SuppressWarnings( {"unchecked", "unused"})
	private T get(Class<T> entityClass, Serializable id) {
		Assert.notNull(id);
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 根据ID获取当前实体类
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		Assert.notNull(id);
		return (T) this.getHibernateTemplate().get(this.persistentClass, id);
	}

	/**
	 * 修改实体，更新数据值到数据库
	 * 
	 * @param entity
	 * @return
	 */
	public Object update(T entity) {
		Assert.notNull(entity);
		getHibernateTemplate().update(entity);
		return entity;
	}

	/**
	 * 修改或者保存当前对象
	 * 
	 * @param entity
	 * @return
	 */
	public Object saveOrUpdate(T entity) {
		Assert.notNull(entity);
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	public Object save(T entity) {
		Assert.notNull(entity);
		getHibernateTemplate().save(entity);
		return entity;
	}

	/**
	 * @param entity
	 * @return
	 */
	public Object merge(T entity) {
		Assert.notNull(entity);
		return getHibernateTemplate().merge(entity);
	}

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 */
	public void remove(T entity) {
		Assert.notNull(entity);
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 根据实体主键，删除实体对象
	 * 
	 * @param id
	 * @return
	 */
	public T removeById(Serializable id) {
		Assert.notNull(id);
		T entity = load(id);
		getHibernateTemplate().delete(entity);
		return entity;
	}

	/**
	 * 根据实体主键，删除实体对象
	 * 
	 * @param id
	 * @return
	 */
	public T removeById(String id) {
		Assert.notNull(id);
		T entity = load(id);
		getHibernateTemplate().delete(entity);
		return entity;
	}

	/**
	 * 通过缓存的形式获取对象。 区别GET方式
	 * 
	 * @param id
	 * @return
	 */
	public T load(Serializable id) {
		Assert.notNull(id);
		return load(id, false);
	}

	@SuppressWarnings("unchecked")
	public T load(Serializable id, boolean lock) {
		Assert.notNull(id);
		T entity = null;
		if (lock) {
			entity = (T) getHibernateTemplate().load(getPersistentClass(), id, LockMode.UPGRADE);
		} else {
			entity = (T) getHibernateTemplate().load(getPersistentClass(), id);
		}
		return entity;
	}

	/**
	 * 获取全部对象.
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 缺省值情况下获取全部对象
	 */
	public List<T> getAll() {
		return this.getAll(this.persistentClass);
	}

	/**
	 * 获取默认的全部实体
	 * 
	 * @param <T>
	 * @param orderBy 排序的字段
	 * @param isAsc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc)
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(this.persistentClass).addOrder(Order.asc(orderBy)));
		else
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(this.persistentClass).addOrder(Order.desc(orderBy)));
	}

	/**
	 * 获取全部对象,带排序字段与升降序参数.
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(Class<T> entityClass, String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc)
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(Order.asc(orderBy)));
		else
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(Order.desc(orderBy)));
	}

	/**
	 * 输出SESSION所有对象，保存数据到数据库中
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 清空SESSION中的所有缓存
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 更新SESSION缓存对象
	 * 
	 * @param entity
	 */
	public void refresh(T entity) {
		getSession().refresh(entity);
	}

	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置. 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * <strong> 参数hql可以带上占位符：UPDATE User set sort=? where userId=?; </strong>
	 * 
	 * @param values 可变参数.JDK1.5的新特性
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/*
	 * @see core.dao.BaseDao#createSqlQuery(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Query createSqlQuery(String sql, Object... values) {
		Assert.hasText(sql);
		Query query = getSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 创建Criteria对象.
	 * 
	 * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
	 */
	public Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(this.persistentClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 * 
	 * @see #createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		Assert.hasText(orderBy);

		Criteria criteria = createCriteria(criterions);

		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 * 
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object... values) {
		Assert.notNull(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 按照hql经行分页查询
	 * 
	 * @param hql
	 * @param begin
	 * @param maxSiz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String hql, final int begin, final int maxSiz) {
		Assert.notNull(hql);
		return (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				return session.createQuery(hql).setFirstResult(begin).setMaxResults(maxSiz).list();
			}
		});
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return createCriteria(Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询唯一对象.
	 * 
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueBy(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * 
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	/*
	 * public boolean isUnique(Object entity, String uniquePropertyNames) {
	 * Assert.hasText(uniquePropertyNames); Criteria criteria =
	 * createCriteria().setProjection(Projections.rowCount()); String[] nameList =
	 * uniquePropertyNames.split(","); try { // 循环加入唯一列 for (String name : nameList) {
	 * criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name))); } //
	 * 以下代码为了如果是update的情况,排除entity自身. String idName = getIdName(); // 取得entity的主键值 Serializable id =
	 * getId(entity); // 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断 if (id != null)
	 * criteria.add(Restrictions.not(Restrictions.eq(idName, id))); } catch (Exception e) {
	 * ReflectionUtils.handleReflectionException(e); } return (Integer) criteria.uniqueResult() == 0; }
	 *//**
	 * 取得对象的主键值,辅助函数.
	 */
	/*
	 * public Serializable getId(Object entity) throws NoSuchMethodException, IllegalAccessException,
	 * InvocationTargetException { Assert.notNull(entity); return (Serializable)
	 * PropertyUtils.getProperty(entity, getIdName()); }
	 */

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(this.persistentClass);
		Assert.notNull(meta, "Class " + persistentClass + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, persistentClass.getSimpleName() + " has no identifier property define.");
		return idName;
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	@SuppressWarnings("unused")
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	@SuppressWarnings("unused")
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 根据属性查询条件获取记录行数
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public int countByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return ((Number) (createCriteria(Restrictions.eq(property, value)).setProjection(
				Projections.rowCount()).uniqueResult())).intValue();
	}

	/**
	 * 获取数据行数的默认方法
	 * 
	 * @return
	 */
	public long countAll() {
		return countAll(this.persistentClass);
	}

	/**
	 * 获取实体的数据行数
	 * 
	 * @param entityClass
	 * @return
	 */
	public long countAll(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
	 * 
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	/*
	 * @SuppressWarnings("unchecked") public PaginatedListImpl pagedQuery(Criteria criteria, PaginatedListImpl
	 * page) { Assert.notNull(criteria); CriteriaImpl impl = (CriteriaImpl) criteria; //
	 * 先把Projection和OrderBy条件取出来,清空两者来执行Count操作 Projection projection = impl.getProjection();
	 * List<CriteriaImpl.OrderEntry> orderEntries; try { orderEntries = (List)
	 * BeanUtils.forceGetProperty(impl, "orderEntries"); BeanUtils.forceSetProperty(impl, "orderEntries", new
	 * ArrayList()); } catch (Exception e) { throw new
	 * InternalError(" Runtime Exception impossibility throw "); } // 执行查询 Integer totalCount = (Integer)
	 * criteria.setProjection(Projections.rowCount()).uniqueResult(); // 将之前的Projection和OrderBy条件重新设回去
	 * criteria.setProjection(projection); if (projection == null) {
	 * criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY); } try {
	 * BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries); } catch (Exception e) { throw new
	 * InternalError(" Runtime Exception impossibility throw "); } // 返回分页对象 if (totalCount < 1){ return page;
	 * } int startIndex = page.getFirstRecordIndex(); List list =
	 * criteria.setFirstResult(startIndex).setMaxResults(page.getPageSize()).list(); page.setList(list);
	 * page.setTotalNumberOfRows(totalCount.intValue()); return page; }
	 */
	/**
	 * 创建新的实体对象
	 */
	public T createNewEntiey() {
		try {
			return getPersistentClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("不能创建实体对象：" + getPersistentClass().getName());
		}
	}

	@SuppressWarnings("unchecked")
	private ClassMetadata getCmd(Class clazz) {
		return (ClassMetadata) this.getCmd(clazz);
	}

	/**********************************************************************************************************
	 * 以下接口以SQLQuery为核心经行业务扩展组合 xiedy
	 **********************************************************************************************************/

	/**
	 * 使用SQL更新数据：insert or update op
	 */
	public void updateBySql(String sql) throws DBException {
		Assert.notNull(sql);
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("通过SQL更新数据异常！出错位置updateBySql(String)中。");
			throw new DBException(e.getMessage());
		} finally {
			this.releaseSession(session);
		}
	}

	/**
	 * 使用hql更新数据：insert or update op
	 */
	public void updateByHql(String hql) throws DBException {
		Assert.notNull(hql);
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery(hql);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("通过HQL更新数据异常！出错位置updateByHql(String)中。");
			throw new DBException(e.getMessage());
		} finally {
			this.releaseSession(session);
		}
	}

	/**
	 * 使用SQL带上占位符的查询语句 如：UPDATE user_dept set sort=? where user_id=? and dept_id=?; 所以params数据的值就是对应着3个占位符的值
	 * 
	 * @param sql
	 * @param params
	 * @throws DBException
	 */
	public void updateBySql(String sql, Object... params) throws DBException {
		Assert.notNull(sql);
		Session session = null;
		try {
			session = this.getSession();
			logger.debug("sql = " + sql);
			SQLQuery query = session.createSQLQuery(sql);
			if (params != null) {
				int i = 0;
				for (Object obj : params) {
					query.setParameter(i, obj);
					i++;
				}
				i = 0;
			}
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("通过SQL更新数据异常！出错位置updateBySql(String,Object[])中。");
			throw new DBException(e.getMessage());
		} finally {
			this.releaseSession(session);
		}
	}

	/**
	 * 使用SQL带上占位符的查询语句 如：UPDATE user_dept set sort=? where user_id=? and dept_id=?; 所以params数据的值就是对应着3个占位符的值
	 * 
	 * @param sql
	 * @param params
	 * @throws DBException
	 */
	public void updateByHql(String hql, Object... params) throws DBException {
		Assert.notNull(hql);
		Session session = null;
		try {
			session = this.getSession();
			logger.debug("sql = " + hql);
			Query query = session.createQuery(hql);
			if (params != null) {
				int i = 0;
				for (Object obj : params) {
					query.setParameter(i, obj);
					i++;
				}
				i = 0;
			}
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("通过Hql更新数据异常！出错位置updateByHql(String,Object[])中。");
			// e.printStackTrace();
			throw new DBException(e.getMessage());
		} finally {
			this.releaseSession(session);
		}
	}

	/**
	 * 使用SQL查询结果，无分页信息
	 * 
	 * @param SQL
	 * @return
	 * @throws DBException
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findBySQL(String sql) throws DBException {
		Assert.notNull(sql);
		Session session = null;
		List<Object[]> result = null;
		try {
			session = super.getSession();
			logger.debug("sql = " + sql);
			result = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			logger.error(e.getCause());
			logger.error("通过SQL查询数据异常！findBySQL(String)中。");
			throw new DBException(e.getMessage());
		} finally {
			this.releaseSession(session);
		}
		return result;
	}

	/**
	 * 使用SQL查询结果，有分页信息
	 * 
	 * @param SQL
	 * @return
	 * @throws DBException
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findBySQL(String sql, int beginIndex, int size) throws DBException {
		Assert.notNull(sql);
		Session session = null;
		List<Object[]> result = null;
		try {
			session = super.getSession();
			logger.debug("sql = " + sql);
			SQLQuery query = session.createSQLQuery(sql);
			query.setFirstResult(beginIndex);
			query.setMaxResults(size);
			result = query.list();
		} catch (Exception e) {
			logger.error("通过SQL查询数据异常！findBySQL(String,int,int)中。");
			throw new DBException(e.getMessage());
		} finally {
			this.releaseSession(session);
		}
		return result;
	}

	/**
	 * 通过HQL获取唯一的实体对象
	 * 
	 * @param hql
	 * @return
	 * @throws DBException
	 */
	public Object uniqueResultByHQL(String hql) throws DBException {
		Assert.notNull(hql);
		Session session = null;
		Object result = null;
		try {
			session = super.getSession();
			logger.debug("hql = " + hql);
			Query qry = session.createQuery(hql);
			result = qry.uniqueResult();
		} catch (Exception e) {
			logger.error("通过HQL查询数据异常！uniqueResultByHQL(String)中。");
			throw new DBException(e.getMessage());
		} finally {
			super.releaseSession(session);
		}
		return result;
	}

	/**
	 * 通过SQL获取单一数据
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	public Object uniqueResultBySQL(String sql) throws DBException {
		Session session = null;
		Object result = null;
		try {
			session = super.getSession();
			logger.debug("sql = " + sql);
			Query qry = session.createSQLQuery(sql);
			result = qry.uniqueResult();
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			super.releaseSession(session);
		}
		return result;
	}

	/**
	 * 解析查询总数sql语句
	 * 
	 * @param sql
	 * @return
	 */
	protected String parseCountSql(String sql) {
		if (!sql.matches(".* (count|COUNT)\\(.+\\) .+"))// 如果语句中已包含count函数，则不作任务解析(当一条统计语句比较复杂时，可自定义统计语句)，否则，按一般规则解析统计语句
		{
			sql = sql.replaceAll("(\\s|　)+", " ");
			int startIndex = sql.indexOf("from ");
			if (startIndex == -1)
				startIndex = sql.indexOf("FROM ");
			int endIndex = sql.lastIndexOf(" group by ");
			if (endIndex == -1) {
				endIndex = sql.lastIndexOf(" GROUP BY ");
			}
			if (endIndex == -1) {
				endIndex = sql.lastIndexOf(" order by ");
			}
			if (endIndex == -1) {
				endIndex = sql.lastIndexOf(" ORDER BY ");
			}
			if (endIndex == -1)
				sql = sql.substring(startIndex);
			else
				sql = sql.substring(startIndex, endIndex);

			sql = "select count(*) " + sql;
		}
		return sql;
	}

	/**
	 * 获取数据的总行数
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	public int getRecordCountBySQL(String sql) throws DBException {
		Session session = null;
		sql = parseCountSql(sql);
		int result = 0;
		try {
			session = super.getSession();
			logger.debug("sql = " + sql);
			Query qry = session.createSQLQuery(sql);
			result = ((Number) qry.uniqueResult()).intValue();
		} catch (Exception e) {
			logger.error("获取SQL查询行数异常！getRecordCountBySQL(String)中。");
			throw new DBException(e);
		} finally {
			super.releaseSession(session);
		}
		return result;
	}

}

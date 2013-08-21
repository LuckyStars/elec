package com.nbcedu.function.elec.devcore.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import com.nbcedu.core.exception.DBException;

/**
 * Dao层工具类
 * 
 * @author qinyuan
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class DaoUtil {
	private static final Logger logger = Logger.getLogger(DaoUtil.class);

	/**
	 * 解析查询总数sql，不能适用于有子查询的sql语句 <br/>
	 * 如果语句中已包含count函数，则不作任务解析，否则，按一般规则解析统计语句
	 * @throws Exception 
	 */
	public static String parseCountSql(String sql) throws Exception {
		Pattern p = Pattern.compile("^.+count.+from.+$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		if (!m.matches()) {
			sql = sql.replaceAll("\\s+", " ");
			int startIndex = sql.toUpperCase().indexOf("FROM ");
			if (startIndex == -1) {
				String errorInfo = "不合法的分页查询总数SQL/HQL： " + sql;
				logger.error(errorInfo);
				throw new DBException(errorInfo);
			}

			sql = "SELECT COUNT(*) " + sql.substring(startIndex);
		}

		return sql;
	}


	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数
	 */
	public static Number countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;

		Field f = null;
		try {
			f = DaoUtil.getAccessibleField(impl, "orderEntries");
			orderEntries = (List) f.get(impl);
			f.set(impl, new ArrayList());
		} catch (Exception e) {
			logger.error("反射赋值CriteriaImpl失败", e);
			e.printStackTrace();
		}

		// 执行Count查询
		Number totalCountObject = (Number) c.setProjection(Projections.rowCount()).uniqueResult();
		Number totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			f.set(impl, orderEntries);
		} catch (Exception e) {
			logger.error("反射赋值CriteriaImpl失败", e);
			e.printStackTrace();
		}

		return totalCount;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}
}

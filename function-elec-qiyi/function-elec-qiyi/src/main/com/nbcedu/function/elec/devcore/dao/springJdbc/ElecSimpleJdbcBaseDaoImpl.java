package com.nbcedu.function.elec.devcore.dao.springJdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.nbcedu.core.exception.DBException;
import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.devcore.util.DaoUtil;

/**
 * JdbcTemplateImpl实现DAO
 * 
 * @author qinyuan
 * @version 1.0
 */
@Repository
@SuppressWarnings("unchecked")
public class ElecSimpleJdbcBaseDaoImpl implements ElecSimpleJdbcBaseDao {
	protected final Logger logger = Logger.getLogger(ElecSimpleJdbcBaseDaoImpl.class);
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 批量更新
	 */
	public int[] batchUpdate(String[] sqls) {
		return this.jdbcTemplate.batchUpdate(sqls);
	}

	/**
	 * 返回单条记录Bean
	 */
	public <T> T queryForBean(String sql, Class<T> type, Object... args) {
		return (T) this.jdbcTemplate.queryForObject(sql, args, type);
	}

	/**
	 * 查询Beam List
	 */
	public <T> List<T> queryForBeanList(String sql, Class<T> type, Object... args) {
		return this.jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(type));
	}

	/**
	 * 查询map list
	 */
	public List<Map<String, Object>> queryForMapList(String sql, Object... args) {
		return this.jdbcTemplate.queryForList(sql, args);
	}

	/**
	 * 返回单条记录map
	 */
	public Map<String, Object> queryForMap(String sql, Object... args) {
		return (Map<String, Object>) this.jdbcTemplate.queryForMap(sql, args);
	}

	/**
	 * 查询返回特定类型
	 */
	public <T> T queryForObject(String sql, Class<T> type, Object... args) {
		return (T) this.jdbcTemplate.queryForObject(sql, args, type);
	}

	/**
	 * 查询整数int
	 */
	public int queryForInt(String sql, Object... args) {
		return this.jdbcTemplate.queryForInt(sql, args);
	}

	/**
	 * 查询整数long
	 */
	public long queryForLong(String sql, Object... args) {
		return this.jdbcTemplate.queryForLong(sql, args);
	}

	/**
	 * 增加add
	 */
	public int add(String sql, Object... args) {
		return this.jdbcTemplate.update(sql, args);
	}

	/**
	 * 删除delete
	 */
	public int delete(String sql, Object... args) {
		return this.jdbcTemplate.update(sql, args);
	}

	/**
	 * 更新update
	 */
	public int update(String sql, Object... args) {
		return this.jdbcTemplate.update(sql, args);
	}

	/**
	 * 分页 数据转换为bean
	 * 
	 * @throws Exception
	 */
	public <T> List<T> queryForBeanPage(String sql, String countSql, Page page, Class<T> type, Object... args) throws Exception {
		// 获取记录总数
		String hqlStr = countSql;
		if (hqlStr == null) {
			hqlStr = DaoUtil.parseCountSql(sql);
		}
		logger.debug("--- page recordCount ---: " + hqlStr);
		int recordCount = jdbcTemplate.queryForInt(hqlStr, args);
		page.setCount(recordCount);

		// 获取结果集
		if (recordCount == 0) {
			return new ArrayList();
		}

		String pageSQL = createPageSQL(sql, page);
		logger.debug("--- page recordList ---: " + pageSQL);
		List<T> list = this.queryForBeanList(pageSQL, type, args);
		page.setDatas(list);

		return list;
	}

	/**
	 * 分页 数据转换为map
	 */
	public List<Map<String, Object>> queryForMapPage(String sql, String countSql, Page page, Object... args) throws Exception {
		// 获取记录总数
		String hqlStr = countSql;
		if (hqlStr == null) {
			hqlStr = DaoUtil.parseCountSql(sql);
		}
		logger.debug("--- page recordCount ---: " + hqlStr);
		int recordCount = jdbcTemplate.queryForInt(hqlStr, args);
		page.setCount(recordCount);

		// 获取结果集
		if (recordCount == 0) {
			return new ArrayList();
		}

		String pageSQL = createPageSQL(sql, page);
		logger.debug("--- page recordList ---: " + pageSQL);
		List<Map<String, Object>> list = this.queryForMapList(pageSQL, args);
		page.setDatas(list);

		return list;
	}

	/**
	 * 增加add，map参数
	 */
	public int add(String tableName, Map<String, Object> params) throws Exception {
		Map<String, Object> tableViewMap = getTableDesc(tableName);

		StringBuilder sql = new StringBuilder(100);
		StringBuilder sql2 = new StringBuilder(100);
		sql.append("insert into " + tableName + "(");
		sql2.append("values(");

		Map<String, Object> paramstemp = new HashMap<String, Object>();
		for (String tm : params.keySet()) {
			paramstemp.put(tm, params.get(tm));
		}

		List<Object> iList = new ArrayList<Object>();
		for (String tn : tableViewMap.keySet()) {
			if (paramstemp.get(tn) != null) {
				sql.append(tn + ",");
				sql2.append("?,");
				iList.add(paramstemp.get(tn));
			}
		}
		if (iList.size() <= 0) {
			throw new DBException("无效map");
		}

		sql.delete(sql.length() - 1, sql.length());
		sql2.delete(sql2.length() - 1, sql2.length());
		sql.append(") ");
		sql2.append(") ");
		sql.append(sql2);

		int in = this.jdbcTemplate.update(sql.toString(), iList.toArray());
		return in;
	}

	/**
	 * 删除delete，map参数
	 */
	public int delete(String tbName, Map<String, ?> conds) throws Exception {
		List<Object> plist = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("DELETE ");
		sb.append(tbName.toUpperCase()).append(" WHERE ");

		for (String key : conds.keySet()) {
			sb.append(key.toUpperCase()).append('=');
			Object o = conds.get(key);
			sb.append('?');
			plist.add(o);
			sb.append(" AND ");
		}
		sb.delete(sb.length() - 5, sb.length() - 1);

		return this.jdbcTemplate.update(sb.toString(), plist.toArray());
	}

	/**
	 * 更新update，map参数
	 */
	public int update(String tableName, Map _valueMap, Map _conditionMap) throws Exception {
		Map<String, Object> tableViewMap = getTableDesc(tableName);

		Map valueMap = new HashMap();
		Map conditionMap = new HashMap();

		for (Object s : _valueMap.keySet()) {
			if (tableViewMap.containsKey(s)) {
				valueMap.put(s, _valueMap.get(s));
			}
		}
		for (Object s : _conditionMap.keySet()) {
			if (tableViewMap.containsKey(s)) {
				conditionMap.put(s, _conditionMap.get(s));
			}
		}

		List paramList = new ArrayList();
		StringBuilder bufferSql = new StringBuilder();
		bufferSql.append("update " + tableName + " set ");
		Set vSet = valueMap.keySet();
		for (Object key : vSet) {
			bufferSql.append(key + "=?,");
			paramList.add(valueMap.get(key));
		}
		bufferSql.deleteCharAt(bufferSql.length() - 1);

		bufferSql.append(" where 1=1 ");
		Set cSet = conditionMap.keySet();
		for (Object key : cSet) {
			bufferSql.append(" and " + key + "=? ");
			paramList.add(conditionMap.get(key));
		}
		bufferSql.deleteCharAt(bufferSql.length() - 1);

		return this.update(bufferSql.toString(), paramList.toArray());
	}

	/**
	 * 自定义sql、存储过程调用
	 */
	public void execute(String sql) {
		this.jdbcTemplate.execute(sql);

		/*
		 * jdbcTemplate.execute(new ConnectionCallback() {
		 * @Override public Object doInConnection(Connection con) throws SQLException, DataAccessException { String
		 * sql="exec proc_insert_indirectCostShareIsFirst ?,?"; Connection conn = null; String resultStr = ""; CallableStatement
		 * cst = conn.prepareCall(sql); cst.setInt(1, Integer.parseInt(company_id)); cst.registerOutParameter(2, Types.CHAR);
		 * cst.execute(); resultStr = cst.getString(2); return resultStr; } });
		 */
	}

	/**
	 * 构建分页sql
	 */
	protected String createPageSQL(String sql, Page page) {
		int begin = page.getOffset();
		int pageSize = page.getPageSize();

		// MYSQL
		sql = sql + " limit " + (begin) + " , " + pageSize;

		// oracle

		// db2

		return sql;
	}

	/**
	 * 通过表名得到表的数据结构
	 */
	private Map<String, Object> getTableDesc(final String tablename) throws Exception {
		return (Map<String, Object>) this.jdbcTemplate.execute("select * from " + tablename, new PreparedStatementCallback() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				HashMap<String, Object> map = new HashMap<String, Object>();
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData md = rs.getMetaData();
				for (int i = 1; i <= md.getColumnCount(); i++) {
					map.put(md.getColumnName(i), md.getColumnType(i));
				}
				return map;
			}
		});
	}

}

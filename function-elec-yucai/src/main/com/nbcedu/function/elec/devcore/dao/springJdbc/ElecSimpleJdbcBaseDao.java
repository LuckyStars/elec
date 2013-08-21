package com.nbcedu.function.elec.devcore.dao.springJdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.nbcedu.core.page.Page;

/**
 * JdbcTemplateImpl实现DAO
 * 
 * @author qinyuan
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public interface ElecSimpleJdbcBaseDao {

	/**
	 * 获取JdbcTemplate
	 */
	JdbcTemplate getJdbcTemplate();
	
	/**
	 * 批量更新
	 */
	int[] batchUpdate(String[] sqls);

	/**
	 * 返回单条记录Bean
	 */
	<T> T queryForBean(String sql, Class<T> type, Object... args);

	/**
	 * 查询Beam List
	 */
	<T> List<T> queryForBeanList(String sql, Class<T> type, Object... args);

	/**
	 * 查询map list
	 */
	List<Map<String, Object>> queryForMapList(String sql, Object... args);

	/**
	 * 返回单条记录map
	 */
	Map<String, Object> queryForMap(String sql, Object... args);

	/**
	 * 查询返回特定类型
	 */
	<T> T queryForObject(String sql, Class<T> type, Object... args);

	/**
	 * 查询整数int
	 */
	int queryForInt(String sql, Object... args);

	/**
	 * 查询整数long
	 */
	long queryForLong(String sql, Object... args);

	/**
	 * 增加add
	 */
	int add(String sql, Object... args);

	/**
	 * 删除delete
	 */
	int delete(String sql, Object... args);

	/**
	 * 更新update
	 */
	int update(String sql, Object... args);

	/**
	 * 分页 数据转换为bean
	 * 
	 * @throws Exception
	 */
	<T> List<T> queryForBeanPage(String sql, String countSql, Page page, Class<T> type, Object... args) throws Exception;

	/**
	 * 分页 数据转换为map
	 */
	List<Map<String, Object>> queryForMapPage(String sql, String countSql, Page page, Object... args) throws Exception;

	/**
	 * 增加add，map参数
	 */
	int add(String tableName, Map<String, Object> params) throws Exception;

	/**
	 * 删除delete，map参数
	 */
	int delete(String tbName, Map<String, ?> conds) throws Exception;

	/**
	 * 更新update，map参数
	 */
	int update(String tableName, Map _valueMap, Map _conditionMap) throws Exception;

	/**
	 * 自定义sql、存储过程调用
	 */
	void execute(String sql);

}
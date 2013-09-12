package com.nbcedu.function.elec.devcore.util.sqlxml.context;

import java.util.HashMap;
import java.util.Map;

public final class ClassSqlContext {
	private Map<String,String> queryMap ;
	ClassSqlContext(Map<String,String> queryMap){
		super();
		this.queryMap = new HashMap<String, String>(queryMap);//ensure the queryMap be immortal
	}
	/**
	 * 安ID获取SQL语句
	 * @param queryId
	 * @return
	 * @author xuechong
	 */
	public String getQueryById(String queryId){
		return this.queryMap.get(queryId);
	}
}

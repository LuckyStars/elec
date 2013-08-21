package com.nbcedu.function.elec.biz;


import org.json.simple.JSONArray;

/**
 * @author 李斌
 *组织机构biz
 */
public interface ECUserPrivilegeTreeBiz {
	/**
	 * 查询用户中心的组织机构  带有总节点
	 * @return
	 */
	JSONArray findNodes();
}

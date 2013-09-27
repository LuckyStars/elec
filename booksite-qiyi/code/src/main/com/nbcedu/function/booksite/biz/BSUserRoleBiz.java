package com.nbcedu.function.booksite.biz;

import java.util.List;

import com.nbcedu.function.booksite.model.BSUserRole;
import com.nbcedu.function.booksite.website.biz.WsBaseBiz;
import com.nbcedu.function.booksite.website.pager.PagerModel;

public interface BSUserRoleBiz extends WsBaseBiz<BSUserRole>{

	PagerModel list();

	String getPersonJsonString();

	void add(String userIdArray, String roleArray);

	PagerModel search(String userName);

	void remove(String id);
	/**
	 * 按UID查询用户权限
	 * @param userId
	 * @return
	 * @author xuechong
	 */
	BSUserRole findUserRoleByUserId(String userId);
	
	List<BSUserRole> findAllAdmins();

	void add(String userIdArray, String roleArray, String type);
	
}

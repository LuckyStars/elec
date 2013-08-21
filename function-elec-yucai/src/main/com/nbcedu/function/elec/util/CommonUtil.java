package com.nbcedu.function.elec.util;

import com.google.gson.Gson;
import com.nbcedu.function.elec.vo.ECUser;
import com.opensymphony.xwork2.ActionContext;

public class CommonUtil {
	
	private static final Gson gson = new Gson();
	/**
	 * 获取当前登录的用户
	 * @return
	 * @author xuechong
	 */
	public static ECUser getCurrentUser(){
		return (ECUser) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
	}
	
	public static Gson getGson(){
		return gson;
	}
	
}

/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Struts2Utils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package com.nbcedu.function.elec.devcore.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionContext;

public class Struts2Utils {

	 /**
	 * 操作之后向前端输出json 
	 * @param result  返回结果 true false
	 * @param succesMsg true要返回的信息
	 * @param falseMsg  false要返回的信息
	 * @author 李斌
	 */
	@SuppressWarnings("unchecked")
	public static void OutJson(boolean result,String succesMsg,String falseMsg){
		
		JSONObject json=new JSONObject();
		HttpServletResponse response=
			(HttpServletResponse)ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out2=null;
		try {
			out2 = response.getWriter();
			if (result) {
				json.put("state", true);
				json.put("msg", succesMsg);
			}
			else{
				json.put("state", false);
				json.put("msg", falseMsg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out2.print(json);
		out2.flush();
		out2.close();
	}
	  /**
	 * 操作之后向前台直接返回html信息 
	 * @param result
	 * @author 李斌
	 */
	public static void OutWrite(String result){
		HttpServletResponse response= (HttpServletResponse) ActionContext
		.getContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		Writer out2 = null;
		try {
			out2 = response.getWriter();
			out2.write(result);
			out2.flush();
			out2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

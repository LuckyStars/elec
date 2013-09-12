package com.nbcedu.function.elec.devcore.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.nbcedu.core.action.BaseAction;
import com.nbcedu.core.page.Page;

/**
 * 基类
 * 
 * @author qinyuan
 * @version 1.0
 */
public class ElecBaseAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected static final String LIST = "list";
	protected static final String RELOAD = "reload";
	/**
	 * 分页对象
	 */
	protected Page page = new Page();
	
	/**
	 * 查询用iD
	 */
	protected String id;
	
	
	
	/////////////////////////
	////GETTERS&SETTERS///////
	////////////////////////
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public void setServletRequest(HttpServletRequest request) {

		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

package com.nbcedu.function.booksite.action;

import com.nbcedu.function.booksite.biz.BookSiteBiz;
import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.website.action.WsBaseAction;
import com.nbcedu.function.booksite.website.uitl.DateUtil;
import com.nbcedu.function.booksite.website.uitl.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

public class AuditAction  extends WsBaseAction{

	private BookSiteBiz bookSiteBiz;
	private BookSite bookSite;
	private int state=0; //待审批
	private String type ="1";
	private String beginTime="",endTime="";
	
	
	public String list() throws Exception {
		String type =(String) Struts2Util.getRequest().getSession().getAttribute("type");
		pm = bookSiteBiz.findBookSite(null,beginTime,endTime,state,type);
		return "list";
	}
	
	public String update() throws Exception {
		// TODO Auto-generated method stub
		BookSite bookSite = bookSiteBiz.findBookSiteById(id);
		bookSite.setBookSite_statusId(state);
		bookSiteBiz.modify(bookSite);
		return RELOAD;
	}
	
		
	////////////////////////
	/////GETTERS&SETTERS////
	////////////////////////
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setBookSiteBiz(BookSiteBiz bookSiteBiz) {
		this.bookSiteBiz = bookSiteBiz;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	
}

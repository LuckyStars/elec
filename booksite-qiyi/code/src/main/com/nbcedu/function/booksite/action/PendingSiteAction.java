package com.nbcedu.function.booksite.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.nbcedu.core.privilege.model.User;
import com.nbcedu.function.booksite.biz.BookSiteBiz;
import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.website.action.WsBaseAction;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class PendingSiteAction extends WsBaseAction {

	private BookSiteBiz bookSiteBiz;
	private int state=1; //审批通过
	private String beginTime="",endTime="";
	private BookSite bookSite;
	private String dutyExplain;
	
	public String list() throws Exception {
		// TODO Auto-generated method stub
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) session.getAttribute("bookSite_init");
		if (map != null && !map.isEmpty()) {
			User user = (User) map.get("user");
			pm = bookSiteBiz.findBookSite(user.getUid(),beginTime,endTime,state,null);
		}else{
			
		}
		return LIST;
	}
	
	public String update() throws Exception {
		// TODO Auto-generated method stub
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) session.getAttribute("bookSite_init");
		if (map != null && !map.isEmpty()) {
			User user = (User) map.get("user");
			
		BookSite bookSite = bookSiteBiz.findBookSiteById(id);
		bookSite.setDutyExplain(dutyExplain);
		bookSite.setBookSite_statusId(2);
		bookSite.setDutyId(user.getUid());
		bookSite.setDutyName(user.getName());
		bookSiteBiz.modify(bookSite);
		}
		return RELOAD;
	}
	
	public String detail() throws Exception {
		// TODO Auto-generated method stub
		bookSite = bookSiteBiz.findBookSiteById(id);
		return "detail";
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
	public String getDutyExplain() {
		return dutyExplain;
	}

	public void setDutyExplain(String dutyExplain) {
		this.dutyExplain = dutyExplain;
	}

	public BookSiteBiz getBookSiteBiz() {
		return bookSiteBiz;
	}

	public BookSite getBookSite() {
		return bookSite;
	}

	public void setBookSite(BookSite bookSite) {
		this.bookSite = bookSite;
	}
}

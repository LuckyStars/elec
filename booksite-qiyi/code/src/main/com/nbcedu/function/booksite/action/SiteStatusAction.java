package com.nbcedu.function.booksite.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import com.nbcedu.function.booksite.biz.SiteStatusBiz;
import com.nbcedu.function.booksite.model.SiteStatus;
import com.opensymphony.xwork2.ActionContext;

public class SiteStatusAction{
	protected static final Logger logger = Logger.getLogger(SiteStatusAction.class);
	private static final long serialVersionUID = 1L;
	private SiteStatus siteStatus = new SiteStatus();
	private SiteStatusBiz siteStatusBiz;
	private List<SiteStatus> listSiteStatus;
	
	public String addEntityReady(){
		return "toAddSiteStatus";
	}
	public String addEntity(){
		siteStatusBiz.addSiteStatus(siteStatus);
		return "jumpToSiteStatusList";
	}
	@SuppressWarnings("unchecked")
	public String updateEntityReady() {
		ActionContext context = ActionContext.getContext();
		Map params = context.getParameters();
		String[] ids = (String[]) params.get("id");
		siteStatus = siteStatusBiz.findById(ids[0]);
		return "toUpdate";
	}
	public String updateEntity() {
		siteStatusBiz.modifySiteStatusById(siteStatus);
		return "jumpToSiteStatusList";
	}
	public String retrieveAll() {
		listSiteStatus = siteStatusBiz.findAllSiteStatus();
		return "toSiteStatusList";
	}

	/////////////////////////
	/////GETTERS&SETTERS/////
	//////////////////////////
	public SiteStatus getSiteStatus() {
		return siteStatus;
	}
	public void setSiteStatus(SiteStatus siteStatus) {
		this.siteStatus = siteStatus;
	}
	public SiteStatusBiz getSiteStatusBiz() {
		return siteStatusBiz;
	}
	public void setSiteStatusBiz(SiteStatusBiz siteStatusBiz) {
		this.siteStatusBiz = siteStatusBiz;
	}
	public List<SiteStatus> getListSiteStatus() {
		return listSiteStatus;
	}
	public void setListSiteStatus(List<SiteStatus> listSiteStatus) {
		this.listSiteStatus = listSiteStatus;
	}
}

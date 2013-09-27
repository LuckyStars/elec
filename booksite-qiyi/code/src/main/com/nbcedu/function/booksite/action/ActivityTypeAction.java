package com.nbcedu.function.booksite.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nbcedu.function.booksite.biz.ActivityTypeBiz;
import com.nbcedu.function.booksite.biz.SiteBiz;
import com.nbcedu.function.booksite.biz.SiteStatusBiz;
import com.nbcedu.function.booksite.model.ActivityType;
import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.model.SiteStatus;
import com.opensymphony.xwork2.ActionContext;

public class ActivityTypeAction {
	protected static final Logger logger = Logger.getLogger(ActivityTypeAction.class);
	private static final long serialVersionUID = 1L;
	
	private ActivityTypeBiz activityTypeBiz;
	private SiteStatusBiz siteStatusBiz;
	private ActivityType activityType = new ActivityType();
	private SiteStatus siteStatus = new SiteStatus();
	private List<ActivityType> listActivityType;
	private List<SiteStatus> listSiteStatus;
	private Site site;
	private SiteBiz siteBiz;
	private List<Site> listSite;
	
	
	public String addEntityReady() {
		listSiteStatus = siteStatusBiz.findAllSiteStatus();
		return "toAddActivityType";
	}
	/**
	 * 增加
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String addEntity() throws UnsupportedEncodingException {
		
		activityType.setActivityType_name(activityType.getActivityType_name().trim());
		activityType.setActivityType_name(activityType.getActivityType_name());
		activityType.setSiteStatus(siteStatusBiz.findById(siteStatus.getSiteStatus_id()));
		activityTypeBiz.addActivityType(activityType);

		return this.retrieveAllBack();
	}
	/**
	 * 删除
	 */
	@SuppressWarnings("unchecked")
	public String deleteEntity() {
		ActionContext context = ActionContext.getContext();
		Map parameters = context.getParameters();
		String [] activityType_ids = (String[]) parameters.get("activityType_id");
		activityTypeBiz.removeEntity(activityType_ids[0]);
		return this.retrieveAllBack();
	}
	/**
	 * 更新
	 * @throws UnsupportedEncodingException 
	 */
	public String updateEntity() throws UnsupportedEncodingException {
		activityType.setSiteStatus(siteStatusBiz.findById(siteStatus.getSiteStatus_id()));
		activityType.setActivityType_name(activityType.getActivityType_name().trim());
		activityType.setActivityType_sort(activityType.getActivityType_sort());
		activityTypeBiz.modifyActivityType(activityType);
		return this.retrieveAllBack();
	}
	/**
	 * 更新状态
	 */
	@SuppressWarnings("unchecked")
	public String updateTypeStatus() {//TODO
		ActionContext context = ActionContext.getContext();
		Map parameters = context.getParameters();
		String [] actTypeStatus_ids = (String[]) parameters.get("activityTypeStatus_id");
		if (actTypeStatus_ids[0].equals("2") || actTypeStatus_ids[0].equals("4")) { //3 表示撤销，2 停用，4 表示空闲
			siteStatus = siteStatusBiz.findForOtherById(1);  //1表示启用
		} else if (actTypeStatus_ids[0].equals("1")) {
			siteStatus = siteStatusBiz.findForOtherById(2);     //停用
		} else if (actTypeStatus_ids[0].equals("3")) {
			siteStatus = siteStatusBiz.findForOtherById(3);     //撤销
		}
		
		String [] actypeIds = (String[]) parameters.get("activityType_id");
		activityType = activityTypeBiz.findById(actypeIds[0]);
		activityType.setSiteStatus(siteStatus);
		activityTypeBiz.modifyActivityType(activityType);
		listSite = siteBiz.findByType(activityType.getActivityType_id());
		for (Site site : listSite) {
			List<ActivityType> types = new ArrayList<ActivityType>();
		   if (activityType.getSiteStatus().getSiteStatus_checkId().equals(1)) {		//当前的级别为启用
				for(ActivityType actType : site.getActivityTypes()) {
					types.add(actType);				//把有当前级别的场馆更新
				}
		   } else {
			   for (ActivityType actType : site.getActivityTypes()) {
					if(actType.getActivityType_id().equals(activityType.getActivityType_id())) {
					} else {
						types.add(actType);
					}
			}
		   }
		   site.setActivityTypes(types);
		   siteBiz.modifySite(site);
		}
		
		return this.retrieveAllBack();
	}
	/**
	 * 查询所有
	 * @return
	 */
	public String retrieveAllBack() {
		listActivityType = activityTypeBiz.findActivityType();
		listSiteStatus = siteStatusBiz.findAllSiteStatus();
		return "list";
	}
	/**
	 * 根据id查询
	 */
	public String retrieveById() {
		listSiteStatus = siteStatusBiz.findAllSiteStatus();
		this.activityType = this.activityTypeBiz.findById(this.activityType.getActivityType_id());
		siteStatus = activityType.getSiteStatus();
		return "toUpdateActivityType";
	}
	
	////////////////////////
	/////GETTERS&SETTERS////
	////////////////////////
	public ActivityTypeBiz getActivityTypeBiz() {
		return activityTypeBiz;
	}
	public void setActivityTypeBiz(ActivityTypeBiz activityTypeBiz) {
		this.activityTypeBiz = activityTypeBiz;
	}
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	public List<ActivityType> getListActivityType() {
		return listActivityType;
	}
	public void setListActivityType(List<ActivityType> listActivityType) {
		this.listActivityType = listActivityType;
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
	public SiteStatus getSiteStatus() {
		return siteStatus;
	}
	public void setSiteStatus(SiteStatus siteStatus) {
		this.siteStatus = siteStatus;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public SiteBiz getSiteBiz() {
		return siteBiz;
	}
	public void setSiteBiz(SiteBiz siteBiz) {
		this.siteBiz = siteBiz;
	}
	public List<Site> getListSite() {
		return listSite;
	}
	public void setListSite(List<Site> listSite) {
		this.listSite = listSite;
	}
}

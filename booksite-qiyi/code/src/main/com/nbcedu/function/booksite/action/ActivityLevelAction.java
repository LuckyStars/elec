package com.nbcedu.function.booksite.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nbcedu.function.booksite.biz.ActivityLevelBiz;
import com.nbcedu.function.booksite.biz.SiteBiz;
import com.nbcedu.function.booksite.biz.SiteStatusBiz;
import com.nbcedu.function.booksite.model.ActivityLevel;
import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.model.SiteStatus;
import com.opensymphony.xwork2.ActionContext;

public class ActivityLevelAction {
	protected static final Logger logger = Logger.getLogger(ActivityLevelAction.class);
	private static final long serialVersionUID = 1L;

	private ActivityLevel activityLevel = new ActivityLevel();
	private ActivityLevelBiz activityLevelBiz;
	private SiteStatusBiz siteStatusBiz;
	private SiteStatus siteStatus = new SiteStatus();
	private List<ActivityLevel> listActivityLevel;
	private List<SiteStatus> listSiteStatus;
	private Site site;
	private SiteBiz siteBiz;
	private List<Site> listSite;
	
	
	public String addEntityReady() {
		listSiteStatus = siteStatusBiz.findAllSiteStatus();
		return "toAddActivityLevel";
	}
	/**
	 * 增加
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String addEntity() throws UnsupportedEncodingException {
		activityLevel.setActivityLevel_name(activityLevel.getActivityLevel_name().trim());
		activityLevel.setSiteStatus(siteStatusBiz.findForOtherById(1));   //默认为启用
		activityLevelBiz.addLevel(activityLevel);
		return "jumpToActivityLevelListBack";
	}
	/**
	 * 删除
	 */
	@SuppressWarnings("unchecked")
	public String deleteEntity() {
		ActionContext context = ActionContext.getContext();
		Map p = context.getParameters();
		String [] str = (String[]) p.get("activityLevel_id");
		activityLevelBiz.removeLevel(str[0]);
		return "jumpToActivityLevelListBack";
				
	}
	/**
	 * 更新
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String updateEntity() throws UnsupportedEncodingException {
		activityLevel.setActivityLevel_name(activityLevel.getActivityLevel_name().trim());
		activityLevel.setSiteStatus(siteStatusBiz.findById(siteStatus.getSiteStatus_id()));
		activityLevelBiz.modifyLevel(activityLevel);
		return "jumpToActivityLevelListBack";
	}
	/**
	 * 更新状态
	 */
	@SuppressWarnings("unchecked")
	public String updateLevelStatus() {
		ActionContext context = ActionContext.getContext();
		Map parameters = context.getParameters();
		String[] activityLevelStatus_ids = (String[]) parameters.get("activityLevelStatus_id");
		if (activityLevelStatus_ids[0].equals("2") || activityLevelStatus_ids[0].equals("4")) { // 3 表示撤销，2 停用，4表示空闲
			// 进行启用
			siteStatus = siteStatusBiz.findForOtherById(1); // 1表示启用
		} else if (activityLevelStatus_ids[0].equals("1")) {
			// 进行禁用
			siteStatus = siteStatusBiz.findForOtherById(2); // 停用
		} else if (activityLevelStatus_ids[0].equals("3")) {
			// 进行撤销
			siteStatus = siteStatusBiz.findForOtherById(3); // 撤销
		}

		String[] activityLevel_ids = (String[]) parameters.get("activityLevel_id");
		activityLevel = activityLevelBiz.findById(activityLevel_ids[0]);
		activityLevel.setSiteStatus(siteStatus);
		activityLevelBiz.modifyLevel(activityLevel);

		listSite = siteBiz.findByLevel(activityLevel.getActivityLevel_id());
		for (Site site : listSite) {
			List<ActivityLevel> levels = new ArrayList<ActivityLevel>();
			if (activityLevel.getSiteStatus().getSiteStatus_checkId().equals(1)) { // 当前的级别为启用
				for (ActivityLevel actLevel : site.getActivityLevels()) {
					levels.add(actLevel); // 把有当前级别的场馆更新
				}
			} else {
				for (ActivityLevel actLevel : site.getActivityLevels()) {
					if (actLevel.getActivityLevel_id().equals(
							activityLevel.getActivityLevel_id())) {
					} else {
						levels.add(actLevel);
					}
				}
			}
			site.setActivityLevels(levels);
			siteBiz.modifySite(site);
		}

		return "jumpToActivityLevelListBack";
	}
	/**
	 * 后台查询所有级别
	 * @return
	 */
	public String retrieveAllBack() {
		listActivityLevel = activityLevelBiz.findAllLevel();
		return "list";
	}
	/**
	 * 根据id查询
	 * 
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String retrieveById() {
		ActionContext context = ActionContext.getContext();
		Map parameters = context.getParameters();
		String [] activityLevel_ids = (String[]) parameters.get("activityLevel_id");
		activityLevel = activityLevelBiz.findById(activityLevel_ids[0]);
		siteStatus = activityLevel.getSiteStatus();
		return "toUpdateActivityLevel";
	}
	///////////////////////
	////GETTERS&SETTERS/////
	///////////////////////
	public ActivityLevelBiz getActivityLevelBiz() {
		return activityLevelBiz;
	}
	public void setActivityLevelBiz(ActivityLevelBiz activityLevelBiz) {
		this.activityLevelBiz = activityLevelBiz;
	}
	public ActivityLevel getActivityLevel() {
		return activityLevel;
	}
	public void setActivityLevel(ActivityLevel activityLevel) {
		this.activityLevel = activityLevel;
	}
	public List<ActivityLevel> getListActivityLevel() {
		return listActivityLevel;
	}
	public void setListActivityLevel(List<ActivityLevel> listActivityLevel) {
		this.listActivityLevel = listActivityLevel;
	}
	public List<SiteStatus> getListSiteStatus() {
		return listSiteStatus;
	}
	public void setListSiteStatus(List<SiteStatus> listSiteStatus) {
		this.listSiteStatus = listSiteStatus;
	}
	public SiteStatusBiz getSiteStatusBiz() {
		return siteStatusBiz;
	}
	public void setSiteStatusBiz(SiteStatusBiz siteStatusBiz) {
		this.siteStatusBiz = siteStatusBiz;
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

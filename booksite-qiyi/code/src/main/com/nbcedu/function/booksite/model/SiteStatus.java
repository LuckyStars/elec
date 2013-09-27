package com.nbcedu.function.booksite.model;

import java.io.Serializable;
import java.util.Set;
/**
 * 场馆状态表
 * @author Tank
 * siteStatus_id：主键
 * siteStatus_name：状态名称(空闲、启用、停用、撤销)
 * siteStatus_checkId: 用来查找状态(0:空闲，1：启用，2：停用，3：撤销)
 * activityTypes：活动类别
 * activityLevels：活动级别
 * sites：场馆
 *
 */
public class SiteStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	private String siteStatus_id;
	private String siteStatus_name;
	private Integer siteStatus_checkId;
	private Set<ActivityType> activityTypes;
	private Set<ActivityLevel> activityLevels;
	private Set<Site> sites;

	public String getSiteStatus_id() {
		return siteStatus_id;
	}
	public void setSiteStatus_id(String siteStatusId) {
		siteStatus_id = siteStatusId;
	}
	public String getSiteStatus_name() {
		return siteStatus_name;
	}
	public void setSiteStatus_name(String siteStatusName) {
		siteStatus_name = siteStatusName;
	}
	public Set<ActivityType> getActivityTypes() {
		return activityTypes;
	}
	public void setActivityTypes(Set<ActivityType> activityTypes) {
		this.activityTypes = activityTypes;
	}
	public Set<ActivityLevel> getActivityLevels() {
		return activityLevels;
	}
	public void setActivityLevels(Set<ActivityLevel> activityLevels) {
		this.activityLevels = activityLevels;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Set<Site> getSites() {
		return sites;
	}
	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}
	public Integer getSiteStatus_checkId() {
		return siteStatus_checkId;
	}
	public void setSiteStatus_checkId(Integer siteStatusCheckId) {
		siteStatus_checkId = siteStatusCheckId;
	}
	
}

package com.nbcedu.function.booksite.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.nbcedu.function.booksite.util.CnToSpell;
/**
 * 场馆实体
 * @author Tank
 * site_id:主键标识
 * site_name:场馆名称
 * site_status：场馆状态
 * site_picName：上传时场馆图片，区别各个场馆
 * site_editorPicName：编辑后的图片名，用来方便查找服务器上的图片
 * site_area: 场馆面积
 * site_capacity：场馆容量
 * site_address：场馆地址
 * activityLevel：活动级别
 * activityType: 活动类别
 * siteStatus：场馆状态
 * bookSites: 预定场馆信息
 * siteType:场馆类行
 * siteController 场馆负责人
 * site_other:备用
 */
@SuppressWarnings("unchecked")
public class Site implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;
	private String site_id;
	private String site_name;
	private String site_picName;
	private String site_editorPicName;
	private String site_area;
	private String site_capacity;
	private String site_address;
	private String site_other;
	private String siteType;
	private String siteController;
	private List<ActivityLevel> activityLevels;
	private List<ActivityType> activityTypes;
	private SiteStatus siteStatus;
	private Set<BookSite> bookSites;
	private BSUserRole bsUserRole;
	private String roleArray="";
	private String userIdArray;
	private BookSite bookSite;
	
	
	public BookSite getBookSite() {
		return bookSite;
	}
	public void setBookSite(BookSite bookSite) {
		this.bookSite = bookSite;
	}
	public BSUserRole getBsUserRole() {
		return bsUserRole;
	}
	public void setBsUserRole(BSUserRole bsUserRole) {
		this.bsUserRole = bsUserRole;
	}
	public String getRoleArray() {
		return roleArray;
	}
	public void setRoleArray(String roleArray) {
		this.roleArray = roleArray;
	}
	public String getUserIdArray() {
		return userIdArray;
	}
	public void setUserIdArray(String userIdArray) {
		this.userIdArray = userIdArray;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getSite_name() {
		return site_name;
	}
	public String getSiteType() {
		return siteType;
	}
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	public String getSiteController() {
		return siteController;
	}
	public void setSiteController(String siteController) {
		this.siteController = siteController;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	public String getSite_picName() {
		return site_picName;
	}
	public void setSite_picName(String sitePicName) {
		site_picName = sitePicName;
	}
	public String getSite_editorPicName() {
		return site_editorPicName;
	}
	public void setSite_editorPicName(String siteEditorPicName) {
		site_editorPicName = siteEditorPicName;
	}
	public List<ActivityLevel> getActivityLevels() {
		return activityLevels;
	}
	public void setActivityLevels(List<ActivityLevel> activityLevels) {
		this.activityLevels = activityLevels;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<ActivityType> getActivityTypes() {
		return activityTypes;
	}
	public void setActivityTypes(List<ActivityType> activityTypes) {
		this.activityTypes = activityTypes;
	}
	public SiteStatus getSiteStatus() {
		return siteStatus;
	}
	public void setSiteStatus(SiteStatus siteStatus) {
		this.siteStatus = siteStatus;
	}
	public String getSite_other() {
		return site_other;
	}
	public void setSite_other(String siteOther) {
		site_other = siteOther;
	}
	public Set<BookSite> getBookSites() {
		return bookSites;
	}
	public void setBookSites(Set<BookSite> bookSites) {
		this.bookSites = bookSites;
	}
	public String getSite_area() {
		return site_area;
	}
	public void setSite_area(String siteArea) {
		site_area = siteArea;
	}
	public String getSite_capacity() {
		return site_capacity;
	}
	public void setSite_capacity(String siteCapacity) {
		site_capacity = siteCapacity;
	}
	public String getSite_address() {
		return site_address;
	}
	public void setSite_address(String siteAddress) {
		site_address = siteAddress;
	}
	@Override
	public int compareTo(Object o) {
		Site a = (Site) o;  
        String s1 = CnToSpell.getFullSpell(this.site_name); //鑾峰緱姹夊瓧鐨勫叏鎷� 
        String s2 = CnToSpell.getFullSpell(a.getSite_name());  
        return s1.compareTo(s2); //姣旇緝涓や釜瀛楃涓茬殑澶у皬 
	}

	
}

package com.nbcedu.function.booksite.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.nbcedu.function.booksite.util.CnToSpell;

/**
 * 活动类别表
 * @author Tank
 * activityType_id:主键
 * activityType_name：活动类型名称
 * activityType_sort：活动类别排序
 * siteStatus：场馆状态
 * sites：场馆
 * bookSites：预定场馆信息
 *
 */
@SuppressWarnings("unchecked")
public class ActivityType implements Serializable,Comparable{

	private static final long serialVersionUID = 1L;
	private String activityType_id;
	private String activityType_name;
	private Integer activityType_sort;
	private SiteStatus siteStatus;
	private List<Site> sites;
	private Set<BookSite> bookSites;
	
	public String getActivityType_id() {
		return activityType_id;
	}
	public void setActivityType_id(String activityTypeId) {
		activityType_id = activityTypeId;
	}
	public String getActivityType_name() {
		return activityType_name;
	}
	public void setActivityType_name(String activityTypeName) {
		activityType_name = activityTypeName;
	}
	public Integer getActivityType_sort() {
		return activityType_sort;
	}
	public void setActivityType_sort(Integer activityTypeSort) {
		activityType_sort = activityTypeSort;
	}
	
	public SiteStatus getSiteStatus() {
		return siteStatus;
	}
	public void setSiteStatus(SiteStatus siteStatus) {
		this.siteStatus = siteStatus;
	}
	
	public List<Site> getSites() {
		return sites;
	}
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
	public Set<BookSite> getBookSites() {
		return bookSites;
	}
	public void setBookSites(Set<BookSite> bookSites) {
		this.bookSites = bookSites;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int compareTo(Object o) {
		ActivityType a = (ActivityType)o;  
        String s1 = CnToSpell.getFullSpell(this.activityType_name);//获得汉字的全拼  
        String s2 = CnToSpell.getFullSpell(a.getActivityType_name());  
        return s1.compareTo(s2);//比较两个字符串的大小  
	}
	
	
}

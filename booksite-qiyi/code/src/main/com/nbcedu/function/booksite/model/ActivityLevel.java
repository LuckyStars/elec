package com.nbcedu.function.booksite.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.nbcedu.function.booksite.util.CnToSpell;
/**
 * 活动级别表
 * @author Tank
 * siteLevel_id:主键标识
 * siteLevel_name:活动级别名
 * activityLevel_sort:排序标识
 * siteStatus：场馆状态
 * sites：场馆
 * bookSites: 预定场馆信息
 */
@SuppressWarnings("unchecked")
public class ActivityLevel implements Serializable,Comparable{

	private static final long serialVersionUID = 1L;
	private String activityLevel_id;
	private String activityLevel_name;
	private Integer activityLevel_sort;
	
	private SiteStatus siteStatus;
	private List<Site> sites;
	private Set<BookSite> bookSites;
	
	public String getActivityLevel_id() {
		return activityLevel_id;
	}
	public void setActivityLevel_id(String activityLevelId) {
		activityLevel_id = activityLevelId;
	}
	public String getActivityLevel_name() {
		return activityLevel_name;
	}
	public void setActivityLevel_name(String activityLevelName) {
		activityLevel_name = activityLevelName;
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
	
	public Integer getActivityLevel_sort() {
		return activityLevel_sort;
	}
	public void setActivityLevel_sort(Integer activityLevelSort) {
		activityLevel_sort = activityLevelSort;
	}
	@Override
	public int compareTo(Object o) { //实现Comparable接口的方法
		ActivityLevel a = (ActivityLevel)o;  
        String s1 = CnToSpell.getFullSpell(this.activityLevel_name);//获得汉字的全拼  
        String s2 = CnToSpell.getFullSpell(a.getActivityLevel_name());  
        return s1.compareTo(s2);//比较两个字符串的大小  
	}
	
}

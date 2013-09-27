package com.nbcedu.function.booksite.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 预定场馆信息表
 * @author Tank
 * bookSite_id:主键标识
 * bookSite_name:场馆预订名称，从场馆表中获取
 * bookSite_beginTime：预定场馆开始时间
 * bookSite_endTime：预定场馆结束时间
 * bookSite_explain：预定说明，即干什么用
 * bookSite_uploadDateTime：预定场馆表单提交时间
 * bookSite_people：场馆预订人
 * site：场馆对象
 * activityLevels:活动级别
 * activityTypes：活动状态
 * bookSite_statudId:预定是否成功，用于统计
 */
public class BookSite implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bookSite_id;
	private Date bookSite_beginTime;
	private Date bookSite_endTime;
	private String bookSite_beginTimeStr;   //用于接收页面时间字符串
	private String bookSite_endTimeStr;		//用于接收页面时间字符串
	private String bookSite_explain;
	private String bookSite_explain1;		//用于截取显示	
	private  Date bookSite_uploadDateTime;
	private String bookSite_people;
	private Integer bookSite_statusId;  //0表示未完成(管理员未审核)，1(审核通过),
										//2负责人场馆处理已完成,3审核已退回,4表示预定人自己取消,
	private String bookSite_clashId;	//相互冲突的id
//	private Integer version;
	private Site site;
	private ActivityLevel activityLevel;
	private ActivityType activityType;
//	private User user;
	private String userId;
	private String userName;
	private String auditId;
	private String auditName;//审核人的名字
	private Date auditDate;
	private String dutyId;
	private String dutyName;
	private String dutyExplain;
	private Date dutyDate;

	
	
	
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public String getDutyId() {
		return dutyId;
	}
	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getDutyExplain() {
		return dutyExplain;
	}
	public void setDutyExplain(String dutyExplain) {
		this.dutyExplain = dutyExplain;
	}
	public Date getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}
	public String getBookSite_id() {
		return bookSite_id;
	}
	public void setBookSite_id(String bookSiteId) {
		bookSite_id = bookSiteId;
	}
	public Date getBookSite_beginTime() {
		return bookSite_beginTime;
	}
	public void setBookSite_beginTime(Date bookSiteBeginTime) {
		bookSite_beginTime = bookSiteBeginTime;
	}
	public Date getBookSite_endTime() {
		return bookSite_endTime;
	}
	public void setBookSite_endTime(Date bookSiteEndTime) {
		bookSite_endTime = bookSiteEndTime;
	}
	public String getBookSite_explain() {
		return bookSite_explain;
	}
	public void setBookSite_explain(String bookSiteExplain) {
		bookSite_explain = bookSiteExplain;
	}
	public Date getBookSite_uploadDateTime() {
		return bookSite_uploadDateTime;
	}
	public void setBookSite_uploadDateTime(Date bookSiteUploadDateTime) {
		bookSite_uploadDateTime = bookSiteUploadDateTime;
	}
	public String getBookSite_people() {
		return bookSite_people;
	}
	public void setBookSite_people(String bookSitePeople) {
		bookSite_people = bookSitePeople;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}

	public ActivityLevel getActivityLevel() {
		return activityLevel;
	}
	public void setActivityLevel(ActivityLevel activityLevel) {
		this.activityLevel = activityLevel;
	}
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBookSite_beginTimeStr() {
		return bookSite_beginTimeStr;
	}
	public void setBookSite_beginTimeStr(String bookSiteBeginTimeStr) {
		bookSite_beginTimeStr = bookSiteBeginTimeStr;
	}
	public String getBookSite_endTimeStr() {
		return bookSite_endTimeStr;
	}
	public void setBookSite_endTimeStr(String bookSiteEndTimeStr) {
		bookSite_endTimeStr = bookSiteEndTimeStr;
	}
	public Integer getBookSite_statusId() {
		return bookSite_statusId;
	}
	public void setBookSite_statusId(Integer bookSiteStatusId) {
		bookSite_statusId = bookSiteStatusId;
	}
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBookSite_explain1() {
		return bookSite_explain1;
	}
	public void setBookSite_explain1(String bookSiteExplain1) {
		bookSite_explain1 = bookSiteExplain1;
	}
	public String getBookSite_clashId() {
		return bookSite_clashId;
	}
	public void setBookSite_clashId(String bookSiteClashId) {
		bookSite_clashId = bookSiteClashId;
	}
//	public Integer getVersion() {
//		return version;
//	}
//	public void setVersion(Integer version) {
//		this.version = version;
//	}
	
}

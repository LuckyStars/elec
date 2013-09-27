package com.nbcedu.function.booksite.action;

import java.util.List;

import com.nbcedu.function.booksite.biz.BookSiteBiz;
import com.nbcedu.function.booksite.biz.SiteBiz;
import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.website.action.WsBaseAction;

/**
 * TODO 预定管理
 * @author lvShaoWei
 * @since 2013-6-25
 */
public class BSBookSiteAction extends WsBaseAction {

	private static long serialVersionUID = -1158007856996105630L;

	private SiteBiz siteBiz;
	private BookSiteBiz bookSiteBiz;
	private List<Site> listSite;

	private String statusId = "", beginTime = "", endTime = "", levelId = "", typeId = "", siteId = "",
			opId = "0", optionId = "";// 搜索变量


	/**
	 * TODO 搜索
	 * @return
	 * @throws Exception
	 * @author lvshaowei
	 */
	public String search() throws Exception {
		listSite = siteBiz.findByStatus(1);
		if (opId.equals("1")) {
			pm = bookSiteBiz.search(null, beginTime.trim(), endTime.trim(), null, null, null,1);
		} else if (opId.equals("4")) {
			pm = bookSiteBiz.search(null, null, null, null, null, siteId, 1);
		}else{
			pm = bookSiteBiz.search(null, null, null, null, null, null, 1);
		}
		return "search";
	}

	// //////////////////////
	// ///GETTERS&SETTERS////
	// //////////////////////
	public SiteBiz getSiteBiz() {
		return siteBiz;
	}
	public void setSiteBiz(SiteBiz siteBiz) {
		this.siteBiz = siteBiz;
	}
	public BookSiteBiz getBookSiteBiz() {
		return bookSiteBiz;
	}
	public void setBookSiteBiz(BookSiteBiz bookSiteBiz) {
		this.bookSiteBiz = bookSiteBiz;
	}
	public List<Site> getListSite() {
		return listSite;
	}
	public void setListSite(List<Site> listSite) {
		this.listSite = listSite;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
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
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getOpId() {
		return opId;
	}
	public void setOpId(String opId) {
		this.opId = opId;
	}
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

}

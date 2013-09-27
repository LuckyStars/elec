package com.nbcedu.function.booksite.action;


import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.nbcedu.function.booksite.biz.ActivityLevelBiz;
import com.nbcedu.function.booksite.biz.ActivityTypeBiz;
import com.nbcedu.function.booksite.biz.BookSiteBiz;
import com.nbcedu.function.booksite.model.ActivityLevel;
import com.nbcedu.function.booksite.model.ActivityType;
import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.website.action.WsBaseAction;
import com.nbcedu.function.booksite.website.uitl.DateUtil;


/**
 * 我的预订
 * 
 * @author xuechong
 * @since 2013-6-25
 */
public class MySiteAction extends WsBaseAction {

	private static final Logger logger = Logger.getLogger(MySiteAction.class);
	private static final long serialVersionUID = 1L;
	private BookSiteBiz bookSiteBiz;
	private ActivityLevelBiz activityLevelBiz;
	private ActivityTypeBiz activityTypeBiz;
	private BookSite bookSite;
	private List<ActivityType> listActivityType;
	private List<ActivityLevel> listActivityLevel;

	/*private String statusId = "", beginTime = "", endTime = "", levelId = "", typeId = "", siteId = "",
			opId = "1", optionId = "";// 搜索变量
*/
	/**
	 * 跳转至修改
	 * 
	 * @return
	 * @author xuechong
	 * @throws Exception
	 */
	public String gotoEdit() {
		this.bookSite = this.bookSiteBiz.findBookSiteById(this.id);
		listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1);
		listActivityType = activityTypeBiz.findAllTypeByStatusId(1);
		return "gotoEdit";
	}

	/**
	 * 提交更新(异步)
	 * 
	 * @return 当更新成功时返回'success'
	 * @author xuechong
	 */
	public void update() {
		String beginTime = DateUtil.format(this.bookSite.getBookSite_beginTime(), "yyyy-MM-dd HH:mm");
		String endTime = DateUtil.format(this.bookSite.getBookSite_endTime(), "yyyy-MM-dd HH:mm");
		List<BookSite> already = this.bookSiteBiz.findBookSiteByTime(
				this.bookSite.getSite().getSite_id(),
				beginTime, endTime);
		boolean flag = false;
		for (Iterator<BookSite> it = already.iterator(); it.hasNext();) {
			BookSite book = (BookSite) it.next();
			if (book.getBookSite_id().equals(this.getBookSite().getBookSite_id())) {
				it.remove();
				flag = true;
				break;
			}
		}
		if (CollectionUtils.isEmpty(already)) {
			BookSite bs = bookSiteBiz.findById(bookSite.getBookSite_id());
			bs.setBookSite_beginTime(DateUtil.parse(beginTime, "yyyy-MM-dd HH:mm"));
			bs.setBookSite_endTime(DateUtil.parse(endTime, "yyyy-MM-dd HH:mm"));
			bs.setActivityLevel(bookSite.getActivityLevel());
			bs.setActivityType(bookSite.getActivityType());
			bs.setBookSite_explain(bookSite.getBookSite_explain());
			bookSiteBiz.modify(bs);
			this.writeStr("success");
		} else {
			this.writeStr("already");
		}
	}
	
	// /////////////////////////////
	// //////GETTERS&SETTERS//////
	// /////////////////////////////
	public void setBookSiteBiz(BookSiteBiz bookSiteBiz) {
		this.bookSiteBiz = bookSiteBiz;
	}

	public BookSite getBookSite() {
		return bookSite;
	}

	public void setBookSite(BookSite bookSite) {
		this.bookSite = bookSite;
	}

	public List<ActivityType> getListActivityType() {
		return listActivityType;
	}

	public void setListActivityType(List<ActivityType> listActivityType) {
		this.listActivityType = listActivityType;
	}

	public List<ActivityLevel> getListActivityLevel() {
		return listActivityLevel;
	}

	public void setListActivityLevel(List<ActivityLevel> listActivityLevel) {
		this.listActivityLevel = listActivityLevel;
	}

	public ActivityLevelBiz getActivityLevelBiz() {
		return activityLevelBiz;
	}

	public void setActivityLevelBiz(ActivityLevelBiz activityLevelBiz) {
		this.activityLevelBiz = activityLevelBiz;
	}

	public ActivityTypeBiz getActivityTypeBiz() {
		return activityTypeBiz;
	}

	public void setActivityTypeBiz(ActivityTypeBiz activityTypeBiz) {
		this.activityTypeBiz = activityTypeBiz;
	}

}

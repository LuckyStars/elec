package com.nbcedu.function.booksite.action;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.nbcedu.core.privilege.model.User;
import com.nbcedu.function.booksite.biz.ActivityLevelBiz;
import com.nbcedu.function.booksite.biz.ActivityTypeBiz;
import com.nbcedu.function.booksite.biz.BookSiteBiz;
import com.nbcedu.function.booksite.biz.SiteBiz;
import com.nbcedu.function.booksite.biz.SiteStatusBiz;
import com.nbcedu.function.booksite.biz.BSUserRoleBiz;
import com.nbcedu.function.booksite.model.ActivityLevel;
import com.nbcedu.function.booksite.model.ActivityType;
import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.model.SiteStatus;
import com.nbcedu.function.booksite.model.BSUserRole;
import com.nbcedu.function.booksite.util.CommonUtil;
import com.nbcedu.function.booksite.website.action.WsBaseAction;
import com.nbcedu.function.booksite.website.uitl.DateUtil;
import com.nbcedu.function.booksite.website.uitl.Struts2Util;
import com.nbcedu.function.functionsupport.core.FunctionSupportUtil;
import com.nbcedu.function.functionsupport.core.SupportManager;
import com.nbcedu.function.functionsupport.mapping.PortalMessage;
import com.nbcedu.function.functionsupport.util.PropertiesUtil;
import com.opensymphony.xwork2.ActionContext;

public class BookSiteAction extends WsBaseAction {
	
	protected static final Logger logger = Logger.getLogger(BookSiteAction.class);
	private static final long serialVersionUID = 1L;

	private ActivityLevelBiz activityLevelBiz;
	private ActivityTypeBiz activityTypeBiz;
	private BookSiteBiz bookSiteBiz;
	private SiteBiz siteBiz;
	private SiteStatusBiz siteStatusBiz;
	private String userId, userName;
	private int state = -1;
	private String statusId = "", beginTime = "", endTime = "", levelId = "", typeId = "", siteId = "",
			opId = "1", optionId = "";// 搜索变量
	private ActivityLevel activityLevel = new ActivityLevel();
	private ActivityType activityType = new ActivityType();
	private BookSite bookSite = new BookSite();
	private Site site = new Site();
	private SiteStatus siteStatus = new SiteStatus();
	private List<ActivityType> listActivityType;
	private List<ActivityLevel> listActivityLevel;
	private List<BookSite> listBookSite;
	private List<Site> listSite;
	private JFreeChart chart;//这里的JFreeChart的变量名必须为chart
	private String interest;
	private List<User> listUser;
	private FunctionSupportUtil functionSupport = SupportManager.getFunctionSupportUtil();
	
	private BSUserRoleBiz userRoleBiz ;
	/**
	 * 前台查询所有预定信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String retrieveAll() throws IOException {
		
		this.listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1); // 1启用状态
		Collections.sort(listActivityLevel); // 按升序排序
		this.listActivityType = activityTypeBiz.findAllTypeByStatusId(1); // 1启用状态
		Collections.sort(listActivityLevel); // 按升序排序
		this.listSite = siteBiz.findAllSite();
		Collections.sort(listActivityLevel); // 按升序排序
		
		ActionContext context = ActionContext.getContext();
		String siteId = (String) context.getSession().get("site.site_id");
		this.listBookSite = bookSiteBiz.findBookSiteInfoBySiteId(siteId, 1); // 0表示未审核，3表示查询所有
		this.listBookSite = retrieveList(listBookSite, 6); // 截取
		
		JSONArray bookSiteArray = new JSONArray();
		for (BookSite bookSite : this.listBookSite) {
			JSONObject bsJson = new JSONObject();
			bsJson.put("id", bookSite.getBookSite_id());
			bsJson.put("title", bookSite.getBookSite_explain1());
			bsJson.put("start",bookSite.getBookSite_beginTime() != null?bookSite.getBookSite_beginTime().toString():"");
			bsJson.put("end",bookSite.getBookSite_endTime() != null?bookSite.getBookSite_endTime().toString():"");
			bookSiteArray.add(bsJson);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(bookSiteArray.toJSONString());
		response.getWriter().flush();
		response.getWriter().close();
		return "toBookSiteList";
	}

	/**
	 * 前台显示标题长度
	 */
	public List<BookSite> retrieveList(List<BookSite> lists, int titleLong) {
		listBookSite = new ArrayList<BookSite>();
		BookSite bookSite = new BookSite();
		if (lists.size() > 0) {
			for (int i = 0; i < lists.size(); i++) {
				bookSite = lists.get(i);
				String con = null;
				if (bookSite.getBookSite_explain() != null) {
					con = CommonUtil.htmlToText(bookSite.getBookSite_explain());
					String context = "";
					if (con.length() > titleLong) {
						context = con.substring(0, titleLong) + "...";
					} else {
						context = con;
					}
					bookSite.setBookSite_explain1(context);
				}
				listBookSite.add(bookSite);
			}
		}
		return listBookSite;
	}

	/**
	 * 前台根据id查询预定信息
	 */
	public String retrieveFrontById() {
		ActionContext context = ActionContext.getContext();
		String[] bookSiteId = (String[]) context.getParameters().get("bookSiteId");
		bookSite = bookSiteBiz.findBookSiteById(bookSiteId[0]);
		return "detail";
	}

	/**
	 * 预定信息
	 * @return
	 */
	public String info() {
		ActionContext context = ActionContext.getContext();
		String[] bookSiteId = (String[]) context.getParameters().get("bookSiteId");
		bookSite = bookSiteBiz.findBookSiteById(bookSiteId[0]);
		return "info";
	}

	/**
	 *后台查询所有预定信息
	 * @return
	 */
	public String retrieveAllBack() {
		pm = bookSiteBiz.findAllByPm();
		return "list";
	}

	/**
	 * 后台根据id查询预定信息
	 * 
	 * @return
	 */
	public String retrieveByIdBack() {
		ActionContext context = ActionContext.getContext();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String[] bookSiteId = (String[]) context.getParameters().get("bookSite_id");
		if (bookSiteId!=null&&bookSiteId.length >0) {
			bookSite = bookSiteBiz.findBookSiteById(bookSiteId[0]);
			listBookSite = bookSiteBiz.findBookSiteByTime(bookSite.getSite().getSite_id(), 0, 
					df.format(bookSite.getBookSite_beginTime()), df.format(bookSite.getBookSite_endTime()));
			Integer temp = 0;
			if (listBookSite.size() >= 1) {
				for (BookSite s : listBookSite) {
					temp = s.getBookSite_clashId() == null?1:0;// clashid 为空 表示没有冲突
				}
			} else {
				temp = 1;
			}
			context.put("clashIdSize", temp); // 1不冲突 ，0 冲突
			activityType = bookSite.getActivityType();
			activityLevel = bookSite.getActivityLevel();
			site = bookSite.getSite();
		} else {
			bookSite = null;
		}
		return "toBookSiteBackInfo";
	}

	/**
	 * 根据场馆查询预定信息
	 */
	@SuppressWarnings("unchecked")
	public String retrieveSiteAndBookInfoById() {
		listSite = siteBiz.findOpenByStatusId(1);
		ActionContext context = ActionContext.getContext();
		Map parameters = context.getParameters();
		String[] siteId = (String[]) parameters.get("site.site_id");
		if (siteId != null) { // 添加信息前查询
			context.getSession().put("site.site_id", siteId[0]);
			site = siteBiz.findSiteById(siteId[0]);
			listActivityLevel = site.getActivityLevels();
			listActivityType = site.getActivityTypes();
			listBookSite = bookSiteBiz.findBookSiteInfoBySiteId(siteId[0], 0); // 0表示未审核，3表示查询所有
		} else { // 添加信息后查询
			String id = (String) (context.getSession().get("site.site_id"));
			site = siteBiz.findSiteById(id);
			listActivityLevel = site.getActivityLevels();
			listActivityType = site.getActivityTypes();
			listBookSite = bookSiteBiz.findBookSiteInfoBySiteId(id, 0); // 3表示查询所有
		}
		return "toBookSiteList";
	}

	/**
	 * find booksite by statusId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String retrieveByStatusId() {
		ActionContext context = ActionContext.getContext();
		Map parameters = context.getParameters();
		String[] bookSite_statusId = (String[]) parameters.get("bookSite.bookSite_statusId");
		if (bookSite_statusId.length != 0) {
			listBookSite = bookSiteBiz.findBookSiteByStatusId(Integer.parseInt(bookSite_statusId[0]));
		} else {
			listBookSite = null;
		}
		return "toBookSiteList";
	}

	/**
	 * 准备增加
	 */
	public String addFrontEntityReady() {
		listActivityLevel = new ArrayList<ActivityLevel>();
		listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1); // 1启用状态
		listActivityType = new ArrayList<ActivityType>();
		listActivityType = activityTypeBiz.findAllTypeByStatusId(1); // 1启用状态
		listSite = new ArrayList<Site>();
		listSite = siteBiz.findAllSite();
		listBookSite = new ArrayList<BookSite>();
		listBookSite = bookSiteBiz.findAllBookSite();
		return "toAddBookSite";
	}

	public static byte[] b = new byte[1024];

	/**
	 * 增加预定场馆实体
	 * @return
	 * @throws Exception
	 */
	public String addFrontEntity() throws Exception {
		synchronized (b) {
			ActionContext context = ActionContext.getContext();
			User curUser = (User) context.getSession().get("user");
			bookSite.setUserId(curUser.getUid());
			bookSite.setUserName(curUser.getName());
			bookSite.setBookSite_people(curUser.getName());//保存预定人qkp信息
			

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = null;
			date = df.parse(df.format(new Date()));

			bookSite.setBookSite_uploadDateTime(date); // 提交时间
			bookSite.setBookSite_beginTime(df.parse(bookSite.getBookSite_beginTimeStr())); // 开始时间
			bookSite.setBookSite_endTime(df.parse(bookSite.getBookSite_endTimeStr())); // 结束时间

			String siteId = null;
			if (site.getSite_id().isEmpty() || site.getSite_id() == null) {
				siteId = (String) context.getSession().get("siteId");
				bookSite.setSite(siteBiz.findSiteById(siteId)); // 插入场馆

			} else {
				siteId = site.getSite_id();
				bookSite.setSite(siteBiz.findSiteById(site.getSite_id())); // 插入场馆
			}
			context.getSession().put("siteId", siteId);
			if (activityType.getActivityType_id() != null) {
				activityType = activityTypeBiz.findById(activityType.getActivityType_id());
			} else {
				activityType = null;
			}
			bookSite.setBookSite_explain(bookSite.getBookSite_explain());
			bookSite.setActivityType(activityType); // 设置类型
			if (activityLevel.getActivityLevel_id() != null) {
				activityLevel = activityLevelBiz.findById(activityLevel.getActivityLevel_id());
			} else {
				activityLevel = null;
			}
			bookSite.setActivityLevel(activityLevel); // 设置级别

			listBookSite = bookSiteBiz.findBookSiteByTime(bookSite.getSite().getSite_id(), 0, df
					.format(bookSite.getBookSite_beginTime()), df.format(bookSite.getBookSite_endTime())); // 此场馆在此时间段相冲突的预定信息
			if (listBookSite.size() > 1) { // 本身的id为1条
				bookSite.setBookSite_statusId(0); // 设置提交时，预定信息的默认状态
				List<String> list = new ArrayList<String>();

				for (BookSite b : listBookSite) {
					list.add(b.getBookSite_id());
				}
				String clashId = null;
				for (int i = 0 , end = list.size(); i < end; i++) { // 加入所有的冲突对象的主键
					if (clashId != null) {
						clashId = clashId + "," + list.get(i);
					} else {
						clashId = list.get(i);
					}
				}
				for (BookSite b2 : listBookSite) { // 为所有的冲突对象 更新 冲突值字段
					if (b2.getBookSite_clashId() != null) {
						String s = null;
						String temp = b2.getBookSite_clashId();
						String[] str = clashId.split(",");
						for (int i = 0; i < str.length; i++) {
							if (str[i].equals(bookSite.getBookSite_id())) {
								if (s != null) {
									s = s + "," + str[i];
								} else {
									s = str[i];
								}
							}
						}
						s = temp + "," + s;
						b2.setBookSite_clashId(s);
					} else {
						b2.setBookSite_clashId(clashId);
					}
					bookSiteBiz.modifyBookSite(b2);
				}
			} else {
				bookSite.setBookSite_statusId(0); // 设置提交时，预定信息的默认状态
			}
			String bsiteId = bookSiteBiz.addBookSite(bookSite);
			// 发协同消息 门户消息   TODO
			
			sendMsg:{
				if(bsiteId!=null){
					StringBuilder messageContent = new StringBuilder("");
					messageContent.append("用户:");
					messageContent.append(curUser.getName());
					messageContent.append("提交了场馆预定申请");
					Collection<BSUserRole> receivers = this.userRoleBiz.findAllAdmins();
					Collection<String> receiverUids = new LinkedList<String>();
					for (BSUserRole receiver : receivers) {
						receiverUids.add(receiver.getUserId());
					}
					//发送协同消息
					this.functionSupport.sendOAMessage(curUser.getUid(), receiverUids, messageContent.toString());
					PortalMessage portalMessage = new PortalMessage();
					portalMessage.setMessageId(bsiteId);
					portalMessage.setContent(messageContent.toString());
					portalMessage.setFunctionName("bookSite");
					String moduleName = PropertiesUtil.findPropertieValue("appconfig.properties", "BOOKSITE.CNNAME");
					String msgType = PropertiesUtil.findPropertieValue("appconfig.properties", "BOOKSITE.TYPE");
					portalMessage.setMessageType(msgType);
					portalMessage.setModuleName(moduleName);
					String url = "bookSite/retrieveByIdBackBookSite.action?bookSite_id=" + bsiteId;
					portalMessage.setUrl(url);
					portalMessage.setTitle(messageContent.toString());
					portalMessage.setReceiverUids(receiverUids);
					//发送门户消息
					this.functionSupport.sendPortalMsg(portalMessage, null);
				}
			}
		}
		return "jumpToBookSiteList";
	}

	/**
	 * 判断是否冲突
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	public void checkAddFrontEntity() throws ParseException, IOException {
		ActionContext context = ActionContext.getContext();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String[] siteId = (String[]) context.getParameters().get("siteId");
		String[] beginTime = (String[]) context.getParameters().get("beginTime");
		String[] endTime = (String[]) context.getParameters().get("endTime");

		bookSite.setBookSite_beginTime(df.parse(beginTime[0])); // 开始时间
		bookSite.setBookSite_endTime(df.parse(endTime[0])); // 结束时间

		listBookSite = bookSiteBiz.findBookSiteByTime(siteId[0], 0, beginTime[0], endTime[0]);
		boolean b = false; // // true:冲突,false:不冲突
		if (listBookSite.size() == 0) {
			b = false;
		} else {
			b = true;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(String.valueOf(b).trim());
	}

	/**
	 * 判断是否冲突
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	public void checkUpdateFrontEntity() throws ParseException, IOException {
		ActionContext context = ActionContext.getContext();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String[] siteId = (String[]) context.getParameters().get("siteId");
		String[] beginTime = (String[]) context.getParameters().get("beginTime");
		String[] endTime = (String[]) context.getParameters().get("endTime");

		bookSite.setBookSite_beginTime(df.parse(beginTime[0])); // 开始时间
		bookSite.setBookSite_endTime(df.parse(endTime[0])); // 结束时间

		listBookSite = bookSiteBiz.findBookSiteByTime(siteId[0], 0, beginTime[0], endTime[0]);
		boolean b = false; // // true:冲突,false:不冲突
		if (listBookSite.size() <= 1) {
			b = false;
		} else {
			b = true;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(String.valueOf(b).trim());
	}

	/**
	 * 管理员确定预定信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		siteStatus = siteStatusBiz.findForOtherById(1); // 启动状态
		bookSite = bookSiteBiz.findBookSiteById(bookSite.getBookSite_id());
		site = bookSite.getSite();
		site.setSiteStatus(siteStatus); // 管理员确认预定信息,设置场馆状态
		bookSite.setBookSite_statusId(1); // 同时，设置预定信息为通过状态1
		siteBiz.modifySite(site);
		bookSite.setSite(siteBiz.findSiteById(site.getSite_id()));
		bookSiteBiz.modifyBookSite(bookSite);
		return "jumpToBookSiteListBack";
	}

	
	public String retriveByUserId() {
		ActionContext context = ActionContext.getContext();
		listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1);
		listActivityType = activityTypeBiz.findAllTypeByStatusId(1);
		listSite = siteBiz.findByStatus(1);
		User user = (User) context.getSession().get("user");
		String userId = user.getId();
		String jump = "";
		int opIdNum = Integer.parseInt(this.opId);
		switch (opIdNum) {
		case 1:
			pm = bookSiteBiz.findAllByPmForAll(statusId, userId, beginTime, endTime, null, null, null, "1");
			break;
		case 2:
			pm = bookSiteBiz.findAllByPmForAll(statusId, userId, null, null, levelId, null, null, "2");
			break;
		case 3:
			pm = bookSiteBiz.findAllByPmForAll(statusId, userId, null, null, null, typeId, null, "3");
			break;
		case 4:
			pm = bookSiteBiz.findAllByPmForAll(statusId, userId, null, null, null, null, siteId, "4");
			break;
		default:
			break;
		}
		
		if (statusId.equals("0")) {
			jump = "toMyBookUnCheckList";
			//进入未通过");
		} else if (statusId.equals("1")) {
			jump = "toMyBookList";
			//进入已通过");
		}

		return jump;
	}

	/**
	 * 我的预定详情
	 * 
	 * @return
	 */
	public String retrieveMyBookById() {
		ActionContext context = ActionContext.getContext();
		String[] str = (String[]) context.getParameters().get("bookSite_id");
		bookSite = bookSiteBiz.findBookSiteById(str[0]);
		return "toMyBookDetail";
	}

	/**
	 *准备更新自己的提交的信息
	 * 
	 * @throws ParseException
	 */
	public String updateMyBookInfoReadyById() throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ActionContext context = ActionContext.getContext();
		
		String[] bookSiteId = (String[]) context.getParameters().get("bookSite_id");
		this.bookSite = this.bookSiteBiz.findBookSiteById(bookSiteId[0]);
		this.listActivityLevel = this.bookSite.getSite().getActivityLevels();
		this.listActivityType = this.bookSite.getSite().getActivityTypes();
		this.site = this.bookSite.getSite();
		this.activityType = this.bookSite.getActivityType();
		this.activityLevel = this.bookSite.getActivityLevel();

		Date currentDate = df.parse(df.format(new Date())); // 当前时间
		Date beginTime = null;
		if (this.bookSite.getBookSite_beginTime() != null) {
			beginTime = df.parse(df.format(this.bookSite.getBookSite_beginTime())); // 订单开始时间
			context.put("beginTime", df.format(this.bookSite.getBookSite_beginTime()));
		}
		Date endTime = null;
		if (this.bookSite.getBookSite_endTime() != null) {
			endTime = df.parse(df.format(this.bookSite.getBookSite_endTime())); // 订单结束时间
			context.put("endTime", df.format(this.bookSite.getBookSite_endTime()));
		}
		if (beginTime != null && endTime != null && (currentDate.before(beginTime) || currentDate.before(endTime))) {
			return "toUpdate";
		}
		return "toReturn";
	}

	/**
	 * 更新自己的预定信息
	 * @return
	 * @throws Exception
	 */
	public String updateMyBookInfoById() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");

		String[] beginTime = (String[]) context.getParameters().get("beginTime");
		String[] endTime = (String[]) context.getParameters().get("endTime");
		String[] oldBeginTime = (String[]) context.getParameters().get("oldBeginTime");
		String[] oldEndTime = (String[]) context.getParameters().get("oldEndTime");

		this.bookSite.setBookSite_beginTime(df.parse(beginTime[0]));
		this.bookSite.setBookSite_endTime(df.parse(endTime[0]));

		this.bookSite.setBookSite_statusId(0); // 更新后，预定状态为未审核
		if (this.activityLevel.getActivityLevel_id() != null) {
			this.activityLevel = this.activityLevelBiz.findById(this.activityLevel.getActivityLevel_id());
		} else {
			this.activityLevel = null;
		}
		this.bookSite.setActivityLevel(this.activityLevel); // 设置级别
		if (this.activityType.getActivityType_id() != null) {
			this.activityType = this.activityTypeBiz.findById(this.activityType.getActivityType_id());
		} else {
			this.activityType = null;
		}
		this.bookSite.setActivityType(this.activityType); // 设置类型

		this.site = this.siteBiz.findSiteById(this.site.getSite_id());
		this.bookSite.setSite(this.site); // 设置场馆
		this.bookSite.setBookSite_clashId(null); // 清空本对象的冲突值
		this.bookSiteBiz.modifyBookSite(this.bookSite); // 更新

		this.listBookSite = this.bookSiteBiz.findBookSiteByTime(this.site.getSite_id(), 0, df.format(df
				.parse(oldBeginTime[0])), df.format(df.parse(oldEndTime[0]))); // 此场馆未审核的，在此时间段相冲突的预定信息
		// //查询此场馆所有的未撤销的记录，判断是否有冲突值，然后删除老的时间断的id，添加新的时间断的id进去

		for (BookSite b : this.listBookSite) {
			String temp = null;
			List<String> list1 = new ArrayList<String>();
			if (b.getBookSite_clashId() != null) {
				String[] clashId = b.getBookSite_clashId().split(",");
				for (int i = 0; i < clashId.length; i++) {
					if (clashId[i].equals(this.bookSite.getBookSite_id())) {

					} else {
						list1.add(clashId[i]);
					}
				}
				for (int i = 0; i < list1.size(); i++) { // 把list转为数组
					clashId[i] = list1.get(i);
					if (temp != null) {
						temp = temp + "," + clashId[i];
					} else {
						temp = clashId[i];
					}
					if (list1.size() < 2) { // 只有自己一个了，就不存在冲突，删除clashId
						b.setBookSite_clashId(null);
					} else {
						b.setBookSite_clashId(temp);
					}
				}
				this.bookSiteBiz.modifyBookSite(b); // 更新冲突值
			}
		}
		// 获取当前对象更新后的时间断
		this.listBookSite = this.bookSiteBiz.findBookSiteByTime(this.bookSite.getSite().getSite_id(), 0, df.format(this.bookSite
				.getBookSite_beginTime()), df.format(this.bookSite.getBookSite_endTime())); // 此场馆未审核的，在此时间段相冲突的预定信息
		//更新时间后");
		if (this.listBookSite.size() > 1) { // 本身的id为1条
			this.bookSite.setBookSite_statusId(0);
			List<String> list = new ArrayList<String>();

			for (BookSite b : this.listBookSite) {
				list.add(b.getBookSite_id());
			}
			String clashId = null;
			for (int i = 0; i < list.size(); i++) { // 加入所有的冲突对象的主键
				if (clashId != null) {
					clashId = clashId + "," + list.get(i);
				} else {
					clashId = list.get(i);
				}
			}
			for (BookSite b2 : this.listBookSite) { // 为所有的冲突对象 更新 冲突值字段
				if (b2.getBookSite_clashId() != null) {
					String s = null;
					String temp1 = b2.getBookSite_clashId();
					String[] str = clashId.split(",");
					for (int i = 0; i < str.length; i++) {
						if (str[i].equals(this.bookSite.getBookSite_id())) {
							if (s != null) {
								s = s + "," + str[i];
							} else {
								s = str[i];
							}
						}
					}
					s = temp1 + "," + s;
					b2.setBookSite_clashId(s);
				} else {
					b2.setBookSite_clashId(clashId);
				}
				this.bookSiteBiz.modifyBookSite(b2);
			}

			for (BookSite b3 : this.listBookSite) { // 更新每个对象,本对象所在时间段内的冲突值
				b3.setBookSite_clashId(null); // 首先清除本对象的冲突值
				List<BookSite> list1 = new ArrayList<BookSite>();
				list1 = this.bookSiteBiz.findBookSiteByTime(b3.getSite().getSite_id(), 0, df.format(b3
						.getBookSite_beginTime()), df.format(b3.getBookSite_endTime()));
				String temp = null;
				for (BookSite b4 : list1) {
					if (temp != null) {
						temp = temp + "," + b4.getBookSite_id();
					} else {
						temp = b4.getBookSite_id();
					}
				}
				b3.setBookSite_clashId(temp);
				this.bookSiteBiz.modifyBookSite(b3);
			}
		} else {
			this.bookSite.setBookSite_statusId(1);
		}
		this.bookSiteBiz.modifyBookSite(this.bookSite);
		// 发协同消息
		String content = user.getName() + " 修改了场馆预订信息!";
		List<BSUserRole> userRoleList = this.userRoleBiz.findAllAdmins();
		
		Collection<String> receiversUid = new LinkedList<String>();
		for (BSUserRole u : userRoleList) {
			receiversUid.add(u.getUserId());
		}
		this.functionSupport.sendOAMessage(user.getUid(), receiversUid, content);
		// 发送门户消息
		String url = "bookSite/retrieveAllBackBookSite.action";
		PortalMessage message = new PortalMessage();
		message.setMessageId(this.bookSite.getBookSite_id());
		message.setContent(content);
		message.setUrl(url);
		message.setFunctionName("bookSite");
		String cnName = PropertiesUtil.findPropertieValue("appconfig.properties", "BOOKSITE.CNNAME");
		message.setModuleName(cnName);
		message.setReceiverUids(receiversUid);
		this.functionSupport.sendPortalMsg(message, null);
		return "jumpToMyBook";
	}

	/**
	 * 取消自己的预定信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateToCancelMyBookInfo() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ActionContext context = ActionContext.getContext();
		String[] bookSiteId = (String[]) context.getParameters().get("bookSite_id");
		User user = (User) context.getSession().get("user");

		this.bookSite = this.bookSiteBiz.findBookSiteById(bookSiteId[0]);
		this.bookSite.setBookSite_statusId(2); // 2表示取消
		this.bookSite.setBookSite_clashId(null); // 把自己的冲突值设置为null
		this.bookSiteBiz.modifyBookSite(this.bookSite);

		// listBookSite = bookSiteBiz.findAllBookSite();
		this.listBookSite = this.bookSiteBiz.findBookSiteByTime(this.bookSite.getSite().getSite_id(), 0, df.format(bookSite
				.getBookSite_beginTime()), df.format(this.bookSite.getBookSite_endTime())); // 此场馆未审核的，在此时间段相冲突的预定信息

		for (BookSite b : this.listBookSite) {
			String temp = null;
			List<String> list1 = new ArrayList<String>();
			if (b.getBookSite_clashId() != null) {
				String[] clashId = b.getBookSite_clashId().split(",");
				for (int i = 0; i < clashId.length; i++) {
					if (clashId[i].equals(this.bookSite.getBookSite_id())) {
					} else {
						list1.add(clashId[i]);
					}
				}
				for (int i = 0; i < list1.size(); i++) { // 把list转为数组
					clashId[i] = list1.get(i);
					if (temp != null) {
						temp = temp + "," + clashId[i];
					} else {
						temp = clashId[i];
					}
					if (list1.size() < 2) { // 只有自己一个了，就不存在冲突，删除clashId
						b.setBookSite_clashId(null);
					} else {
						b.setBookSite_clashId(temp);
					}
				}
				bookSiteBiz.modifyBookSite(b); // 更新冲突值
			}
		}

		// 发协同消息
		String content = user.getName() + " 取消了场馆预订信息!";
		List<BSUserRole> userRoleList = this.userRoleBiz.findAllAdmins();
		Collection<String> receiversUid = new LinkedList<String>();
		for (BSUserRole u : userRoleList) {
			receiversUid.add(u.getUserId());
		}
		this.functionSupport.sendOAMessage(user.getUid(), receiversUid, content);
		// 发送门户消息
		//String functioName = FunctionUtils.getFunctionNameCN("bookSite");
		String url = "bookSite/retrieveAllBackBookSite.action";
//		portalAccessor.sendMsgToPortal(bookSite.getBookSite_id(), content, content, url, functioName,
//				receiveIds);//TODO
		PortalMessage message = new PortalMessage();
		message.setMessageId(bookSite.getBookSite_id());
		message.setContent(content);
		message.setUrl(url);
		message.setFunctionName("bookSite");
		String cnName = PropertiesUtil.findPropertieValue("appconfig.properties", "BOOKSITE.CNNAME");
		String msgType = PropertiesUtil.findPropertieValue("appconfig.properties", "BOOKSITE.TYPE");
		message.setMessageType(msgType);
		message.setModuleName(cnName);
		message.setReceiverUids(receiversUid);
		this.functionSupport.sendPortalMsg(message, null);
		return "jumpToMyBook";
	}

	/**
	 * 管理员取消预定信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cancelBookInfo() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ActionContext context = ActionContext.getContext();
		String[] bookSiteId = (String[]) context.getParameters().get("bookSite_id");
		//User user = (User) context.getSession().get("user");

		this.bookSite = this.bookSiteBiz.findBookSiteById(bookSiteId[0]);
		this.bookSite.setBookSite_statusId(2); // 2表示取消
		this.bookSite.setBookSite_clashId(null); // 把自己的冲突值设置为null
		this.bookSiteBiz.modifyBookSite(this.bookSite);

		this.listBookSite = this.bookSiteBiz.findBookSiteByTime(this.bookSite.getSite().getSite_id(), 0, df.format(this.bookSite
				.getBookSite_beginTime()), df.format(this.bookSite.getBookSite_endTime())); // 此场馆未审核的，在此时间段相冲突的预定信息

		for (BookSite b : this.listBookSite) {
			String temp = null;
			List<String> list1 = new ArrayList<String>();
			if (b.getBookSite_clashId() != null) {//"有冲突值
				String[] clashId = b.getBookSite_clashId().split(",");
				for (int i = 0; i < clashId.length; i++) {
					if (!clashId[i].equals(this.bookSite.getBookSite_id())) {
						list1.add(clashId[i]);
					} 
				}
				for (int i = 0; i < list1.size(); i++) { // 把list转为数组
					clashId[i] = list1.get(i);
					if (temp != null) {
						temp = temp + "," + clashId[i];
					} else {
						temp = clashId[i];
					}
					if (list1.size() < 2) { // 只有自己一个了，就不存在冲突，删除clashId
						b.setBookSite_clashId(null);
					} else {
						b.setBookSite_clashId(temp);
					}
				}
				this.bookSiteBiz.modifyBookSite(b); // 更新冲突值
			}
		}
		return "jumpToBookSiteListBack";
	}

	/**
	 * 发送信息
	 */
	public String retrieveToSendMessage() {
		return "";
	}

	/**
	 * 导入到excel中
	 * @throws IOException
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	@SuppressWarnings("unchecked")
	public String retrieveXLS() throws RowsExceededException, WriteException, IOException {
		ActionContext context = ActionContext.getContext();
		Map map = context.getParameters();
		String str1 = null;
		String[] str = (String[]) map.get("statisticsId");
		if (str[0].equals("1")) {
			str1 = retrieveSiteXLSTable();
		} else if (str[0].equals("2")) {
			str1 = retrieveLevelXLSTable();
		}
		return str1;
	}

	/**
	 * 场馆xls表
	 * @return
	 */
	public String retrieveSiteXLSTable() throws IOException, RowsExceededException, WriteException {
		ActionContext context = ActionContext.getContext();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String today = (String) context.getSession().get("today");
		File xlsFile = new File(ServletActionContext.getServletContext().getRealPath(File.separator)
				+ "function"+File.separator+"function-bookSite"+File.separator+"xlsFile" +File.separator + today);
		File xlsPNG = new File(ServletActionContext.getServletContext().getRealPath(File.separator)
				+ "function"+File.separator+"function-bookSite"+File.separator+"xlsPNG"+File.separator + today);
		String xlsPath = xlsFile + ".xls";
		context.getSession().put("xlsFile", today + ".xls");
		// 导出数据到excel
		WritableWorkbook workBook = Workbook.createWorkbook(new File(xlsPath)); // 创建/打开excel
		// 生成名为"场馆使用统计图"的工作表，参数0表示这是第一页
		WritableSheet sheet1 = workBook.createSheet("场馆使用统计图", 0);
		// 向excel中插入图片
		File fileImage = new File(xlsPNG + ".png");
		// WritableImage 图像操作
		WritableImage image = new WritableImage(0, 0, 10, 20, fileImage);
		sheet1.addImage(image);

		// 生成名为"场馆使用统计表"的工作表，参数0表示这是第一页
		WritableSheet sheet = workBook.createSheet("场馆使用统计表", 0);
		Label label = null;
		// jxl.write.Number numb = null;
		int row = 0; // 行
		int col = 0; // 列
		label = new Label(col++, row, "场馆名称", retrieveTitle());
		sheet.addCell(label);
		label = new Label(col++, row, "活动级别", retrieveTitle());
		sheet.addCell(label);
		label = new Label(col++, row, "活动类型", retrieveTitle());
		sheet.addCell(label);
		label = new Label(col++, row, "开始时间", retrieveTitle());
		sheet.addCell(label);
		label = new Label(col++, row, "结束时间", retrieveTitle());
		sheet.addCell(label);
		label = new Label(col++, row, "备注", retrieveTitle());
		sheet.addCell(label);
		label = new Label(col++, row, "预订人", retrieveTitle());
		sheet.addCell(label);

		col = 0;
		row = 1;

		sheet.setRowView(0, 400); // 设置0+1行（第一行）的行高
		sheet.setColumnView(0, 40); // 设置0+1列（第1列）的列宽
		sheet.setColumnView(1, 15); // 设置1+1列（第2列）的列宽

		// 按场馆次数
		this.listSite = this.siteBiz.findAllSite();
		for (Site b : this.listSite) {
			this.listBookSite = this.bookSiteBiz.findBookSiteInfoBySiteId(b.getSite_id(), 1); // 获取此场馆的所有通过预定信息
			for (BookSite bs : this.listBookSite) {
				label = new Label(col, row, b.getSite_name(), retrieveNormolCell()); // 场馆名称
				sheet.addCell(label);
				label = new Label(++col, row, bs.getActivityLevel().getActivityLevel_name(),
						retrieveNormolCell()); // 活动级别
				sheet.addCell(label);
				label = new Label(++col, row, bs.getActivityType().getActivityType_name(),
						retrieveNormolCell()); // 活动类型
				sheet.addCell(label);
				label = new Label(++col, row, df.format(bs.getBookSite_beginTime()), retrieveNormolCell()); // 开始时间
				sheet.addCell(label);
				label = new Label(++col, row, df.format(bs.getBookSite_endTime()), retrieveNormolCell()); // 结束时间
				sheet.addCell(label);
				label = new Label(++col, row, bs.getBookSite_explain(), retrieveNormolCell()); // 备注
				sheet.addCell(label);
				label = new Label(++col, row, bs.getUserName(), retrieveNormolCell()); // 预定人
				sheet.addCell(label);
				col = 0;
				row++;
			}

		}
		// 写入数据并关闭文件
		workBook.write();
		workBook.close();
		return "toDownLoad";
	}

	/**
	 * 活动级别xls表
	 * 
	 * @return
	 */
	public String retrieveLevelXLSTable() throws IOException, RowsExceededException, WriteException {
		ActionContext context = ActionContext.getContext();
		System.out.println(context.getSession().get("beginTime"));
		String beginTime = (String) context.getSession().get("beginTime");
		String endTime = (String) context.getSession().get("endTime");
		String today = (String) context.getSession().get("today");
		File xlsFile = new File(ServletActionContext.getServletContext().getRealPath(File.separator)
				+ "function"+File.separator+"function-bookSite"+File.separator+"xlsFile"+File.separator + today);
		File xlsPNG = new File(ServletActionContext.getServletContext().getRealPath(File.separator)
				+ "function"+File.separator+"function-bookSite"+File.separator+"xlsPNG"+File.separator + today);

		String xlsPath = xlsFile + ".xls";
		context.getSession().put("xlsFile", today + ".xls");
		// 导出数据到excel
		WritableWorkbook workBook = Workbook.createWorkbook(new File(xlsPath)); // 创建/打开excel

		// 生成名为"场馆使用统计图"的工作表，参数0表示这是第一页
		WritableSheet sheet1 = workBook.createSheet("场馆使用统计图", 0);
		// 向excel中插入图片
		File fileImage = new File(xlsPNG + ".png");
		// WritableImage 图像操作
		WritableImage image = new WritableImage(0, 0, 10, 20, fileImage);
		sheet1.addImage(image);

		// 生成名为"场馆使用统计表"的工作表，参数0表示这是第一页
		WritableSheet sheet = workBook.createSheet("场馆使用统计表", 0);
		Label label = null;
		jxl.write.Number numb = null;
		int row = 0; // 行
		int col = 0; // 列

		col = 0;
		row = 1;
		label = new Label(0, 0, "活动级别名称", retrieveTitle());
		sheet.addCell(label);
		label = new Label(1, 0, "次数", retrieveTitle());
		sheet.addCell(label);
		sheet.setRowView(0, 400); // 设置0+1行（第一行）的行高
		sheet.setColumnView(0, 40); // 设置0+1列（第1列）的列宽
		sheet.setColumnView(1, 15); // 设置1+1列（第2列）的列宽

		// 按活动级别
		listActivityLevel = activityLevelBiz.findAllLevel();
		for (ActivityLevel a : listActivityLevel) {
			label = new Label(col, row, a.getActivityLevel_name(), retrieveNormolCell()); // 级别名称
			sheet.addCell(label);
			if (beginTime != null && endTime != null) {
				numb = new jxl.write.Number(++col, row++, bookSiteBiz.findByLevelCount(a
						.getActivityLevel_id(), 1, beginTime, endTime), retrieveNormolCell()); // 预定次数
			} else {
				numb = new jxl.write.Number(++col, row++, bookSiteBiz.findByLevelCount(a
						.getActivityLevel_id(), 1, null, null), retrieveNormolCell()); // 预定次数
			}
			sheet.addCell(numb);
			col = 0;
		}
		// //向excel中插入图片
		// File fileImage = new File(xlsPNG + ".png");
		// //WritableImage 图像操作
		// WritableImage image = new WritableImage(5, 5 , 2 , 3 , fileImage); //从A5开始 跨2行3个单元格
		// sheet.addImage(image);
		// 写入数据并关闭文件
		workBook.write();
		workBook.close();
		return "toDownLoad";
	}

	/**
	 * 在jsp页面显示JFREECHART
	 * @return
	 * @throws ParseException
	 */
	public String retrieveJfreeChart() throws ParseException {
		ActionContext context = ActionContext.getContext();
		String[] str = (String[]) context.getParameters().get("statisticsId");
		String begin = bookSite.getBookSite_beginTimeStr();
		String end = bookSite.getBookSite_endTimeStr();
		Integer statisticsId = Integer.parseInt(str[0]);
		if ((begin == null || begin.length() == 0) && (end == null || end.length() == 0)) {
			context.getSession().put("beginTime", null);
			context.getSession().put("endTime", null);
			// 默认就是场馆次数
			if (statisticsId == 1) { // 1表示场馆次数统计
				chart = retrieveSiteJfreeChart(null, null);
			} else if (statisticsId == 2) { // 2表示按级别统计
				chart = retrieveLevelJfreeChart(null, null);
			}
		} else if (begin.equals("查询所有") || end.equals("查询所有")) {
			context.getSession().put("beginTime", null);
			context.getSession().put("endTime", null);
			//日期为空
			// 默认就是场馆次数
			if (statisticsId == 1) { // 1表示场馆次数统计
				chart = retrieveSiteJfreeChart(null, null);
			} else if (statisticsId == 2) { // 2表示按级别统计
				chart = retrieveLevelJfreeChart(null, null);
			}
		} else {
			context.getSession().put("beginTime", begin);
			context.getSession().put("endTime", end);
			context.put("beginTime", begin);
			context.put("endTime", end);
			// 默认就是场馆次数
			if (statisticsId == 1) { // 1表示场馆次数统计
				chart = retrieveSiteJfreeChart(begin, end);
			} else if (statisticsId == 2) { // 2表示按级别统计
				chart = retrieveLevelJfreeChart(begin, end);
			}
		}
		context.put("statisticsId", str[0]);
		return "showChart";
	}

	/**
	 * 场馆次数jfreechart图
	 * @return
	 */
	public JFreeChart retrieveSiteJfreeChart(String str1, String str2) {
		ActionContext context = ActionContext.getContext();
		// 得到当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar rightNow = Calendar.getInstance();
		Date now = rightNow.getTime();
		String today = sdf.format(now).toString() + "_" + new Random().nextInt(1000);
		// 判断上传目标文件夹是否存在，如果不存在就创建一个
		java.io.File uploadFile = new java.io.File(ServletActionContext.getServletContext().getRealPath(File.separator)
				+ "function"+File.separator+"function-bookSite"+File.separator+"xlsFile");
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		File filePNG = new File(ServletActionContext.getServletContext().getRealPath(File.separator)
				+ "function"+File.separator+"function-bookSite"+File.separator+"xlsPNG");
		if (!filePNG.exists()) {
			filePNG.mkdirs();
		}
		context.getSession().put("today", today);

		// 创建数据集 图上显示的信息
		CategoryDataset dataset = retrieveSiteDataSet(str1, str2);
		// 设置标题样式
		chart = ChartFactory.createBarChart3D("按场馆次数统计结果", // 图表标题
				"场馆名称", // 目录轴的显示标签
				"使用次数", // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				true, // 是否生成工具
				false); // 是否生成URL链接

		// 设置字体
		Font charFont = new Font("", 14, 14); // "隶书"
		// 解决中文问题
		chart.getTitle().setFont(charFont); // 标题
		chart.getLegend().setItemFont(charFont); // 图例

		// 图标区域对象，图中每一个小区域
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// x轴
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(charFont);
		domainAxis.setTickLabelFont(charFont);
		// 设置距离图片左侧距离
		domainAxis.setLowerMargin(0.1);
		// 设置距离图片右侧距离
		domainAxis.setUpperMargin(0.1);

		// y轴
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setNumberFormatOverride(new DecimalFormat("0")); // 数据轴数据标签的显示格式,整型(默认为double型)
		rangeAxis.setAutoTickUnitSelection(false); // 步长自动设置(默认为true)
		// rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits()); //纵轴单元为整型
		rangeAxis.setLabelFont(charFont);
		// 设置最高一个柱与图片顶端的距离
		rangeAxis.setUpperMargin(0.1);
		// 设置最低一个柱与图片底端的距离
		rangeAxis.setLowerMargin(0.15);
		
		// //把报表保存为本地文件
		try {
			File images = new File(ServletActionContext.getServletContext().getRealPath(File.separator)
					+ "function"+File.separator+"function-bookSite"+File.separator+"xlsPNG"+File.separator + today + ".png");
			context.put("images", images.getName());
			ChartUtilities.saveChartAsPNG(images, chart, 600, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chart;
	}

	/**
	 * 活动级别jfreechart图
	 * @return
	 */
	public JFreeChart retrieveLevelJfreeChart(String str1, String str2) {
		ActionContext context = ActionContext.getContext();
		// 得到当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar rightNow = Calendar.getInstance();
		Date now = rightNow.getTime();
		String today = sdf.format(now).toString() + "_" + new Random().nextInt(1000);
		// 判断上传目标文件夹是否存在，如果不存在就创建一个
		java.io.File uploadFile = new java.io.File(ServletActionContext.getServletContext().getRealPath((File.separator) + "function"+File.separator+"function-bookSite"+File.separator+"xlsFile"));
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		File filePNG = new File(ServletActionContext.getServletContext().getRealPath(File.separator)
				+ "function"+File.separator+"function-bookSite"+File.separator+"xlsPNG");
		if (!filePNG.exists()) {
			filePNG.mkdirs();
		}
		context.getSession().put("today", today);

		// 创建数据集 图上显示的信息
		CategoryDataset dataset = retrieveLevelDataSet(str1, str2);
		// 设置标题样式
		chart = ChartFactory.createBarChart3D("按活动级别统计结果", // 图表标题
				"级别名称", // 目录轴的显示标签
				"使用次数", // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				true, // 是否生成工具
				false); // 是否生成URL链接

		// 设置字体
		Font charFont = new Font("", 14, 14);
		// 解决中文问题
		chart.getTitle().setFont(charFont); // 标题
		chart.getLegend().setItemFont(charFont); // 图例

		// 图标区域对象，图中每一个小区域
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// x轴
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(charFont);
		domainAxis.setTickLabelFont(charFont);
		// 设置距离图片左侧距离
		domainAxis.setLowerMargin(0.1);
		// 设置距离图片右侧距离
		domainAxis.setUpperMargin(0.1);

		// y轴
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setLabelFont(charFont);
		rangeAxis.setNumberFormatOverride(new DecimalFormat("0")); // 数据轴数据标签的显示格式,整型(默认为double型)
		rangeAxis.setAutoTickUnitSelection(false); // 步长自动设置(默认为true)
		// 设置最高一个柱与图片顶端的距离
		rangeAxis.setUpperMargin(0.1);
		// 设置最低一个柱与图片底端的距离
		rangeAxis.setLowerMargin(0.15);
		// //把报表保存为本地文件
		try {
			File images = new File(ServletActionContext.getServletContext().getRealPath("/")
					+ "function/function-bookSite/xlsPNG/" + today + ".png");
			context.put("images", images.getName());
			ChartUtilities.saveChartAsPNG(images, chart, 600, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chart;
	}

	/**
	 * 获取场馆次数数据集对象
	 * @return
	 */
	private CategoryDataset retrieveSiteDataSet(String str1, String str2) {
		List<Site> list = siteBiz.findAllSite();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int count;
		for (Site s : list) {
			count = 0;
			count = bookSiteBiz.findBySiteCount(s.getSite_id(), 1, str1, str2); // 1表示已经审核通过完成的
			// dataset.setValue(count, s.getSite_name(), s.getSite_name());
			dataset.addValue(count, s.getSite_name(), s.getSite_name());
		}
		// DatasetUtilities可以设置 复杂数据集
		return dataset;
	}

	/**
	 * 获取级别数据集对象
	 * 
	 * @return
	 */
	private CategoryDataset retrieveLevelDataSet(String str1, String str2) {
		listActivityLevel = activityLevelBiz.findAllLevel();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int count;
		for (ActivityLevel a : listActivityLevel) {
			count = 0;
			count = bookSiteBiz.findByLevelCount(a.getActivityLevel_id(), 1, str1, str2); // 1表示已经审核通过完成的
			dataset.addValue(count, a.getActivityLevel_name(), a.getActivityLevel_name());
		}
		return dataset;
	}

	/**
	 * 设置excel标题样式
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static WritableCellFormat retrieveTitle() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 14);
		try {
			font.setColour(Colour.BLACK); // 黑色字体
			font.setBoldStyle(font.BOLD);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			e.printStackTrace();
		}

		return format;
	}

	/**
	 * 设置其他单元格样式
	 * 
	 * @return
	 */
	public static WritableCellFormat retrieveNormolCell() { // 12号字体,上下左右居中
		WritableFont font = new WritableFont(WritableFont.TIMES, 12);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			// format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK); //,带黑色边框
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 文件下载开始
	 */
	private String file; // 需要下载的文件名,通过链接传过来
	public void setFile(String file) {
		this.file = file;
	}
	// getFile()是处理保存中文文件名带来的问题，主要是用来处理保存文件时中文名字乱码问题
	public String getFile() {
		String fileName = "";
		try {
			fileName = new String(file.getBytes(), "ISO8859-1"); // 把file转换成ISO8859-1编码格式
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fileName;

	}

	// 对于上边的文件路径，给出他的输入流(实际文件资源)，对应在配置文件中的InputName属性名
	// 只要得到该方法中的InputStream，后面的事就好办了(注意这里的路径，路径错了会报异常java.lang.IllegalArgumentException: Can not find a
	// java.io.InputStream with the name [inputStream] in the invocation stack. Check the <param
	// name="inputName"> tag specified for this action
	// ).getResourceAsStream("/UploadFiles" + "\\" + file)
	public InputStream getInputStream() throws FileNotFoundException {
		InputStream in = null;
		in = ServletActionContext.getServletContext().getResourceAsStream(
				File.separator + "function"+File.separator+"function-booksite"+File.separator+"xlsFile"+File.separator + file);
		return in;
	}

	// execute方法只需返回SUCCESS
	public String execute() throws Exception {
		// 这里可加入权限控制
		return "success";
	}

	public String search() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1);
		listActivityType = activityTypeBiz.findAllTypeByStatusId(1);
		listSite = siteBiz.findByStatus(1);
		if (state != -1) {
			pm = bookSiteBiz.search(user.getUid(), null, null, null, null, null, state);
		} else {
			if (opId.equals("1")) {
				pm = bookSiteBiz.search(user.getUid(), beginTime.trim(), endTime.trim(), null, null, null,
						state);
			} else if (opId.equals("2")) {
				pm = bookSiteBiz.search(user.getUid(), null, null, null, levelId, null, state);
			} else if (opId.equals("3")) {
				pm = bookSiteBiz.search(user.getUid(), null, null, typeId, null, null, state);
			} else if (opId.equals("4")) {
				pm = bookSiteBiz.search(user.getUid(), null, null, null, null, siteId, state);
			}
		}
		return "search";
	}

	public String detail() throws Exception {
		bookSite = bookSiteBiz.findBookSiteById(id);
		return "detail";
	}

	public String input() throws Exception {
		bookSite = bookSiteBiz.findBookSiteById(id);
		listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1);
		listActivityType = activityTypeBiz.findAllTypeByStatusId(1);
		return "input";
	}

	public String modify() throws Exception {
		BookSite bs = bookSiteBiz.findById(bookSite.getBookSite_id());
		bs.setBookSite_beginTime(DateUtil.parse(beginTime, "yyyy-MM-dd HH:mm"));
		bs.setBookSite_endTime(DateUtil.parse(endTime, "yyyy-MM-dd HH:mm"));
		bs.setActivityLevel(bookSite.getActivityLevel());
		bs.setActivityType(bookSite.getActivityType());
		bs.setBookSite_explain(bookSite.getBookSite_explain());
		bookSiteBiz.modify(bs);
		return RELOAD;
	}
	/**
	 * 进入检查方法
	 * @return
	 * @throws Exception
	 */
	public String checkTime() throws Exception {
		listBookSite = bookSiteBiz.findBookSiteByTime(siteId, beginTime, endTime);
		// true:冲突,false:不冲突
		if (listBookSite != null && listBookSite.size() > 0) {
			Struts2Util.renderText("true", "encoding:utf-8");
		} else {
			Struts2Util.renderText("false", "encoding:utf-8");
		}
		return null;
	}

	public String cancel() throws Exception {
		bookSite = bookSiteBiz.findById(id);
		bookSite.setBookSite_statusId(4);// 设置取消
		bookSiteBiz.modify(bookSite);
		return RELOAD;
	}

	////////////////////////
	/////GETTERS&SETTERS////
	////////////////////////
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public BookSiteBiz getBookSiteBiz() {
		return bookSiteBiz;
	}
	public void setBookSiteBiz(BookSiteBiz bookSiteBiz) {
		this.bookSiteBiz = bookSiteBiz;
	}
	public SiteBiz getSiteBiz() {
		return siteBiz;
	}
	public void setSiteBiz(SiteBiz siteBiz) {
		this.siteBiz = siteBiz;
	}
	public List<User> getListUser() {
		return listUser;
	}
	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}
	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public BookSite getBookSite() {
		return bookSite;
	}
	public void setBookSite(BookSite bookSite) {
		this.bookSite = bookSite;
	}
	public List<BookSite> getListBookSite() {
		return listBookSite;
	}
	public void setListBookSite(List<BookSite> listBookSite) {
		this.listBookSite = listBookSite;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public List<Site> getListSite() {
		return listSite;
	}
	public void setListSite(List<Site> listSite) {
		this.listSite = listSite;
	}
	public ActivityLevelBiz getActivityLevelBiz() {
		return activityLevelBiz;
	}
	public void setActivityLevelBiz(ActivityLevelBiz activityLevelBiz) {
		this.activityLevelBiz = activityLevelBiz;
	}
	public List<ActivityLevel> getListActivityLevel() {
		return listActivityLevel;
	}
	public void setListActivityLevel(List<ActivityLevel> listActivityLevel) {
		this.listActivityLevel = listActivityLevel;
	}
	public ActivityLevel getActivityLevel() {
		return activityLevel;
	}
	public void setActivityLevel(ActivityLevel activityLevel) {
		this.activityLevel = activityLevel;
	}
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
	public SiteStatus getSiteStatus() {
		return siteStatus;
	}
	public void setSiteStatus(SiteStatus siteStatus) {
		this.siteStatus = siteStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setUserRoleBiz(BSUserRoleBiz userRoleBiz) {
		this.userRoleBiz = userRoleBiz;
	}
}

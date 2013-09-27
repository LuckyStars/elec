package com.nbcedu.function.booksite.action;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import com.nbcedu.core.privilege.model.Role;
import com.nbcedu.core.privilege.model.User;
import com.nbcedu.function.booksite.biz.ActivityLevelBiz;
import com.nbcedu.function.booksite.biz.ActivityTypeBiz;
import com.nbcedu.function.booksite.biz.BSUserRoleBiz;
import com.nbcedu.function.booksite.biz.BookSiteBiz;
import com.nbcedu.function.booksite.biz.SiteBiz;
import com.nbcedu.function.booksite.biz.SiteStatusBiz;
import com.nbcedu.function.booksite.model.ActivityLevel;
import com.nbcedu.function.booksite.model.ActivityType;
import com.nbcedu.function.booksite.model.BSUserRole;
import com.nbcedu.function.booksite.model.BookSite;
import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.model.SiteStatus;
import com.nbcedu.function.booksite.website.action.WsBaseAction;

import com.nbcedu.function.booksite.website.uitl.Struts2Util;
import com.opensymphony.xwork2.ActionContext;

public class SiteAction extends WsBaseAction {
	protected static final Logger logger = Logger.getLogger(SiteAction.class);
	private static final long serialVersionUID = 1L;
	private ActivityLevel activityLevel = new ActivityLevel();
	private ActivityType activityType = new ActivityType();
	private Site site = new Site();
	private SiteStatus siteStatus = new SiteStatus();
	private SiteBiz siteBiz;
	private BookSiteBiz bookSiteBiz;
	private BSUserRoleBiz  userRoleBiz;
	private ActivityLevelBiz activityLevelBiz;
	private ActivityTypeBiz activityTypeBiz;
	private SiteStatusBiz siteStatusBiz;
	private List<Site> listSite;
	private List<ActivityLevel> listActivityLevel;
	private List<ActivityType> listActivityType;
	private List<SiteStatus> listSiteStatus;
	private User user;
	private Role role;
	private List<Role> listRole;
	private BSUserRole bsUserRole;
	private BookSite bookSite;
	/*
	 * 这里的JFreeChart的变量名必须为chart
	 */
	private JFreeChart chart;
	private String interest;
	private File picture;
	private String pictureFileName;
	private static final int BUFFER_SIZE = 16 * 1024;

	/**
	 * ready to add
	 */
	public String addEntityReady() {
		listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1);
		listActivityType = activityTypeBiz.findAllTypeByStatusId(1);
		return "toAddSite";
	}

	/**
	 * add site
	 */
	public String addEntity() {
		ActionContext context = ActionContext.getContext();
		String[] typeId = (String[]) context.getParameters().get("activityType_id");
		if (typeId != null) {
			listActivityType = new ArrayList<ActivityType>();
			for (int i = 0,end = typeId.length; i < end ; i++) {
				activityType = activityTypeBiz.findById(typeId[i]);
				listActivityType.add(activityType);
			}
			site.setActivityTypes(listActivityType);
		}
		String[] levelId = (String[]) context.getParameters().get("activityLevel_id");
		if (levelId != null) {
			listActivityLevel = new ArrayList<ActivityLevel>();
			for (int i = 0; i < levelId.length; i++) {
				activityLevel = activityLevelBiz.findById(levelId[i]);
				listActivityLevel.add(activityLevel);
			}
			site.setActivityLevels(listActivityLevel);
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = null;
		if (pictureFileName != null && picture.exists()) {
			newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000)
					+ getExtention(this.getPictureFileName());
			File uploadFile = new File(ServletActionContext.getServletContext().getRealPath(
					"/function/function-booksite/uploadPic"));
			if (!uploadFile.exists()) {
				uploadFile.mkdirs();
			}
			File file = new File(ServletActionContext.getServletContext().getRealPath(
					"/function/function-booksite/uploadPic"), newFileName);
			copy(picture, file);
		}
		
		String siteType = Struts2Util.getRequest().getParameter("siteType");
			site.setSiteType(siteType);
		String userIdArray = Struts2Util.getRequest().getParameter("userIdArray");
			site.setSiteController(userIdArray);
			
			if(siteType!=null && siteType!=""){ //增加角色
				String roleArray="4";
				userRoleBiz.add(userIdArray,roleArray,siteType);
			}
				
		
		site.setSite_picName(pictureFileName);
		site.setSite_editorPicName(newFileName); // the edited name
		site.setSiteStatus(siteStatusBiz.findForOtherById(4)); // 新增场馆的状态默认为空闲
		
		siteBiz.addSite(site);

		return "jumpToSiteListBack";
	}

	/**
	 * get site by id
	 */
	public String retrieveById() {
		listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1);
		listActivityType = activityTypeBiz.findAllTypeByStatusId(1);
		listSiteStatus = siteStatusBiz.findAllSiteStatus();
		ActionContext context = ActionContext.getContext();
		Map parameters = context.getParameters();
		String[] siteIds = (String[]) parameters.get("site_id");
		site = siteBiz.findSiteById(siteIds[0]);
		List<String> list = new ArrayList<String>();
		List<String> list1 = new ArrayList<String>();
		for (ActivityLevel actLevel : site.getActivityLevels()) {
			list.add(actLevel.getActivityLevel_id());
		}
		context.put("level", list);
		for (ActivityType actType : site.getActivityTypes()) {
			list1.add(actType.getActivityType_id());
		}
		context.put("type", list1);
		siteStatus = site.getSiteStatus();
		String userIdArray = site.getSiteController();
		if(userIdArray!=null && !"".equals(userIdArray)){
			userIdArray = "'" +userIdArray +"'";
			userIdArray = userIdArray.replaceAll(",", "','");
			Struts2Util.getRequest().setAttribute("userIdArray", userIdArray);
		}
		return "toUpdate";
	}

	/**
	 * 修改
	 */
	@SuppressWarnings("unchecked")
	public String update() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = null;
		if (pictureFileName != null && picture.exists()) { // 重新上传图片
			newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000)
					+ getExtention(this.getPictureFileName());
			File uploadFile = new File(ServletActionContext.getServletContext().getRealPath(
					"/function/function-booksite/uploadPic"));
			if (!uploadFile.exists()) {
				uploadFile.mkdirs();
			}
			File file = new File(ServletActionContext.getServletContext().getRealPath(
					"/function/function-booksite/uploadPic"), newFileName);
			copy(picture, file);
			site.setSite_picName(pictureFileName);
			site.setSite_editorPicName(newFileName); // the edited name
		} else { // 用以前的图片
			site.setSite_editorPicName(site.getSite_editorPicName());
		}
		site.setSite_address(site.getSite_address());
		site.setSite_area(site.getSite_area());
		site.setSite_capacity(site.getSite_capacity());
		site.setSite_other(site.getSite_other());
		String siteType = Struts2Util.getRequest().getParameter("siteTypeEdit");
			site.setSiteType(siteType);
		String userIdArray = Struts2Util.getRequest().getParameter("userIdArrayEdit");
			site.setSiteController(userIdArray);
		//增加角色
			if(siteType!=null && siteType!=""){ //增加角色
				String roleArray="4";
				userRoleBiz.add(userIdArray,roleArray,siteType);
			}
		ActionContext context = ActionContext.getContext();
		Map pro = context.getParameters();
		String[] actLevelIds = (String[]) pro.get("activityLevel_id");
		String[] actTypeIds = (String[]) pro.get("activityType_id");
		listActivityLevel = new ArrayList<ActivityLevel>();
		// 判断是否重新选择级别
		if (actLevelIds != null) {
			for (int i = 0,end=actLevelIds.length; i < end; i++) {
				activityLevel = activityLevelBiz.findById(actLevelIds[i]);
				listActivityLevel.add(activityLevel);
			}
			site.setActivityLevels(listActivityLevel);
		}

		// 判断是否重新选择类型
		listActivityType = new ArrayList<ActivityType>();
		if (actTypeIds != null) {
			for (int i = 0,end = actTypeIds.length; i <end; i++) {
				activityType = activityTypeBiz.findById(actTypeIds[i]);
				listActivityType.add(activityType);
			}
			site.setActivityTypes(listActivityType);
		}
		siteStatus = siteStatusBiz.findById(siteStatus.getSiteStatus_id());
		site.setSiteStatus(siteStatus); // 更新状态
		siteBiz.modifySite(site);
		//更新成功");
		return "jumpToSiteListBack";
	}

	/**
	 * 修改状态
	 */
	@SuppressWarnings("unchecked")
	public String updateStatus() {
		ActionContext context = ActionContext.getContext();
		Map parameters = context.getParameters();
		String[] siteStatusIds = (String[]) parameters.get("siteStatus_id");
		if (siteStatusIds[0].equals("2") || siteStatusIds[0].equals("4")) { // 3 表示撤销，2 停用，4 状态字段，表示空闲
			siteStatus = siteStatusBiz.findForOtherById(1); // 1表示启用
		} else if (siteStatusIds[0].equals("1")) {
			siteStatus = siteStatusBiz.findForOtherById(2); // 禁用
		} else if (siteStatusIds[0].equals("3")) {
			siteStatus = siteStatusBiz.findForOtherById(3); // 撤销
		}
		String[] siteIds = (String[]) parameters.get("site_id");
		site = siteBiz.findSiteById(siteIds[0]);
		site.setSiteStatus(siteStatus);
		siteBiz.modifySite(site);
		return "jumpToSiteListBack";
	}

	/**
	 * get all site for manage
	 * 
	 * @return
	 */
	public String retrieveAllBack() {
		listActivityLevel = new ArrayList<ActivityLevel>();
		listActivityLevel = activityLevelBiz.findAllLevelByStatusId(1);
		listActivityType = new ArrayList<ActivityType>();
		listActivityType = activityTypeBiz.findAllTypeByStatusId(1);
		pm = siteBiz.findAllByPm(); // 管理场馆
		return "list";
	}

	/**
	 * 前台场馆列表，预定
	 */
	@SuppressWarnings("unchecked")
	public String retrieveOpen() {
		this.pm = siteBiz.findAllByPmForStatusId(1);
		Map<String, Object> map = (Map<String, Object>) ActionContext.getContext().getSession().get("bookSite_init");
		if(map.get("user")!=null){
			this.role = new Role();
			if (ActionContext.getContext().getSession().get("ROLEID").equals("1")) {
				this.role.setName("BOOKSITE_ADMIN");
			} else {
				this.role.setName("BOOKSITE_COMMON");
			}
			return "bookSite";
		}
		return "toLogin";
	}

	/**
	 * 执行文件上传过程
	 * @param src
	 * @param dst
	 */

	private static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件后缀名
	 * @param fileName
	 * @return
	 */
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	/**
	 * 在jsp页面显示JFREECHART
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String retrieveJfreeChart() {
		ActionContext context = ActionContext.getContext();
		Map parameters = (Map) context.getParameters();
		String[] beginTime = (String[]) parameters.get("beginTime");
		String[] endTime = (String[]) parameters.get("endTime");
		chart = this.getJfreeChart(beginTime[0], endTime[0]);
		return "showChart";
	}

	/**
	 * jfreechart图
	 * @return
	 */
	public JFreeChart getJfreeChart(String beginTime, String endTime) {
		ActionContext context = ActionContext.getContext();
		// 得到当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar rightNow = Calendar.getInstance();
		Date now = rightNow.getTime();
		String today = sdf.format(now).toString() + "_" + new Random().nextInt(1000);
		// 判断上传目标文件夹是否存在，如果不存在就创建一个
		java.io.File uploadFile = new java.io.File(ServletActionContext.getServletContext().getRealPath("/")
				+ "function/function-bookSite/xls");
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		File filePNG = new File(ServletActionContext.getServletContext().getRealPath(
				"/function/function-bookSite/xls"), today);

		context.getSession().put("path", filePNG);
		// 创建数据集 图上显示的信息

		CategoryDataset dataset = getDataSet(beginTime, endTime);

		// 设置标题样式
		chart = ChartFactory.createBarChart3D("统计结果", // 图表标题
				"场馆名称", // 目录轴的显示标签
				"使用次数", // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				true, // 是否生成工具
				false); // 是否生成URL链接

		// 设置字体
		Font charFont = new Font("", 12, 12);
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
		// 设置最高一个柱与图片顶端的距离
		rangeAxis.setUpperMargin(0.1);
		// 设置最低一个柱与图片底端的距离
		rangeAxis.setLowerMargin(0.15);
		// //把报表保存为本地文件
		try {
			File file = new File(filePNG + ".png");
			ChartUtilities.saveChartAsPNG(file, chart, 400, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return chart;

	}

	/**
	 * 获取一个演示用的简单数据集对象
	 * 
	 * @return
	 */
	private CategoryDataset getDataSet(String beginTime, String endTime) {
		List<Site> list = siteBiz.findAllSite();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int count;
		for (Site site : list) {
			count = 0; // 设置默认为0
			count = bookSiteBiz.findBySiteCount(site.getSite_id(), 1, beginTime, endTime);
			dataset.setValue(count, site.getSite_name(), site.getSite_name());
		}
		return dataset;
	}

	////////////////////////
	/////GETTERS&SETTERS////
	/////////////////////////
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
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
	public ActivityLevel getActivityLevel() {
		return activityLevel;
	}
	public void setActivityLevel(ActivityLevel activityLevel) {
		this.activityLevel = activityLevel;
	}
	public List<ActivityType> getListActivityType() {
		return listActivityType;
	}
	public void setListActivityType(List<ActivityType> listActivityType) {
		this.listActivityType = listActivityType;
	}
	public String getPictureFileName() {
		return pictureFileName;
	}
	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	public File getPicture() {
		return picture;
	}
	public void setPicture(File picture) {
		this.picture = picture;
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
	public BookSiteBiz getBookSiteBiz() {
		return bookSiteBiz;
	}
	public void setBookSiteBiz(BookSiteBiz bookSiteBiz) {
		this.bookSiteBiz = bookSiteBiz;
	}
	public static int getBufferSize() {
		return BUFFER_SIZE;
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
	public SiteStatus getSiteStatus() {
		return siteStatus;
	}
	public void setSiteStatus(SiteStatus siteStatus) {
		this.siteStatus = siteStatus;
	}
	public List<ActivityLevel> getListActivityLevel() {
		return listActivityLevel;
	}
	public void setListActivityLevel(List<ActivityLevel> listActivityLevel) {
		this.listActivityLevel = listActivityLevel;
	}
	public List<Role> getListRole() {
		return listRole;
	}
	public void setListRole(List<Role> listRole) {
		this.listRole = listRole;
	}

	public BSUserRoleBiz getUserRoleBiz() {
		return userRoleBiz;
	}

	public void setUserRoleBiz(BSUserRoleBiz userRoleBiz) {
		this.userRoleBiz = userRoleBiz;
	}

	public BSUserRole getBsUserRole() {
		return bsUserRole;
	}

	public void setBsUserRole(BSUserRole bsUserRole) {
		this.bsUserRole = bsUserRole;
	}

	public BookSite getBookSite() {
		return bookSite;
	}

	public void setBookSite(BookSite bookSite) {
		this.bookSite = bookSite;
	}
	
	
	
}

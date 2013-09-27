package com.nbcedu.function.booksite.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jdom.JDOMException;

import com.nbcedu.core.privilege.model.User;
import com.nbcedu.function.booksite.biz.BSUserRoleBiz;
import com.nbcedu.function.booksite.biz.SiteBiz;
import com.nbcedu.function.booksite.model.BSUserRole;
import com.nbcedu.function.booksite.model.Site;
import com.nbcedu.function.booksite.website.action.WsBaseAction;
import com.nbcedu.function.booksite.website.uitl.Struts2Util;
import com.opensymphony.xwork2.ActionContext;


public class UserAction extends WsBaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private BSUserRoleBiz  userRoleBiz;
	private String roleArray="";
	private String userIdArray;
	private String personJson;
	private String userName="";
	private SiteBiz siteBiz;
	/**
	 * 跳转到管理员管理
	 */
	public String admin(){
		return "toManageIndex";
	}
	
	public String tree() throws Exception {
		personJson = userRoleBiz.getPersonJsonString();
		if(personJson!=null){
			personJson=personJson.replaceAll(",]","]");
			Struts2Util.renderJson(personJson,"encoding:UTF-8");
		}
		return null;
	}
	
	public String list() throws Exception {
		pm = userRoleBiz.list();
		return LIST;
	}
	
	public String search() throws Exception {
		pm = userRoleBiz.search(userName.trim());
		return "search";
	}
	
	public String addORupdate() throws Exception {
		String type = Struts2Util.getRequest().getParameter("type");
		if(type!=null && type!=""){
		userRoleBiz.add(userIdArray,roleArray,type);
		}
		userRoleBiz.add(userIdArray,roleArray);
		return RELOAD;
	}
	
	public String del() throws Exception {
		userRoleBiz.remove(id);
		return RELOAD;
	}

	/**
	 * 进入场馆预订应用时，获取该用户信息
	 * @throws IOException
	 * @throws JDOMException
	 */
	@SuppressWarnings("unchecked")
	public String index() throws JDOMException, IOException {
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) session.getAttribute("bookSite_init");
		context.getSession().put("ROLEID", "0");
		if (map != null && !map.isEmpty()) {
			User user = (User) map.get("user");
			if(user.getUid().equals("1")){
				context.getSession().put("ROLEID", "1");
				context.getSession().put("user", user);
				return "jumpSiteIndex";
			}
			BSUserRole uRole = this.userRoleBiz.findUserRoleByUserId(user.getUid());
			if (uRole != null) {
				context.getSession().put("ROLEID", uRole.getRoleArray());
				context.getSession().put("TYPE", uRole.getType());
				List<Site> siteList = siteBiz.findSiteByUserId(user.getUid());
				if(siteList!=null && siteList.size()>0){
					context.getSession().put("FZR", "4");
				}else{
					context.getSession().put("FZR", "0");
				}
			}else{
				return "login";//没有权限
			}
			context.getSession().put("user", user);
		}
		// 将原有的直接跳转场馆预订页面改为管理页面
		return "jumpSiteIndex";
	}
	public String retrieveSendMessage(){
		return "toSendMessage";
	}
	
	////////////////////////
	////GETTERS&SETTERS////
	///////////////////////
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getPersonJson() {
		return personJson;
	}
	public String getUserName() {
		return userName.trim();
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public SiteBiz getSiteBiz() {
		return siteBiz;
	}

	public void setSiteBiz(SiteBiz siteBiz) {
		this.siteBiz = siteBiz;
	}

	public void setPersonJson(String personJson) {
		this.personJson = personJson;
	}
	public void setUserRoleBiz(BSUserRoleBiz userRoleBiz) {
		this.userRoleBiz = userRoleBiz;
	}
}

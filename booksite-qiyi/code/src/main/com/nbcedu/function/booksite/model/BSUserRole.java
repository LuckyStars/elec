package com.nbcedu.function.booksite.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * <p>用户角色</p>
 * @author 黎青春
 * Create at:2012-4-12 下午04:54:47
 */
public class BSUserRole implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;   					// 编号
	private String userName;  				//用户名
	private String tel;  					//审核人电话
	private String email;  					//审核人Email
	private String roleArray;  				// 角色集合
	private String userId;  				// 用户编号
	private String state="0";  				//状态 
	private Date createDate = new Date(); 	//创建时间
	private String deleteFlag="1";		//是否删除 1:未删除，0:已删除
	private String type; //1:电教，2：行政
	private String sort;					//排序字段
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoleArray() {
		return roleArray;
	}
	public void setRoleArray(String roleArray) {
		this.roleArray = roleArray;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
}

package com.nbcedu.function.elec.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="t_elec_userprivilege")
public class ECUserPrivilege implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@Id
	@Column(unique=true,length=32,nullable=false)
	@GeneratedValue(generator = "nbc-uuid")
    @GenericGenerator(name = "nbc-uuid",strategy="uuid")
	private String id;
	/**
	 * uid
	 */
	@Column(length=32,nullable=false)
	private String uid;
	/**
	 * pid
	 */
	@Column(length=32,nullable=false)
	private String pid;
	/**
	 * 用户名
	 */
	@Column(length=20,nullable=false)
	private String userName;
	
	/**
	 * 权限
	 */
	@Column(length=255)
	private String privileges;
	/////////////////////////
	/////GETTERS&SETTERS/////
	/////////////////////////
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
}

package com.nbcedu.function.elec.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.nbcedu.function.elec.vo.DeleteableEntity;

/**
 * 地点
 * @author xuechong
 *
 */
@Entity
@Table(name="t_elec_place")
public class ECPlace implements DeleteableEntity{
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
	 * 名称
	 */
	@Column(unique=true,nullable=false,length=50)
	private String name;
	
	/**
	 * 课程类别
	 */
	@Column(nullable=false,name="typename")
	private String typeName;
	/**
	 * 删除状态(1：关闭、0：开启)
	 */
	@Column(nullable=false,name="delstate")
	private Integer delState;
	
	/**
	 * 创建时间
	 */
	@Column(nullable=false,name="createtime")
	private Date createTime;
	/////////////////////////
	/////GETTERS&SETTERS/////
	/////////////////////////
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDelState() {
		return delState;
	}
	public void setDelState(Integer delState) {
		this.delState = delState;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}

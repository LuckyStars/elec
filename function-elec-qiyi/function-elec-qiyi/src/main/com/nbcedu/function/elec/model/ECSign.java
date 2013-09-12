package com.nbcedu.function.elec.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_elec_sign")
public class ECSign implements Serializable{
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
	 * 课程ID
	 */
	@Column(length=32,nullable=false,name="course_id")
	private String courseId;
	/**
	 * 周-课时ID 多个课时时间存到一个字段 以 ,分开
	 */
	@Column(length=9999,nullable=true,name="classhour_id")
	private String classhourIds;
	/**
	 * 报名人uid
	 */
	@Column(length=32,nullable=false,name="stu_id")
	private String stuId;
	
	/**
	 * 报名时间
	 */
	@Column(name="create_date")
	private Date createDate;
	/////////////////////////
	/////GETTERS&SETTERS/////
	/////////////////////////
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getClasshourIds() {
		return classhourIds;
	}
	public void setClasshourIds(String classhourIds) {
		this.classhourIds = classhourIds;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}

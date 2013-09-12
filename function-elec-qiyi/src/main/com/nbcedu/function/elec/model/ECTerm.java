package com.nbcedu.function.elec.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.nbcedu.function.elec.vo.DeleteableEntity;

@Entity
@Table(name="t_elec_term")
public class ECTerm implements DeleteableEntity{
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
	@Column(length=50)
	private String name;
	/**
	 * 开放时间开始
	 */
	@Column(name="open_date_start")
	private Date openDateStart;
	/**
	 * 开放时间结束
	 */
	@Column(name="open_date_end")
	private Date openDateEnd;
	/**
	 * 报名开始
	 */
	@Column(name="sign_date_start")
	private Date signDateStart;
	/**
	 * 报名结束
	 */
	@Column(name="sign_date_end")
	private Date signDateEnd;
	/**
	 * 开课时间
	 */
	@Column(name="lession_date_start")
	private Date lessonDateStart;
	/**
	 * 结课时间
	 */
	@Column(name="lesson_date_end")
	private Date lessonDateEnd;
	/**
	 * 默认上课时间
	 */
	@Column(name="class_time_start")
	@Temporal(TemporalType.TIME)
	private Date classTimeStart;
	/**
	 * 默认下课时间
	 */
	@Column(name="class_time_end")
	@Temporal(TemporalType.TIME)
	private Date classTimeEnd;
	
	/**
	 * 备注
	 */
	@Column(name="comments",length=255)
	private String comments;
	/**
	 * 删除状态
	 * @see com.nbcedu.function.elec.util.Constants.DEL_STATE_REMOVED
	 * @see com.nbcedu.function.elec.util.Constants.DEL_STATE_ENABLED
	 */
	@Column(nullable=false,name="del_state")
	private Integer delState;
	/**
	 * 是否是当前学期
	 */
	@Column(nullable=false,name="current_term")
	private boolean currentTerm;
	/**
	 * 每人最大选课数(0为不限)
	 */
	@Column(nullable=false,name="max_course")
	private Integer maxCourse;
	/**
	 * 创建时间
	 */
	@Column(nullable=false,name="create_date")
	private Date createDate;
	/////////////////////////
	/////GETTERS&SETTERS/////
	/////////////////////////
	public Integer getDelState() {
		return delState;
	}
	public void setDelState(Integer delState) {
		this.delState = delState;
	}
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
	public Date getOpenDateStart() {
		return openDateStart;
	}
	public void setOpenDateStart(Date openDateStart) {
		this.openDateStart = openDateStart;
	}
	public Date getOpenDateEnd() {
		return openDateEnd;
	}
	public void setOpenDateEnd(Date openDateEnd) {
		this.openDateEnd = openDateEnd;
	}
	public Date getSignDateStart() {
		return signDateStart;
	}
	public void setSignDateStart(Date signDateStart) {
		this.signDateStart = signDateStart;
	}
	public Date getSignDateEnd() {
		return signDateEnd;
	}
	public void setSignDateEnd(Date signDateEnd) {
		this.signDateEnd = signDateEnd;
	}
	public Date getLessonDateStart() {
		return lessonDateStart;
	}
	public void setLessonDateStart(Date lessonDateStart) {
		this.lessonDateStart = lessonDateStart;
	}
	public Date getLessonDateEnd() {
		return lessonDateEnd;
	}
	public void setLessonDateEnd(Date lessonDateEnd) {
		this.lessonDateEnd = lessonDateEnd;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public boolean getCurrentTerm() {
		return currentTerm;
	}
	public void setCurrentTerm(boolean currentTerm) {
		this.currentTerm = currentTerm;
	}
	public boolean isCurrentTerm() {
		return currentTerm;
	}
	public Integer getMaxCourse() {
		return maxCourse;
	}
	public void setMaxCourse(Integer maxCourse) {
		this.maxCourse = maxCourse;
	}
	public Date getClassTimeStart() {
		return classTimeStart;
	}
	public void setClassTimeStart(Date classTimeStart) {
		this.classTimeStart = classTimeStart;
	}
	public Date getClassTimeEnd() {
		return classTimeEnd;
	}
	public void setClassTimeEnd(Date classTimeEnd) {
		this.classTimeEnd = classTimeEnd;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}

package com.nbcedu.function.elec.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_elec_course")
public class ECCourse implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@Id
	@Column(unique = true, length = 32, nullable = false)
	@GeneratedValue(generator = "nbc-uuid")
	@GenericGenerator(name = "nbc-uuid", strategy = "uuid")
	private String id;
	/**
	 * 所属哪期兴趣班ID
	 */
	@Column(length = 32, name = "term_id")
	private String termId;
	/**
	 * 课程分类
	 */
	@Column(length = 32, name = "type_id")
	private String typeId;
	/**
	 * 年级范围，多个以，分隔
	 */
	private String gradeIds;
	/**
	 * 年级名称集合，多个以，分隔
	 */
	private String gradeNames;
	/**
	 * 课程名称
	 */
	private String name;
	/**
	 * 限定总人数
	 */
	@Column(length = 5)
	private Integer maxStudentNum;

	/**
	 * 任课老师ID，多个以，分隔
	 */
	@Lob
	private String teacherIds;
	/**
	 * 任课老师姓名，多个以，分隔
	 */
	@Lob
	private String teacherNames;
	/**
	 * 报名开始
	 */
	@Column(name = "sign_startDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date signStartDate;
	/**
	 * 报名结束
	 */
	@Column(name = "sign_endDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date signEndDate;

	/**
	 * 开课日期
	 */
	@Column(name = "class_startDate")
	@Temporal(TemporalType.DATE)
	private Date classStartDate;
	/**
	 * 结课日期
	 */
	@Column(name = "class_endDate")
	@Temporal(TemporalType.DATE)
	private Date classEndDate;

	/**
	 * 每周课时
	 */
	@Column(length = 1)
	private Integer classhourNum;
	/**
	 * 每周课时-详情List
	 */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "eCCourse")
	@OrderBy("weekIndex ASC, weekStartTime ASC")
	private List<ECClasshour> classhourList = new ArrayList<ECClasshour>();

	/**
	 * 上课地点
	 */
	@JoinColumn(name = "start_palce")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private ECPlace startPlace;
	/**
	 * 放学地点
	 */
	@JoinColumn(name = "end_place")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private ECPlace endPlace;
	/**
	 * 课程提示
	 */
	@Column(name = "course_comment", length = 255)
	private String courseComment;
	/**
	 * 是否俱乐部(史家):1-是 0-否
	 */
	@Column(name = "club_state")
	private Integer clubState;

	/**
	 * 课程内容
	 */
	@Lob
	private String courseContent;
	/**
	 * 学生要求
	 */
	@Lob
	private String courseRequire;
	/**
	 * 备 注
	 */
	@Lob
	private String courseNote;
	
	/**
	 * 课程属性(育才)
	 */
	@Column(length = 2)
	private Integer courseAttr;
	
	/**
	 * 录入时间，根据它排序
	 */
	private Date operatorDate;
	
	/**
	 * 课时要求(史家)
	 */
	@Column(name = "classhourRequire")
	private Integer classhourRequire;
	
	// ///////////////////////
	// ///GETTERS&SETTERS/////
	// ///////////////////////
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getMaxStudentNum() {
		return maxStudentNum;
	}

	public void setMaxStudentNum(Integer maxStudentNum) {
		this.maxStudentNum = maxStudentNum;
	}

	public ECPlace getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(ECPlace startPlace) {
		this.startPlace = startPlace;
	}

	public ECPlace getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(ECPlace endPlace) {
		this.endPlace = endPlace;
	}

	public String getCourseComment() {
		return courseComment;
	}

	public void setCourseComment(String courseComment) {
		this.courseComment = courseComment;
	}

	public Integer getClubState() {
		return clubState;
	}

	public void setClubState(Integer clubState) {
		this.clubState = clubState;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(String gradeIds) {
		this.gradeIds = gradeIds;
	}

	public Integer getClasshourNum() {
		return classhourNum;
	}

	public void setClasshourNum(Integer classhourNum) {
		this.classhourNum = classhourNum;
	}

	public List<ECClasshour> getClasshourList() {
		return classhourList;
	}

	public void setClasshourList(List<ECClasshour> classhourList) {
		this.classhourList = classhourList;
	}

	public Date getSignStartDate() {
		return signStartDate;
	}

	public void setSignStartDate(Date signStartDate) {
		this.signStartDate = signStartDate;
	}

	public Date getSignEndDate() {
		return signEndDate;
	}

	public void setSignEndDate(Date signEndDate) {
		this.signEndDate = signEndDate;
	}

	public Date getClassStartDate() {
		return classStartDate;
	}

	public void setClassStartDate(Date classStartDate) {
		this.classStartDate = classStartDate;
	}

	public Date getClassEndDate() {
		return classEndDate;
	}

	public void setClassEndDate(Date classEndDate) {
		this.classEndDate = classEndDate;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	public String getCourseRequire() {
		return courseRequire;
	}

	public void setCourseRequire(String courseRequire) {
		this.courseRequire = courseRequire;
	}

	public String getCourseNote() {
		return courseNote;
	}

	public void setCourseNote(String courseNote) {
		this.courseNote = courseNote;
	}

	public Date getOperatorDate() {
		return operatorDate;
	}

	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}

	public String getGradeNames() {
		return gradeNames;
	}

	public void setGradeNames(String gradeNames) {
		this.gradeNames = gradeNames;
	}

	public String getTeacherIds() {
		return teacherIds;
	}

	public void setTeacherIds(String teacherIds) {
		this.teacherIds = teacherIds;
	}

	public String getTeacherNames() {
		return teacherNames;
	}

	public void setTeacherNames(String teacherNames) {
		this.teacherNames = teacherNames;
	}

	public Integer getCourseAttr() {
		return courseAttr;
	}

	public void setCourseAttr(Integer courseAttr) {
		this.courseAttr = courseAttr;
	}

	public Integer getClasshourRequire() {
		return classhourRequire;
	}

	public void setClasshourRequire(Integer classhourRequire) {
		this.classhourRequire = classhourRequire;
	}

}

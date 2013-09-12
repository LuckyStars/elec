package com.nbcedu.function.elec.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.nbcedu.function.elec.util.WeekType;

@Entity
@Table(name = "t_elec_classhour")
public class ECClasshour implements Serializable {
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
	 * 所属课程
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private ECCourse eCCourse;
	/**
	 * 周
	 */
	@Column(length = 1)
	private Integer weekIndex;
	/**
	 * 周-开始时间
	 */
	@Column(name = "week_startTime")
	@Temporal(TemporalType.TIME)
	private Date weekStartTime;
	/**
	 * 周-结束时间
	 */
	@Column(name = "week_endTime")
	@Temporal(TemporalType.TIME)
	private Date weekEndTime;

	// ///////////////////////
	// ///GETTERS&SETTERS/////
	// ///////////////////////
	public String getWeekIndexStr(){
		return WeekType.getById(this.weekIndex).getName();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ECCourse geteCCourse() {
		return eCCourse;
	}

	public void seteCCourse(ECCourse eCCourse) {
		this.eCCourse = eCCourse;
	}

	public Integer getWeekIndex() {
		return weekIndex;
	}

	public void setWeekIndex(Integer weekIndex) {
		this.weekIndex = weekIndex;
	}

	public Date getWeekStartTime() {
		return weekStartTime;
	}

	public void setWeekStartTime(Date weekStartTime) {
		this.weekStartTime = weekStartTime;
	}

	public Date getWeekEndTime() {
		return weekEndTime;
	}

	public void setWeekEndTime(Date weekEndTime) {
		this.weekEndTime = weekEndTime;
	}

}

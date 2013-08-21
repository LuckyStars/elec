package com.nbcedu.function.elec.vo;

import java.util.*;

import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlData;
import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlModel;

/**
 * 
 * @author bzh
 * 本班报名课程
 */
@ExlModel
public class ECCourseClass {
	
	/**课程ID**/
	private String coursrId;
	/**课程类别ID**/
	private String typeId;
	/**课程类别**/
	@ExlData(sortId=1,title="课程类别")
	private String typeName;
	/**课程名称**/
	@ExlData(sortId=2,title="课程名称")
	private String courseName;
	/**任课老师**/
	@ExlData(sortId=3,title="任课老师")
	private String teacherName;
	/**开课日期**/
	@ExlData(sortId=4,title="开课日期")
	private Date startTime;
	/**结课日期**/
	@ExlData(sortId=5,title="结课日期")
	private Date endTime;
	/**上课时间**/
	@ExlData(sortId=6,title="上课时间")
	private List<String> classTime = new ArrayList<String>();
	/**上课地点**/
	@ExlData(sortId=7,title="上课地点")
	private String lessonPlace;
	/**限定人数**/
	@ExlData(sortId=8,title="限定人数")
	private Integer totolNum;
	/**报名人数**/
	@ExlData(sortId=9,title="报名人数")
	private Integer selectedNum;
	
	public ECCourseClass() {
		
	}
	
	public ECCourseClass(String coursrId, String typeId, String typeName,
			String courseName, String teacherName, Date startTime,
			Date endTime, List<String> classTime, String lessonPlace,
			Integer totolNum, Integer selectedNum) {
		super();
		this.coursrId = coursrId;
		this.typeId = typeId;
		this.typeName = typeName;
		this.courseName = courseName;
		this.teacherName = teacherName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.classTime = classTime;
		this.lessonPlace = lessonPlace;
		this.totolNum = totolNum;
		this.selectedNum = selectedNum;
	}




	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCoursrId() {
		return coursrId;
	}
	public void setCoursrId(String coursrId) {
		this.coursrId = coursrId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getTotolNum() {
		return totolNum;
	}
	public void setTotolNum(Integer totolNum) {
		this.totolNum = totolNum;
	}

	public Integer getSelectedNum() {
		return selectedNum;
	}

	public void setSelectedNum(Integer selectedNum) {
		this.selectedNum = selectedNum;
	}

	public String getLessonPlace() {
		return lessonPlace;
	}

	public void setLessonPlace(String lessonPlace) {
		this.lessonPlace = lessonPlace;
	}

	public List<String> getClassTime() {
		return classTime;
	}
	public void setClassTime(List<String> classTime) {
		this.classTime = classTime;
	}
	
}

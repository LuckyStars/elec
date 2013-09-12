package com.nbcedu.function.elec.vo;

import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlData;
import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlModel;

/**
 * 任课老师查看学生报名情况页面
 * @author xuechong
 *
 */
@ExlModel
public class ECStudentInfoVo {
	/**
	 * 报名ID
	 */
	private String signId;
	/**
	 * 学生ID
	 */
	private String stuId;
	/**
	 * 报名课程ID
	 */
	private String ecCourseId;
	@ExlData(sortId=1,title="学号")
	private String stuNo;
	@ExlData(sortId=2,title="学生姓名")
	private String stuName;
	@ExlData(sortId=3,title="性别")
	private String sex;
	@ExlData(sortId=4,title="班级")
	private String className;
	@ExlData(sortId=5,title="班主任")
	private String classMasterName;
	@ExlData(sortId=6,title="家长电话")
	private String parentMobile;
	
	
	//////////////////////////
	/////GETTERS&SETTERS/////
	//////////////////////////
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getEcCourseId() {
		return ecCourseId;
	}
	public void setEcCourseId(String ecCourseId) {
		this.ecCourseId = ecCourseId;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassMasterName() {
		return classMasterName;
	}
	public void setClassMasterName(String classMasterName) {
		this.classMasterName = classMasterName;
	}
	public String getParentMobile() {
		return parentMobile;
	}
	public void setParentMobile(String parentMobile) {
		this.parentMobile = parentMobile;
	}
	
}

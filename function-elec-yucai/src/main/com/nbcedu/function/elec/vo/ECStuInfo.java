package com.nbcedu.function.elec.vo;

import java.util.*;

import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlData;
import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlModel;
/**
 * 
 * @author bzh
 * 班主任查看页面实体
 */

@ExlModel
public class ECStuInfo {
	
	/**学生uid**/
	private String stuId;
	/**学号**/
	@ExlData(sortId=1,title="学号")
	private String stuNo;
	/**学生姓名**/
	@ExlData(sortId=2,title="学生姓名")
	private String stuName;
	/**性别**/
	@ExlData(sortId=3,title="性别")
	private String sex;
	/**家长电话**/
	@ExlData(sortId=6,title="家长电话")
	private String parentsPhone;
	/**课程名称**/
	@ExlData(sortId=7,title="报名课程")
	private String mainName;
	
	private List<ECCourseVO> mainList = new ArrayList<ECCourseVO>();
	
	public ECStuInfo() {
		
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
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

	public String getParentsPhone() {
		return parentsPhone;
	}

	public void setParentsPhone(String parentsPhone) {
		this.parentsPhone = parentsPhone;
	}

	public String getMainName() {
		return mainName;
	}

	public void setMainName(String mainName) {
		this.mainName = mainName;
	}

	public List<ECCourseVO> getMainList() {
		return mainList;
	}

	public void setMainList(List<ECCourseVO> mainList) {
		this.mainList = mainList;
	}

}

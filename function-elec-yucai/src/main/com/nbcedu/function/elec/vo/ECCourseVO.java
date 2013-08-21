package com.nbcedu.function.elec.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlData;
import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlModel;
import com.nbcedu.function.elec.model.ECClasshour;
import com.nbcedu.function.elec.model.ECPlace;
import com.nbcedu.function.elec.model.ECTerm;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.function.elec.util.CourseAttr;
import com.nbcedu.function.elec.util.DictionaryUtil;

/**
 * 课程
 * 
 * @author qinyuan
 * @since 2013-3-7
 */
@ExlModel
public class ECCourseVO {
	private String id; // id
	private String termId; // 所属哪期兴趣班ID
	private String typeId; // 课程类别
	private String gradeIds; // 年级范围，多个以，分隔
	@ExlData(sortId=5,title="年级范围")
	private String gradeNames; // 年级名称集合，多个以，分隔
	@ExlData(sortId=2,title="课程名称")
	private String name; // 课程名称
	@ExlData(sortId=7,title="限定人数")
	private Integer maxStudentNum; // 限定总人数
	private String teacherIds; // 任课老师ID，多个以，分隔
	@ExlData(sortId=3,title="任课老师")
	private String teacherNames; // 任课老师姓名，多个以，分隔
	private Date signStartDate; // 报名开始
	private Date signEndDate; // 报名结束
	private Date classStartDate; // 开课日期
	private Date classEndDate; // 结课日期
	private Integer classhourNum; // 每周课时
	private List<ECClasshour> classhourList = new ArrayList<ECClasshour>(); // 每周课时-详情List
	private ECPlace startPlace; // 上课地点
	private ECPlace endPlace; // 放学地点
	private String courseComment; // 课程提示
	private Integer clubState; // 是否俱乐部:1-是 0-否
	private String courseContent; // 课程内容
	private String courseRequire; // 学生要求
	private String courseNote; // 备 注
	private Date operatorDate; // 录入时间，根据它排序
	private Integer courseAttr; //课程属性(育才)
	
	@ExlData(sortId=8,title="已选人数")
	private Integer currentStudentNum;// 已选人数
	
	// vo
	private String[] condGradeIds; // 年级范围
	private String[] teacherIdsArr; // 任课老师ID范围
	@ExlData(sortId=6,title="上课地点")
	private String startPlaceName; // 上课地点名称
	private String typeName; // 课程类别名称
	private boolean isEdit; // 是否出现：编辑、删除
	
	private String startPlaceId; // 上课地点ID
	private String endPlaceId; // 放学地点ID
	
	private String signStartDateStr; // 报名开始Str
	private String signEndDateStr; // 报名结束Str
	private String classStartDateStr; // 开课日期Str
	private String classEndDateStr; // 结课日期Str
	private String[] weekIndexs;
	private String[] weekStartTimes;
	private String[] weekEndTimes;
	
	@ExlData(sortId=4,title="上课时间")
	private String classhoursListStr; //上课时间字符串（为了迎合Excel导出）
	
	private ECTerm ecTerm;
	
	private Integer weekIndex; //周
	
	/**
	 * 课时要求(史家)
	 */
	private Integer classhourRequire;
	
	public ECCourseVO(String id, String termId, String typeId, String gradeIds, String gradeNames, String name, Integer maxStudentNum,
			String teacherNames, Date classStartDate, Date classEndDate, String startPlaceId, String startPlaceName, Integer clubState, 
			Date signStartDate, String teacherIds, Date signEndDate, Integer classhourRequire) {
		this.id = id;
		this.termId = termId;
		this.typeId = typeId;
		this.gradeIds = gradeIds;
		this.gradeNames = gradeNames;
		this.name = name;
		this.maxStudentNum = maxStudentNum;
		this.teacherNames = teacherNames;
		this.classStartDate = classStartDate;
		this.classEndDate = classEndDate;
		this.startPlaceId = startPlaceId;
		this.startPlaceName = startPlaceName;
		this.clubState = clubState;
		this.signStartDate = signStartDate;
		this.teacherIds = teacherIds;
		this.signEndDate = signEndDate;
		this.classhourRequire = classhourRequire;
	}
	
	public ECCourseVO() {
	}
	
	public String getClubStateStr(){
		if(clubState != null){
			return clubState.intValue()==Constants.CLUB_STATE_YES ? "是" : "否" ;
		}
		return "";
	}
	public String getCourseAttrStr(){
		if(courseAttr != null){
			return CourseAttr.getById(courseAttr.intValue()).getName();
		}
		return "";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(String gradeIds) {
		this.gradeIds = gradeIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMaxStudentNum() {
		return maxStudentNum;
	}

	public void setMaxStudentNum(Integer maxStudentNum) {
		this.maxStudentNum = maxStudentNum;
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

	public Integer getCurrentStudentNum() {
		return currentStudentNum;
	}

	public void setCurrentStudentNum(Integer currentStudentNum) {
		this.currentStudentNum = currentStudentNum;
	}

	public String[] getCondGradeIds() {
		return condGradeIds;
	}

	public void setCondGradeIds(String[] condGradeIds) {
		this.condGradeIds = condGradeIds;
	}
	
	@ExlData(sortId=1,title="课程类别")
	public String getTypeName() {
		//课程类别
		if(!StringUtils.isBlank(typeId)){
			return DictionaryUtil.getTypeById(typeId).getName();
		}
		return this.typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStartPlaceId() {
		return startPlaceId;
	}

	public void setStartPlaceId(String startPlaceId) {
		this.startPlaceId = startPlaceId;
	}

	public String getGradeNames() {
		return gradeNames;
	}

	public void setGradeNames(String gradeNames) {
		this.gradeNames = gradeNames;
	}

	public String getStartPlaceName() {
		return startPlaceName;
	}

	public void setStartPlaceName(String startPlaceName) {
		this.startPlaceName = startPlaceName;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
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

	public String[] getTeacherIdsArr() {
		return teacherIdsArr;
	}

	public void setTeacherIdsArr(String[] teacherIdsArr) {
		this.teacherIdsArr = teacherIdsArr;
	}

	public String getEndPlaceId() {
		return endPlaceId;
	}

	public void setEndPlaceId(String endPlaceId) {
		this.endPlaceId = endPlaceId;
	}

	public String getSignStartDateStr() {
		return signStartDateStr;
	}

	public void setSignStartDateStr(String signStartDateStr) {
		this.signStartDateStr = signStartDateStr;
	}

	public String getSignEndDateStr() {
		return signEndDateStr;
	}

	public void setSignEndDateStr(String signEndDateStr) {
		this.signEndDateStr = signEndDateStr;
	}

	public String getClassStartDateStr() {
		return classStartDateStr;
	}

	public void setClassStartDateStr(String classStartDateStr) {
		this.classStartDateStr = classStartDateStr;
	}

	public String getClassEndDateStr() {
		return classEndDateStr;
	}

	public void setClassEndDateStr(String classEndDateStr) {
		this.classEndDateStr = classEndDateStr;
	}

	public String[] getWeekIndexs() {
		return weekIndexs;
	}

	public void setWeekIndexs(String[] weekIndexs) {
		this.weekIndexs = weekIndexs;
	}

	public String[] getWeekStartTimes() {
		return weekStartTimes;
	}

	public void setWeekStartTimes(String[] weekStartTimes) {
		this.weekStartTimes = weekStartTimes;
	}

	public String[] getWeekEndTimes() {
		return weekEndTimes;
	}

	public void setWeekEndTimes(String[] weekEndTimes) {
		this.weekEndTimes = weekEndTimes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ECCourseVO other = (ECCourseVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ECTerm getEcTerm() {
		return ecTerm;
	}

	public void setEcTerm(ECTerm ecTerm) {
		this.ecTerm = ecTerm;
	}

	public String getClasshoursListStr() {
		return classhoursListStr;
	}

	public void setClasshoursListStr(String classhoursListStr) {
		this.classhoursListStr = classhoursListStr;
	}

	public Integer getCourseAttr() {
		return courseAttr;
	}

	public void setCourseAttr(Integer courseAttr) {
		this.courseAttr = courseAttr;
	}

	public Integer getWeekIndex() {
		return weekIndex;
	}

	public void setWeekIndex(Integer weekIndex) {
		this.weekIndex = weekIndex;
	}

	public Integer getClasshourRequire() {
		return classhourRequire;
	}

	public void setClasshourRequire(Integer classhourRequire) {
		this.classhourRequire = classhourRequire;
	}
	
}

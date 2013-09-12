package com.nbcedu.function.elec.biz;

import java.util.List;
import java.util.Map;

import com.nbcedu.function.elec.devcore.biz.ElecBaseBiz;
import com.nbcedu.function.elec.model.ECClasshour;
import com.nbcedu.function.elec.model.ECSign;

/**
 * 课时
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
public interface ECClasshourBiz extends ElecBaseBiz<ECClasshour> {
	/**
	 * （只适用于史家）
	 * 1、判断课时人数是否达到上限；<br/>
	 * 2、校验老师后台录入学生报名记录冲突; <br/>
	 * @return 返回数据库中导致冲突的报名记录，没有返回null
	 */
	public ECSign validateConflict(String stuId, String courseId, List<String> classhourIdList);
	
	/**
	 * 根据课程查询课时列表
	 * @param courseId
	 * @return
	 */
	List<ECClasshour> findBy(String courseId);
	
	/**
	 * 校验课时： <br/>
	 * 1、一门课：同时选周一时，时间不能冲突<br/>
	 * 2、任课老师：同一时间，只能任教一门课 <br/>
	 * 3、上课地点：同一时间，不能重复
	 */
	public Map<String, Object> checkClassHour(String teacherIds, String teacherNames, String startPlaceId, String termId,
			String classStartDateStr, String classEndDateStr, String[] weekIndexs, String[] weekStartTimes, String[] weekEndTimes, String courseID)
			throws Exception;
	
}

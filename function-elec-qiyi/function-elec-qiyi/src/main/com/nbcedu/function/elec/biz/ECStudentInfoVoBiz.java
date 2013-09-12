package com.nbcedu.function.elec.biz;

import java.util.List;

import com.nbcedu.function.elec.model.ECSign;
import com.nbcedu.function.elec.vo.ECStudentInfoVo;

public interface ECStudentInfoVoBiz {
	/**
	 * 根据报名信息封装学生对象
	 * @param signList
	 * @return
	 * @author xuechong
	 */
	public List<ECStudentInfoVo> encapeList(List<ECSign> signList);
	/**
	 * 根据报名信息封装学生对象
	 * @param signList
	 * @return
	 * @author xuechong
	 */
	public List<ECStudentInfoVo> encapeList(List<ECSign> signList,String gradeId);
	/**
	 * 获取所有学生数据
	 * @return
	 * @author xuechong
	 */
	public String getStuJson();
	
	/**
	 * 按节点ID获取数据
	 * @return
	 * @author xuechong
	 * @param courseId 
	 */
	public String getTreeNodes(String id, String courseId);
}

package com.nbcedu.function.elec.biz;

import java.util.List;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBiz;
import com.nbcedu.function.elec.model.ECSign;

public interface ECSignBiz extends ElecBaseBiz<ECSign>{
	/**
	 * 按课程ID查询报名信息(全部)
	 * @param courseId
	 * @return
	 * @author xuechong
	 */
	public List<ECSign> findByCourseId(String courseId);
	/**
	 * 按课程ID查询报名信息(指定年级)
	 * @param courseId
	 * @param gradeId
	 * @return
	 * @author xuechong
	 */
	public List<ECSign> findByCourseId(String courseId,String gradeId);

	/**
	 * 按课程ID查询报名信息(分页)
	 * @param page
	 * @param id
	 * @return
	 * @author xuechong
	 */
	public Page findPageByCourseId(Page page, String id);
	
	/**
	 * 定时清空上课时间为空的报名
	 * @author libin
	 */
	public void removeECSignTimer(Long  signCreateTime);
	
	/**
	 * 按学期ID查找
	 * @param termId
	 * @return
	 * @author xuechong
	 */
	public List<ECSign> findByTermId(String termId);
	
	/**
	 * 批量增加
	 * 
	 * @author xuechong
	 */
	public void addAll(List<ECSign> signList);
	
	/**
	 * 根据学生id查询此学生本学期报的课程
	 * @param student uid
	 * @return
	 */
	public List<ECSign> findByStudentId(String studentId);
	
	/**
	 * 根据学生id,课程id查询此学生报的课程
	 * @param studentId
	 * @param courseId
	 * @return
	 */
	public ECSign findByStudentIdAndCourseId(String studentId,String courseId);
	
	/**
	 * 根据学生id和学期id获得此学生报课程名数
	 * @param studentId
	 * @param termId
	 * @return
	 */
	public Long findByStudentIdAndTermId(String studentId,String termId);
	
	/**
	 * 根据课时id获得此课时的报名数
	 * @param classDateId
	 * @return
	 */
	public Long findByClassDateCount(String classDateId);
	
	/**
	 * 学生报名
	 * @param ecSign
	 * @return
	 */
	public String addSignPro(ECSign ecSign);
	
	/**
	 *根据id删除所选的课程 
	 * @param id
	 * @return
	 */
	public boolean removeSign(String id);
	/**
	 * 根据id退课时
	 * @param id
	 * @return
	 */
	public boolean updateSign(String id);
	
	/**
	 * 根据课程id删除录入学生数据
	 * @param id
	 * @author
	 */
	void removeByCourseId(String id);
}

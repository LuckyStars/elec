package com.nbcedu.function.elec.biz;

import java.util.Collection;
import java.util.List;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBiz;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.vo.ECCourseVO;

/**
 * 课程管理
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
public interface ECCourseBiz extends ElecBaseBiz<ECCourse> {
	
	/**
	 * 条件分页，page为null时，不分页
	 * @param page
	 * @param ecCourseVO
	 */
	List<ECCourseVO> findPage(ECCourseVO ecCourseVO, Page page);
	/**
	 * 条件分页，page为null时，不分页
	 * @param page
	 * @param ecCourseVO
	 */
	List<ECCourseVO> findPage(ECCourseVO ecCourseVO, Page page,String gradeId);
	
	/**
	 * 校验课程名重复. true-重复 false-不重复
	 * @return
	 */
	boolean checkName(String name, String termId, String... courseID);
	
	/**
	 * 根据课程id，查看
	 * @param id
	 * @return
	 */
	ECCourseVO getCourseById(String id);
	
	/**
	 * 按学期获取总数
	 * @param termId
	 * @return
	 * @author xuechong
	 */
	int findCountByTerm(String termId);
	/**
	 * 删除课程
	 * @param ids
	 * @author
	 */
	void removeALL(Collection<String> ids);
}

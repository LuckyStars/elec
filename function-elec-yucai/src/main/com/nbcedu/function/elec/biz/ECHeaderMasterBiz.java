package com.nbcedu.function.elec.biz;

import java.util.List;

import com.nbcedu.function.elec.vo.ECCourseClass;
import com.nbcedu.function.elec.vo.ECCourseVO;
import com.nbcedu.function.elec.vo.ECStuInfo;


public interface ECHeaderMasterBiz {
	
	/**
	 * 班主任查看本班的学生选课信息
	 * @param user
	 * @return
	 */
	public List<ECStuInfo> findStuByHeaderMaster(String uid);
	
	
	/**
	 * 班主任根据课程名搜索本班的学生选课信息
	 * @param uid
	 * @param name
	 * @return
	 */
	public List<ECStuInfo> findToSearchStu(String uid , ECCourseVO ecCourseVO);
	
	/**
	 * 获取到处exel文件的标题名称
	 * @param uid
	 * @return
	 */
	public String getHeadText(String uid , String flag);
	
	/**
	 * 班主任查看本班报名课程
	 * @param uid
	 * @return
	 */
	public List<ECCourseClass> findAllOurClass(String termId ,String uid  , String mainName);
	
	
	/**
	 * 班主任查看单门课程有哪些学生报名
	 * @param uid
	 * @param courseId
	 * @return
	 */
	public List<ECStuInfo> findOneMainStu(String uid , String courseId);
	
	
	/**
	 * 班主任 导出本班报名课程
	 * @param uid
	 * @return
	 */
	public String exportByHeaderMaster(String termId,String uid);
	

}

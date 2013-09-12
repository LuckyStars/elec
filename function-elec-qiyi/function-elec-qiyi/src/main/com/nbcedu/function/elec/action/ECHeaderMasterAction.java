package com.nbcedu.function.elec.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.biz.ECHeaderMasterBiz;
import com.nbcedu.function.elec.biz.ECTermBiz;
import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.devcore.util.exl.ExlAnnotationUtil;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.model.ECTerm;
import com.nbcedu.function.elec.util.CommonUtil;
import com.nbcedu.function.elec.vo.ECCourseClass;
import com.nbcedu.function.elec.vo.ECCourseVO;
import com.nbcedu.function.elec.vo.ECStuInfo;

@Component("ecHeaderMaster")
@Scope("prototype")
public class ECHeaderMasterAction extends ElecBaseAction {
	
	private static final long serialVersionUID = 1L;
	
	@Resource(name="elecTermBiz")
	private ECTermBiz ectermBiz;
	@Resource(name="ecHeaderMasterBiz")
	private ECHeaderMasterBiz ecHeaderMasterBiz;
	@Resource
	private ECCourseBiz ecCourseBiz;
	
	private ECTerm ecTerm;
	private List<ECStuInfo> stuList = new ArrayList<ECStuInfo>();
	private List<ECCourseClass>  classList = new ArrayList<ECCourseClass>();
	private List<ECCourseVO> courselist = new ArrayList<ECCourseVO>();
	
	private ECCourseVO ecCourseVO;
	private String courseName;
	private ECCourse ecCourse;
	/**
	 * 文件下载开始
	 */
	private String file; // 需要下载的文件名,通过链接传过来
	
	
	/**
	 * 班主任查询本班学生选课信息
	 * @return
	 */
	public String findStuByHeaderMaster(){
		//获取当前兴学期趣班
		this.ecTerm = this.ectermBiz.findCurrentTerm();
		//获取班级学生的报名信息
		stuList = this.ecHeaderMasterBiz.findStuByHeaderMaster(CommonUtil.getCurrentUser().getUid());

		return "stuList";
	}
	/**
	 * 班主任根据课程名搜索本班的学生选课信息
	 * @return
	 */
	public String searchStuByHeaderMaster(){
		//获取当前学期兴趣班
		this.ecTerm = this.ectermBiz.findCurrentTerm();
		String flag = request.getParameter("flag");
		//获取当前用户的uid
		ecCourseVO.setTermId(ecTerm.getId());
		stuList = this.ecHeaderMasterBiz.findToSearchStu(CommonUtil.getCurrentUser().getUid() , ecCourseVO);
		request.setAttribute("search",flag );
		return "stuList";
	}

	/**
	 * 班主任导出本班学生报名信息
	 * @return
	 */
	public void exportByStu(){
		//获取当前用户的uid
		String head = this.ecHeaderMasterBiz.getHeadText(CommonUtil.getCurrentUser().getUid() , "stu");
		stuList = this.ecHeaderMasterBiz.findStuByHeaderMaster(CommonUtil.getCurrentUser().getUid());
		ExlAnnotationUtil.export(head, stuList);
	}
	
	/**
	 * 班主任查看本班报名课程
	 * @return
	 */
	public String findAllClass(){
		//获取当前学期兴趣班
		this.ecTerm = this.ectermBiz.findCurrentTerm();
		//获取当前用户的uid
		this.classList = this.ecHeaderMasterBiz.findAllOurClass( ecTerm.getId() ,CommonUtil.getCurrentUser().getUid() , null); 
		
		return "ourList";
	}
	
	/**
	 * 班主任根据课程名称查看本班报名课程
	 * @return
	 */
	public String searchAllOurClass(){
		//获取当前学期兴趣班
		this.ecTerm = this.ectermBiz.findCurrentTerm();
		//获取当前用户的uid
		this.classList = this.ecHeaderMasterBiz.findAllOurClass( ecTerm.getId(), CommonUtil.getCurrentUser().getUid() , courseName); 
		return "ourList";
	}
	
	/**
	 * 班主任查看单门课程有哪些学生报名
	 * @return
	 */
	public String findOneMainStu(){
		//获取当前用户的uid
		this.ecCourse = this.ecCourseBiz.findById(this.id);
		this.stuList = this.ecHeaderMasterBiz.findOneMainStu(CommonUtil.getCurrentUser().getUid() , this.id);
		request.setAttribute("apply", "OK");
		return "stuList";
	}
	
	/**
	 * 	 班主任 导出本班报名课程
	 * @return
	 */
	public  String exportOurClass(){ 
		//获取当前用户的uid
		String fName = this.ecHeaderMasterBiz.exportByHeaderMaster( ecTerm.getId() , CommonUtil.getCurrentUser().getUid());
		this.file = fName + ".xls"; 
		return SUCCESS;
	}
	
	/**
	 * 班主任查询所有课程信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findAllMain(){
		//获取当前学期兴趣班
		this.ecTerm = this.ectermBiz.findCurrentTerm();
		if(ecCourseVO==null){
			ecCourseVO = new ECCourseVO();
		}
		ecCourseVO.setTermId(ecTerm.getId());
		ecCourseBiz.findPage(ecCourseVO, page);
		courselist = (List<ECCourseVO>) page.getDatas();
		return LIST;
	}
	
	
	/**
	 * 浏览器转码问题
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getFile() throws UnsupportedEncodingException {
		String agent = (String) this.request.getHeader("USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {// FF
			file = new String(this.file.getBytes(), "ISO8859-1");
		} else {
			file = java.net.URLEncoder.encode(this.file, "UTF-8");
		}
		return file;

	}
	/**
	 * 文件下载
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStream() throws Exception {
		InputStream in = null;
		in = ServletActionContext.getServletContext().getResourceAsStream("/" + "/excel/" + this.file);
		return in;
	}
	
	public void setFile(String file) throws UnsupportedEncodingException {
		this.file = file;
	}
	
	public ECTerm getEcTerm() {
		return ecTerm;
	}
	public void setEcTerm(ECTerm ecTerm) {
		this.ecTerm = ecTerm;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<ECStuInfo> getStuList() {
		return stuList;
	}
	public void setStuList(List<ECStuInfo> stuList) {
		this.stuList = stuList;
	}
	public ECCourseVO getEcCourseVO() {
		return ecCourseVO;
	}
	public void setEcCourseVO(ECCourseVO ecCourseVO) {
		this.ecCourseVO = ecCourseVO;
	}
	public List<ECCourseClass> getClassList() {
		return classList;
	}
	public void setClassList(List<ECCourseClass> classList) {
		this.classList = classList;
	}
	public ECCourse getEcCourse() {
		return ecCourse;
	}
	public void setEcCourse(ECCourse ecCourse) {
		this.ecCourse = ecCourse;
	}
	public List<ECCourseVO> getCourselist() {
		return courselist;
	}
	public void setCourselist(List<ECCourseVO> courselist) {
		this.courselist = courselist;
	}
	
}

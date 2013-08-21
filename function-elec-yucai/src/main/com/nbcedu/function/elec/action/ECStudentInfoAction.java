package com.nbcedu.function.elec.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.JsonObject;
import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.biz.ECClasshourBiz;
import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.biz.ECSignBiz;
import com.nbcedu.function.elec.biz.ECStudentInfoVoBiz;
import com.nbcedu.function.elec.biz.ECTermBiz;
import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.devcore.util.exl.ExlAnnotationUtil;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.model.ECSign;
import com.nbcedu.function.elec.model.ECTerm;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.function.elec.util.Struts2Utils;
import com.nbcedu.function.elec.vo.ECStudentInfoVo;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcClass;
import com.nbcedu.integration.uc.client.vo.NbcUcStudent;

/**
 * 学生信息管理
 * @author xuechong
 */
@Controller("stuInfoAction")
@Scope("prototype")
public class ECStudentInfoAction extends ElecBaseAction{
	private static final Logger logger = Logger.getLogger(ECStudentInfoAction.class);
	private static final long serialVersionUID = 1L;
	
	@Resource(name="elecStuInfoVoBiz")
	private ECStudentInfoVoBiz stuInfoVoBiz;
	@Resource(name="elecSignBiz")
	private ECSignBiz signBiz;
	@Resource(type=ECCourseBiz.class)
	private ECCourseBiz courseBiz;
	@Resource(name="baseClient")
	private BaseClient baseClient;
	@Resource(name="elecTermBiz")
	private ECTermBiz termBiz;
	@Resource
	private ECClasshourBiz classhourBiz;
	
	private ECCourse course;
	private List<ECStudentInfoVo> stuList ;
	private ECStudentInfoVo stuInfo;
	private Page page = new Page();
	private String treeJson;
	private String studentIds;
	private String[] classHourId;
	
	/**
	 * 删除报名
	 * @return
	 * @author xuechong
	 */
	public String remove(){
		ECSign sign = this.signBiz.findById(this.stuInfo.getSignId());
		this.course = new ECCourse();
		this.course.setId(sign.getCourseId());
		this.signBiz.removeById(this.stuInfo.getSignId());
		return this.list();
	}
	/**
	 * 分页
	 * @return
	 * @author xuechong
	 */
	@SuppressWarnings("unchecked")
	public String list(){
		this.page = this.signBiz.findPageByCourseId(this.page,this.course.getId());
		this.stuList = this.stuInfoVoBiz.encapeList((List<ECSign>) this.page.getDatas());
		this.page.setDatas(this.stuList);
		this.course = this.courseBiz.findById(this.course.getId());
		String flag = request.getParameter("flag");
		if(flag==null){
			flag = request.getSession().getAttribute("flag_list").toString();
		}
		//String referer = request.getHeader("Referer");
		//request.getSession().setAttribute("ecStudentInfoReferer", referer);
		request.getSession().setAttribute("flag_list", flag);
		return LIST;
	}
	/**
	 * 导出EXL
	 * @author xuechong
	 */
	public void exportExl(){
		this.course = this.courseBiz.findById(this.course.getId());
		if(this.course!=null){
			String head = this.course.getName() + "学生列表";
			stuList = stuInfoVoBiz.encapeList(signBiz.findByCourseId(this.course.getId()));
			List<String> conditions = Arrays.asList(new String[] {
					"选课人数:" + this.listSize(this.stuList),
					"任课教师:" + course.getTeacherNames() });
			ExlAnnotationUtil.export(head, conditions, this.stuList);
		}else{
			ExlAnnotationUtil.export("学生列表",Collections.emptyList());
		}
	}
	
	public void tree(){
		this.id = StringUtils.isBlank(this.id)?"root":this.id.trim();
		Struts2Utils.renderJson(this.stuInfoVoBiz.getTreeNodes(this.id,this.course.getId()));
	}
	
	
	/**
	 * 验证学生是否已经报过名
	 * @author xuechong
	 */
	public void validateStudent(){
		List<ECSign> signList = this.signBiz.findByCourseId(this.course.getId());
		JsonObject result;
		if(signList==null||signList.isEmpty()){
			result = new JsonObject();
			result.addProperty("state", "suc");
		}else{
			Set<String> commitSet = getStuIdSet();
			result = 
				this.getSignValidator().validate(commitSet,this.course.getId(),classHourId);
		}
		Struts2Utils.renderJson(result.toString());
	}
	
	/**
	 * 增加
	 * @return
	 * @author xuechong
	 */
	public String addSign(){
		
		Set<String> commitSet = getStuIdSet();
		List<ECSign> signList = new ArrayList<ECSign>();
		
		String classHourIds = Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_SHIJIA) ?
				StringUtils.join(this.classHourId, ","):"";
				
		for (String stuId : commitSet) {
			if(StringUtils.isNotBlank(stuId)){
				ECSign sign = new ECSign();
				sign.setStuId(stuId);
				sign.setCourseId(this.course.getId());
				sign.setClasshourIds(classHourIds);
				sign.setCreateDate(new Date());
				signList.add(sign);
			}
		}
		if(signList!=null&&!signList.isEmpty()){
			this.signBiz.addAll(signList);
		}
		return "reList";
	}
	//////////////////////////////
	/////////PRIVATE//////////////
	//////////////////////////////
	@SuppressWarnings("unchecked")
	private Integer listSize(List list){
		return list==null ? 0 :list.size();
	}
	private Set<String> getStuIdSet(){
		return new HashSet<String>(Arrays.asList(this.studentIds.split(",")));
	}
	
	/**
	 * 获取匹配当前报名方式的验证器
	 * @return
	 * @author xuechong
	 */
	private SignCallBack getSignValidator(){
		
		if(Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_SHIJIA)){//史家报名方式:验证并提示课时冲突
			return new SignCallBack() {
				
				@Override
				public JsonObject validate(Set<String> commitId, String courseId, String[] classHourId) {
					JsonObject result = new JsonObject();
					
					ECTerm curTerm = termBiz.findCurrentTerm();
					List<ECSign> signList = signBiz.findByTermId(curTerm.getId());
					
					Set<String> maxCourse = 
						this.findMaxCourseConflict(commitId, signList, curTerm.getMaxCourse());
					
					Set<String> alreadySigned = 
						this.findAlreadySignedConflict(commitId, signList, courseId);
					
					Set<String> classHour = 
						this.findClassHourConflict(commitId, courseId, Arrays.asList(classHourId));
					//是否还有足够的位置
					boolean stuCount = 
						this.stuCountConflict(course.getId(),signList,commitId.size());
						
					result.addProperty("state",
							maxCourse.isEmpty()&&alreadySigned.isEmpty()&&classHour.isEmpty()&&stuCount?"suc":"fail");
					result.addProperty("max", StringUtils.join(maxCourse, ","));
					result.addProperty("already", StringUtils.join(alreadySigned, ","));
					result.addProperty("hour", StringUtils.join(classHour,","));
					result.addProperty("stuCount", String.valueOf(stuCount));
					return result;
				}

				
			};
		}else if(Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_YUCAI)){//只验证报名冲突
			return new SignCallBack() {//育才
				@Override
				public JsonObject validate(Set<String> commitId, String courseId, String[] classHourId) {
					JsonObject result = new JsonObject();
					
					ECTerm curTerm = termBiz.findCurrentTerm();
					List<ECSign> signList = signBiz.findByTermId(curTerm.getId());
					
					Set<String> maxCourse = 
						this.findMaxCourseConflict(commitId, signList, curTerm.getMaxCourse());
					
					Set<String> alreadySigned = 
						this.findAlreadySignedConflict(commitId, signList, courseId);
					
					boolean stuCount = 
						this.stuCountConflict(course.getId(),signList,commitId.size());
					
					result.addProperty("state",
							maxCourse.isEmpty()&&alreadySigned.isEmpty()&&stuCount?"suc":"fail");
					
					result.addProperty("max", StringUtils.join(maxCourse, ","));
					result.addProperty("already", StringUtils.join(alreadySigned, ","));
					result.addProperty("stuCount", String.valueOf(stuCount));
					
					return result;
				}
			};
		}else{
			logger.error("报名方式配置错误:" + Constants.SIGN_TYPE);
			throw new IllegalArgumentException("无法处理报名方式:" + Constants.SIGN_TYPE);
		}
		
	}
	
	/**
	 * 验证器父类
	 * @author xuechong
	 *
	 */
	private abstract class SignCallBack{
		/**
		 * 验证学生报名信息并返回已报名情况
		 * @author xuechong
		 * @param classHourId 
		 * @param courseId 
		 */
		abstract JsonObject validate(Set<String> commitId, String courseId, String[] classHourId);
		
		/**
		 * 验证最大报名数冲突
		 * @param commitId
		 * @param signList
		 * @return
		 * @author xuechong
		 */
		Set<String> findMaxCourseConflict(Set<String> commitId,List<ECSign> signList,Integer count){
			Set<String> result = new HashSet<String>();
			if(count<=0){//为0时为不限
				return result;
			}
			Map<String,Integer> countMap = new HashMap<String, Integer>();
			for (ECSign ecSign : signList) {
				if(commitId.contains(ecSign.getStuId())){
					//the count plus 1 each time
					countMap.put(ecSign.getStuId(), 
							countMap.get(ecSign.getStuId())!=null?
									countMap.get(ecSign.getStuId())+1:1);
					if(countMap.get(ecSign.getStuId())>=count){
						result.add(buildMessageContent(ecSign.getStuId()));
					}
				}
			}
			return result;
		}
		/**
		 * 验证是否已经报名过
		 * @param commitId
		 * @param signList
		 * @param courseId
		 * @return
		 * @author xuechong
		 */
		Set<String> findAlreadySignedConflict(Set<String> commitId,List<ECSign> signList,String courseId){
			Set<String> result = new HashSet<String>();
			for (ECSign ecSign : signList) {
				if(commitId.contains(ecSign.getStuId())&&ecSign.getCourseId().equals(courseId)){
					result.add(buildMessageContent(ecSign.getStuId()));
				}
			}
			return result;
		}
		
		/**
		 * 验证课时冲突
		 * @param commitId
		 * @param courseId
		 * @param classHourId
		 * @return
		 * @author xuechong
		 */
		Set<String> findClassHourConflict(Set<String> commitId,String courseId,List<String> classHourId){
			Set<String> result = new HashSet<String>();
			for (String stuId : commitId) {
				ECSign sign = classhourBiz.validateConflict(stuId, courseId, classHourId);
				if(sign!=null){
					ECCourse course = courseBiz.findById(courseId);
					result.add(buildMessageContent(stuId) + "(" + course.getName() + ")");
				}
			}
			return result;
		}
		
		/**
		 * 验证是否超过最大报名数
		 * @param courseId
		 * @param signList
		 * @param newSign
		 * @return
		 * @author xuechong
		 */

		boolean stuCountConflict(String courseId,
				List<ECSign> signList,Integer newSign) {
			ECCourse course = courseBiz.findById(courseId);
			if(course!=null&&course.getMaxStudentNum()!=null){
				if(course.getMaxStudentNum()==0) return Boolean.TRUE;
				Integer already =0;
				for (ECSign ecSign : signList) {
					if(ecSign.getCourseId().equals(courseId)){
						already++;
					}
				}
				return course.getMaxStudentNum()>=already+newSign;
			}else return Boolean.FALSE;
		}
		
		String findPid(String uid){
			return baseClient.queryUidPid(1, uid);
		}
		Map<String,String> paramMap(String key,String value){
			Map<String,String> result = new HashMap<String, String>(1);
			result.put(key, value);
			return result;
		}
		String buildMessageContent(String stuUid){
			NbcUcStudent stu = baseClient.queryStudent(1, findPid(stuUid));
			NbcUcClass clazz = baseClient.queryClass(2, paramMap("studentId", stu.getPid()));
			return stu.getName() + "(" +clazz.getClassName()+")";
		}
		
	}
	
	
	//////////////////////////
	/////GETTERS&SETTERS//////
	//////////////////////////
	public ECCourse getCourse() {
		return course;
	}
	public void setCourse(ECCourse course) {
		this.course = course;
	}
	public List<ECStudentInfoVo> getStuList() {
		return stuList;
	}
	public void setStuList(List<ECStudentInfoVo> stuList) {
		this.stuList = stuList;
	}
	public ECStudentInfoVo getStuInfo() {
		return stuInfo;
	}
	public void setStuInfo(ECStudentInfoVo stuInfo) {
		this.stuInfo = stuInfo;
	}
	public void setStuInfoVoBiz(ECStudentInfoVoBiz stuInfoVoBiz) {
		this.stuInfoVoBiz = stuInfoVoBiz;
	}
	public void setSignBiz(ECSignBiz signBiz) {
		this.signBiz = signBiz;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getTreeJson() {
		return treeJson;
	}
	public void setTreeJson(String treeJson) {
		this.treeJson = treeJson;
	}
	public void setCourseBiz(ECCourseBiz courseBiz) {
		this.courseBiz = courseBiz;
	}
	public String[] getClassHourId() {
		return classHourId;
	}
	public void setClassHourId(String[] classHourId) {
		this.classHourId = classHourId;
	}
	public String getStudentIds() {
		return studentIds;
	}
	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
	}
	
}

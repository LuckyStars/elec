package com.nbcedu.function.elec.biz.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nbcedu.function.elec.biz.ECStudentInfoVoBiz;
import com.nbcedu.function.elec.dao.ECCourseDao;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.model.ECSign;
import com.nbcedu.function.elec.vo.ECStudentInfoVo;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcClass;
import com.nbcedu.integration.uc.client.vo.NbcUcParent;
import com.nbcedu.integration.uc.client.vo.NbcUcStudent;
import com.nbcedu.integration.uc.client.vo.NbcUcTeacher;
import com.nbcedu.integration.uc.client.vo.NbcUcTreeNode;

@Repository("elecStuInfoVoBiz")
public class ECStudentInfoVoBizImpl implements ECStudentInfoVoBiz{
	@Resource(type=com.nbcedu.function.elec.dao.ECCourseDao.class)
	private ECCourseDao courseDao;
	@Resource(name="baseClient")
	private BaseClient baseClient;
	
	private static final String sexCodeMale = "9001001";
	private static final String sexCodeFemale = "9001002";
	
	@Override
	public List<ECStudentInfoVo> encapeList(List<ECSign> signList) {
		return this.encapeList(signList, null);
	}
	@Override
	public List<ECStudentInfoVo> encapeList(List<ECSign> signList,
			String gradeId) {
		List<ECStudentInfoVo> result = new ArrayList<ECStudentInfoVo>();
		
		if(signList==null||signList.isEmpty()){
			return result;
		}
		
		boolean searchGrade = StringUtils.isNotBlank(gradeId);
		for (ECSign sign : signList) {
			String pid = this.baseClient.queryUidPid(1, sign.getStuId());
			ECStudentInfoVo vo = new ECStudentInfoVo();
			NbcUcStudent ucStu = this.baseClient.queryStudent(1, pid);
			NbcUcClass clazz = this.findClassByStuId(pid);
			if(searchGrade){///排除年级
				if(!String.valueOf(clazz.getGradeNum()).equalsIgnoreCase(gradeId)){
					continue;
				}
			}
			vo.setClassMasterName(this.getMasterNameByClassId(clazz.getId()));
			vo.setClassName(clazz.getGradeNum() + "年级" + clazz.getClassName());
			vo.setEcCourseId(sign.getCourseId());
			vo.setParentMobile(this.getParentPhoneByStuId(pid));
			vo.setSex(sexName(ucStu.getSex()));
			vo.setSignId(sign.getId());
			vo.setStuName(ucStu.getName());
			vo.setStuNo(ucStu.getStudentno());
			
			result.add(vo);
		}
		return result;
	}
	/**
	 * 获取性别名称
	 * @param sexCode
	 * @return
	 * @author xuechong
	 */
	private String sexName(String sexCode){
		return sexCode.equals(sexCodeMale) ? "男":sexCode.equals(sexCodeFemale)?"女":"未知";
	}
	/**
	 * 根据学生id查询学生所在班级
	 * @param id
	 * @return
	 * @author xuechong
	 */
	private NbcUcClass findClassByStuId(String pid){
		return this.baseClient.queryClass(2,newParamMap("studentId", pid));
	}
	/**
	 * 根据班级ID查询班主任姓名(如果多个则以逗号","分隔)
	 * @param id
	 * @return
	 * @author xuechong
	 */
	private String getMasterNameByClassId(String id){
		List<NbcUcTeacher> list = 
			baseClient.queryTeacherList(2, 1, newParamMap("classId", id));
		if(list==null||list.isEmpty()){
			return "";
		}
		StringBuffer result = new StringBuffer(list.size()*3);
		for (NbcUcTeacher teacher : list) {
			result.append(teacher.getName());
			result.append(",");
		}
		return subBuffer(result);
	}
	
	/**
	 * 根据学生ID查询家长电话号码
	 * @param id
	 * @return
	 * @author xuechong
	 */
	private String getParentPhoneByStuId(String id){
		List<NbcUcParent> list = 
			this.baseClient.queryParentList(2, 2, newParamMap("studentId", id));
		if(list==null||list.isEmpty()){
			return "";
		}
		StringBuffer result = new StringBuffer(24);
		for (NbcUcParent parent : list) {
			if(!StringUtils.isBlank(parent.getTelephone())){
				result.append(parent.getTelephone());
				result.append(",");
			}
		}
		if(StringUtils.isNotBlank(result.toString())){
			return subBuffer(result);
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	private Map newParamMap(String key,String value){
		Map<String, String> result = new HashMap<String, String>();
		result.put(key, value);
		return result;
	}
	
	private static String subBuffer(StringBuffer buffer){
		return buffer.substring(0, buffer.length()-1);
	}
	@Override
	public String getStuJson() {
		return buildJson("root").toString();
	}
	
	private JsonArray buildJson(String id){
		JsonArray result = new JsonArray();
		List<NbcUcTreeNode> nodeList = this.baseClient.queryTreeGradeClassStudents(id);
		for (NbcUcTreeNode node : nodeList) {
			JsonObject json = new JsonObject();
			json.addProperty("id", node.getId());
			json.addProperty("text", node.getTitle());
			if(!node.isLeaf()||node.getId().indexOf("|")>0){
				json.addProperty("state","closed");
				json.add("children", buildJson(node.getId()));
			}else{
				json.addProperty("state","");
			}
			result.add(json);
		}
		return result;
	}
	@Override
	public String getTreeNodes(String id,String courseId) {
		ECCourse course = this.courseDao.findUnique("FROM ECCourse c where c.id=?", courseId);
		JsonArray result = new JsonArray();
		List<NbcUcTreeNode> nodeList = this.baseClient.queryTreeGradeClassStudents(id);
		for (NbcUcTreeNode node : nodeList) {
			if(!id.equals("root")||
					(id.equals("root")
					&&course.getGradeIds().contains(node.getId().substring(node.getId().indexOf("|")+1))))
				{//过滤年级,只让可选择本课程的班级出现
				JsonObject json = new JsonObject();
				json.addProperty("id", node.getId().indexOf("|")>0?node.getId():findUid(node.getId()));
				json.addProperty("text", node.getTitle());
				json.addProperty("state",!node.isLeaf()||node.getId().indexOf("|")>0?"closed":"");
				result.add(json);
			}
		}
		return result.toString();
	}
	
	String findUid(String pid){
		return baseClient.queryUidPid(2, pid);
	}
	String findPid(String uid){
		return baseClient.queryUidPid(1, uid);
	}
	
	public static void main(String[] args) {
		BaseClient bc = new BaseClient();
		System.out.println(bc.queryUidPid(2,"cd0a335c088011e28959e41f13cd1f28"));
		System.out.println(bc.queryUidPid(1,"cd0a335c088011e28959e41f13cd1f28"));
		
	}
}

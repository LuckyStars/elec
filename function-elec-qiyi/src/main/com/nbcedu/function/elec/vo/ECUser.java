package com.nbcedu.function.elec.vo;


import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.StringUtils;

import com.nbcedu.function.elec.devcore.util.StringUtil;
import com.nbcedu.function.elec.model.ECUserPrivilege;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcClass;
/**
 * 登录后保存当前用户信息的实体
 * @author xuechong
 *
 */
public final class ECUser {
	
	private final String uid;
	private final String name;
	private final String pid;
	private final boolean admin;//是否是管理员
	private final boolean manager;//是否是教务老师
	private final boolean courseTeacher;//是否是任课老师(即可录入操作)
	private final boolean parent;//是否是家长
	private final boolean classMaster;//是否是班主任
	private final boolean courseTeacherViewable;//是否是任课老师(即可查看操作)
	private final String classId;//班级id
	private final String gradeId;//年级id
	private BaseClient baseClient;
	
	public ECUser(ECUserPrivilege userPrivilege,boolean parent,boolean classMaster, boolean courseTeacherViewable) {
		super();
		boolean adminTemp = Boolean.FALSE;
		boolean managerTemp = Boolean.FALSE;
		boolean courseTemp = Boolean.FALSE;
		this.uid = userPrivilege.getUid();
		this.name = userPrivilege.getUserName();
		this.pid = userPrivilege.getPid();
		this.parent = parent;
		this.classMaster = classMaster;
		this.courseTeacherViewable = courseTeacherViewable;
		if(StringUtils.isNotBlank(userPrivilege.getPrivileges())){
			for (String priId : userPrivilege.getPrivileges().split(",")) {
				if(priId.equals(Constants.ROLE_ID_ADMIN)){
					adminTemp = Boolean.TRUE;
				}
				if(priId.equals(Constants.ROLE_ID_MANAGER)){
					managerTemp = Boolean.TRUE;
				}
				if(priId.equals(Constants.ROLE_ID_COURSE_TEACHER)){
					courseTemp = Boolean.TRUE;
				}
			}
		}
		
		this.admin = adminTemp;
		this.manager = managerTemp;
		this.courseTeacher = courseTemp;
		
		if(parent){
			this.baseClient=new BaseClient();
			Map<String, String> param = new HashMap<String, String>(1);
			param.put("studentId", pid);
			NbcUcClass clazz = this.baseClient.queryClass(2, param);
			this.classId = clazz.getId();
			if (!StringUtil.isEmpty(clazz.getGradeId())) {//如果班级id为空 使用GradeNum
				this.gradeId=clazz.getGradeId();
			}else {
				this.gradeId=clazz.getGradeNum()+"";
			}
		}else{
			this.classId="";
			this.gradeId="";
		}
	}
	
	public final String getUid() {
		return uid;
	}
	public final String getName() {
		return name;
	}
	public boolean isAdmin() {
		return admin;
	}
	public boolean isManager() {
		return manager;
	}
	public boolean isCourseTeacher() {
		return courseTeacher;
	}
	public String getPid() {
		return pid;
	}
	public boolean isParent() {
		return parent;
	}
	public boolean isClassMaster() {
		return classMaster;
	}
	public String getClassId() {
		return classId;
	}
	public String getGradeId() {
		return gradeId;
	}
	public boolean isCourseTeacherViewable() {
		return courseTeacherViewable;
	}
	
	
}

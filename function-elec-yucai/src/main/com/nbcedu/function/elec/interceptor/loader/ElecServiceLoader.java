package com.nbcedu.function.elec.interceptor.loader;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.nbcedu.core.framework.filter.ServiceInfoLoader;
import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.biz.ECUserPrivilegeBiz;
import com.nbcedu.function.elec.model.ECUserPrivilege;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.function.elec.vo.ECCourseVO;
import com.nbcedu.function.elec.vo.ECUser;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcDiction;
import com.nbcedu.integration.uc.client.vo.NbcUcPerson;
import com.nbcedu.integration.uc.client.vo.NbcUcStudent;

/**
 * Loader加载器
 * 
 * @author qinyuan
 * @since 2013-2-26
 */
@Component("elecLoader")
public class ElecServiceLoader implements ServiceInfoLoader {
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name="ecUserPrivilegeBiz")
	private ECUserPrivilegeBiz ecUserPrivilegeBiz;
	@Resource
	private ECCourseBiz eCCourseBiz;
	@Resource(name="baseClient")
	private BaseClient baseClient;
	@Override
	public Object load(Map<?, ?> param) {
		ECUserPrivilege userPrivilege=null;
		String uid = null;
		boolean isParent = Boolean.FALSE;
		boolean isCLassMaster = Boolean.FALSE;
		boolean isCourseTeacherViewable = Boolean.FALSE;
		try {
			uid = (String) param.get("uid");
			userPrivilege=ecUserPrivilegeBiz.findEcUserPrivilege(uid);
			if (uid.equals(Constants.ADMIN_UID)) {
				if (userPrivilege==null) {
					userPrivilege=new ECUserPrivilege();
					userPrivilege.setUid(uid);
					userPrivilege.setUserName("admin");
					userPrivilege.setPrivileges(Constants.ROLE_ID_ADMIN);
				}
			} else {
				NbcUcPerson person = this.baseClient.queryPerson(1, uid);
				isParent =person.getType().equals(Constants.PERSON_TYPE_PARENT);
				if(userPrivilege==null){//无此用户权限
					if(isParent){
						NbcUcStudent stu = baseClient.queryStudent(1, baseClient.queryUidPid(1, person.getUid()));//person的自带pid不能用必须进行pid转行  此处用户中心pid对应有问题
						userPrivilege = new ECUserPrivilege();
						userPrivilege.setUid(baseClient.queryUidPid(2,stu.getPid()));
						userPrivilege.setPid(stu.getPid());
						userPrivilege.setUserName(stu.getName());
					}else{//可能：班主任、查看权限教师
						userPrivilege = new ECUserPrivilege();
						userPrivilege.setUid(uid);
						userPrivilege.setPid(person.getPid());
						userPrivilege.setUserName(person.getName());
						
						//对于只有查看权限的教师：可查看/提示无权限
						ECCourseVO ecVo = new ECCourseVO();
						ecVo.setTeacherIdsArr(new String[]{uid});
						List<ECCourseVO> courseList = eCCourseBiz.findPage(ecVo, null);
						isCourseTeacherViewable = courseList!=null && courseList.size()>0;
					}
					
				}
				
				isCLassMaster =this.isClassMaster(uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return new ECUser(userPrivilege,isParent,isCLassMaster, isCourseTeacherViewable);
	}
	
	private Boolean isClassMaster(String uid){
		for (NbcUcDiction diction : this.baseClient.queryIdentity(uid, 2)) {
			if(String.valueOf(diction.getId()).equals(Constants.PERSON_TYPE_CLASS_MASTER)){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}

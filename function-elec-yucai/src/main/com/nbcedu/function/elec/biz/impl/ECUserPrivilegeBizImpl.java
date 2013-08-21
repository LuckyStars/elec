package com.nbcedu.function.elec.biz.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.biz.ECUserPrivilegeBiz;
import com.nbcedu.function.elec.dao.ECUserPrivilegeDao;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBizImpl;
import com.nbcedu.function.elec.model.ECUserPrivilege;
import com.nbcedu.integration.uc.client.facade.BaseClient;
@Repository("ecUserPrivilegeBiz")
public class ECUserPrivilegeBizImpl extends ElecBaseBizImpl<ECUserPrivilege>  implements ECUserPrivilegeBiz {
	
	private ECUserPrivilegeDao ecUserPrivilegeDao;
	@Resource(name="baseClient")
	private BaseClient baseClient;
	
	@Resource(name="ecUserPrivilegeDao")
	public void setEcUserPrivilegeDao(ECUserPrivilegeDao ecUserPrivilegeDao) {
		super.setElecHibernateBaseDao(ecUserPrivilegeDao);
		this.ecUserPrivilegeDao = ecUserPrivilegeDao;
	}

	@Override
	public String addECUers(String userRoles) {
		String errorUserName="";//添加出现错误的用户
		String alreadyAddedUserName="";//已经添加过的用户
		try {
			if (userRoles!=null&&!userRoles.trim().equals("")) {
				String[] userRoleArry=userRoles.split("#");
				Map<String, String> userMap=new HashMap<String, String>();
				for (String userRole : userRoleArry) {//过滤多种身份的用户
					String[] user=userRole.split("/");
					String uid=user[0];
					if (userMap.get(uid)==null) {
						userMap.put(uid, userRole);
					}
				}
				for (String userUid : userMap.keySet()) {
					String[] user=userMap.get(userUid).split("/");
					String uid=user[0];
					String userName=user[1];
					String privileges=user[2];
					String pid=baseClient.queryUidPid(1, uid);
					
					ECUserPrivilege ecUserPrivilege=new ECUserPrivilege();
					ecUserPrivilege.setUid(uid);
					ecUserPrivilege.setPid(pid);
					ecUserPrivilege.setUserName(userName);
					ecUserPrivilege.setPrivileges(privileges);
					if (findEcUserPrivilege(uid)!=null) {
						alreadyAddedUserName+=userName+",";
						continue;
					}
					ECUserPrivilege userPrivilege=ecUserPrivilegeDao.saveEle(ecUserPrivilege);
					if (userPrivilege==null||userPrivilege.getId()==null) {
						errorUserName+=userName+",";
					}
				}
				if (!alreadyAddedUserName.equals("")) {
					alreadyAddedUserName=alreadyAddedUserName+"已经添加过!";
				}
				if (!errorUserName.equals("")) {
					errorUserName=errorUserName+"添加出现错误请重新添加!";
				}
			}
		} catch (Exception e) {
			errorUserName+="抱歉服务器繁忙请稍后使用!";//发生异常
			e.printStackTrace();
		}
		if (!alreadyAddedUserName.equals("")&&!errorUserName.equals("")) {
			return alreadyAddedUserName+errorUserName;
		}else if (!alreadyAddedUserName.equals("")) {
			return alreadyAddedUserName;
		}else if (!errorUserName.equals("")) {
			return errorUserName;
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ECUserPrivilege> findEcUserPrivileges(Page page, String name) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql=new StringBuilder("From ECUserPrivilege ecu where 1=1 ");
		if (name!=null&&!name.trim().equals("")) {
			hql.append(" and ecu.userName like ?");
			params.add("%"+name.trim()+"%");
		}
		return (List<ECUserPrivilege>) ecUserPrivilegeDao.findPage(page,hql.toString(),params.toArray()).getDatas();
	}

	@Override
	public boolean modifyECUers(ECUserPrivilege ecUserPrivilege) {
		ECUserPrivilege ecUserP=ecUserPrivilegeDao.saveOrUpdateEle(ecUserPrivilege);
		return ecUserP!=null&&ecUserP.getId()!=null;
	}

	@Override
	public boolean removeECUer(String id) {
		try {
			ecUserPrivilegeDao.removeById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ECUserPrivilege findEcUserPrivilege(String uid) {
		return ecUserPrivilegeDao.findUnique("From ECUserPrivilege ec where ec.uid=?", uid);
	}

	@Override
	public boolean removeByIdCollection(Collection<String> ids) {
		try {
			for (String string : ids) {
				this.ecUserPrivilegeDao.removeById(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}

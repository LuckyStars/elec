package com.nbcedu.function.elec.biz;

import java.util.Collection;
import java.util.List;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBiz;
import com.nbcedu.function.elec.model.ECUserPrivilege;

public interface ECUserPrivilegeBiz extends ElecBaseBiz<ECUserPrivilege> {
	/**
	 * 批量添加权限  userRole规则 "uid/name/role1,role2,role3#uid/name/role1,role2,role3"
	 * uid/name/role1,role2,role3为一个整体 #为分隔符
	 * @param userRoles 
	 * @return 如果所有添加成功返回为null 否则将返回添加失败的人的名称
	 */
	public String addECUers(String userRoles);
	/**
	 * 权限更新
	 * @param ecUserPrivilege
	 * @return boolean
	 */
	public boolean modifyECUers(ECUserPrivilege ecUserPrivilege);
	/**
	 * 分页得到所有人员权限
	 * @return List&lt;ECUserPrivilege&gt;
	 */
	public List<ECUserPrivilege> findEcUserPrivileges(Page page,String name);
	/**
	 * 删除
	 * @param ecUserPrivilege
	 * @return boolean
	 */
	public boolean removeECUer(String id);
	/**
	 * 根据uid获得此人用户权限
	 * @param uid
	 * @return
	 */
	public ECUserPrivilege findEcUserPrivilege(String uid);
	/**
	 * 按ID批量删除
	 * @return
	 * @author xuechong
	 */
	public boolean removeByIdCollection(Collection<String> ids );
}

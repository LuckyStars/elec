package com.nbcedu.function.elec.action;
/**
 * @author 李斌
 * 权限管理action
 * 
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.nbcedu.function.elec.biz.ECUserPrivilegeBiz;
import com.nbcedu.function.elec.biz.ECUserPrivilegeTreeBiz;
import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.devcore.util.Struts2Utils;
import com.nbcedu.function.elec.model.ECUserPrivilege;
import com.nbcedu.function.elec.util.DictionaryUtil;
import com.nbcedu.function.elec.vo.ECPrivilege;
import com.nbcedu.function.elec.vo.ECUserPrivilegeVo;
import com.opensymphony.xwork2.ActionContext;


@Controller("ecuPgAction")
@Scope("prototype")
public class ECUserPrivilegeAction extends ElecBaseAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="ecUserPrivilegeBiz")
	private ECUserPrivilegeBiz ecUserPrivilegeBiz;
	@Resource(name="ecUserPrivilegeTreeBiz")
	private ECUserPrivilegeTreeBiz ecUserPrivilegeTreeBiz;
	private List<ECUserPrivilege> ecUserPrivileges;
	private String userName;
	private String userRoles;
	private ECUserPrivilege ecUserPrivilege;
	/**
	 * 权限管理列表页
	 * @return
	 */
	public String listPg(){
		try {
			ecUserPrivileges=ecUserPrivilegeBiz.findEcUserPrivileges(page, userName);
			List<ECUserPrivilegeVo> ecUserPrivilegeVos=new ArrayList<ECUserPrivilegeVo>();
			
			for (ECUserPrivilege userPrivileges : ecUserPrivileges) {//装配上角色名称
				ECUserPrivilegeVo userPriVo=new ECUserPrivilegeVo();
				
				userPriVo.setEcUserPrivilege(userPrivileges);
				
				String[] privilegeIdArr=userPrivileges.getPrivileges().split(",");
				String[] roleNames=new String[privilegeIdArr.length];
				for (int i=0;i<privilegeIdArr.length;i++) {
					roleNames[i]=DictionaryUtil.
						getPrivilegeById(privilegeIdArr[i]).getName();
				}
				userPriVo.setNames(roleNames);
				
				ecUserPrivilegeVos.add(userPriVo);
			}
			
			ActionContext.getContext().put("ecUserPrivilegeVos", ecUserPrivilegeVos);
			
			List<ECPrivilege>  privileges=DictionaryUtil.getPrivilegeList();
			ActionContext.getContext().put("privileges", privileges);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	
	/**
	 * 向前台输出json树结构
	 */
	public void ecuserTree(){
		Struts2Utils.OutWrite(ecUserPrivilegeTreeBiz.findNodes().toString());
	}
	
	/**
	 * 权限管理添加
	 */
	public void ecuserAdd(){
		String errorUserNames=ecUserPrivilegeBiz.addECUers(userRoles);
		if (errorUserNames==null) {
			//页面跳转  带上分页值和查询条件给用户更好的体验   
			//(由于form提交数据,url不会改变 ,js跳转改变浏览器url地址 以便于刷新)
			Struts2Utils.OutWrite(
					"<script>window.location='listPgPgAction.action?userName="
					+userName+"&page.offset="+page.getOffset()+"';</script>");
		}else {
			Struts2Utils.OutWrite(
					"<script>alert('"+errorUserNames+"');window.location='listPgPgAction.action?userName="
					+userName+"&page.offset="+page.getOffset()+"';</script>");
		}
	}
	
	/**
	 * 权限管理更新
	 */
	public void ecuserUpdate(){
		ECUserPrivilege userPri=ecUserPrivilegeBiz.findById(ecUserPrivilege.getId());
		userPri.setPrivileges(ecUserPrivilege.getPrivileges());
		if (ecUserPrivilegeBiz.modifyECUers(userPri)) {
			Struts2Utils.OutWrite(
					"<script>window.location='listPgPgAction.action?userName="
						+userName+"&page.offset="+page.getOffset()+"';</script>");
		}else {
			Struts2Utils.OutWrite("<script>alert('更新失败!');history.go(-1);</script>");
		}
		
	}
	
	/**
	 * 权限管理删除
	 */
	public void ecuserDel(){
		boolean bool=ecUserPrivilegeBiz.removeECUer(id);
		if (bool) {
			Struts2Utils.OutWrite(
					"<script>window.location='listPgPgAction.action?userName="
					+userName+"&page.offset="+page.getOffset()+"';</script>");
		}else {
			Struts2Utils.OutWrite("<script>alert('删除失败!');history.go(-1);</script>");
		}
	}
	
	/***
	 * 权限管理批量删除
	 */
	public void ecuserDels(){
		try {
			if (id!=null&&!id.trim().isEmpty()) {
				if (this.ecUserPrivilegeBiz.removeByIdCollection(Arrays.asList(id.split(",")))) {
					Struts2Utils.OutWrite(
							"<script>window.location='listPgPgAction.action?userName="
							+userName+"&page.offset="+page.getOffset()+"';</script>");
				}else {
					Struts2Utils.OutWrite("<script>alert('删除失败!');history.go(-1);</script>");
				}
			}else {
				Struts2Utils.OutWrite(
						"<script>window.location='listPgPgAction.action?userName="
						+userName+"&page.offset="+page.getOffset()+"';</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.OutWrite("<script>alert('删除失败!');history.go(-1);</script>");
		}
	}
	//get&set
	public List<ECUserPrivilege> getEcUserPrivileges() {
		return ecUserPrivileges;
	}
	public void setEcUserPrivileges(List<ECUserPrivilege> ecUserPrivileges) {
		this.ecUserPrivileges = ecUserPrivileges;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}
	public ECUserPrivilege getEcUserPrivilege() {
		return ecUserPrivilege;
	}
	public void setEcUserPrivilege(ECUserPrivilege ecUserPrivilege) {
		this.ecUserPrivilege = ecUserPrivilege;
	}
}

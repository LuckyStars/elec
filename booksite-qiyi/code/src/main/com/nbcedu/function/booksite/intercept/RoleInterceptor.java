package com.nbcedu.function.booksite.intercept;

import java.util.ArrayList;
import java.util.Map;

import com.nbcedu.core.privilege.model.Role;
import com.nbcedu.core.privilege.model.User;
import com.nbcedu.function.booksite.biz.BSUserRoleBiz;
import com.nbcedu.function.booksite.constants.RoleConstants;
import com.nbcedu.function.booksite.model.BSUserRole;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class RoleInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	private BSUserRoleBiz userRoleBiz;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		User curUser = (User)invocation.getInvocationContext().getSession().get("user");
		if(curUser==null){
			Map<String, Object> map = (Map<String, Object>)invocation.getInvocationContext().getSession().get("bookSite_init");
			if (map!=null&&!map.isEmpty()) {
				User initUser = (User) map.get("user");
				String uid = initUser.getUid();
				if(uid.equals("1")){
					Role r = new Role();
					r.setName(RoleConstants.BOOK_SITE_ADMIN);
					initUser.setRoles(new ArrayList<Role>());
					initUser.getRoles().add(r);
				}else{
					BSUserRole userRole = this.userRoleBiz.findUserRoleByUserId(uid);
					if(userRole!=null&&userRole.getRoleArray().contains("1")){
						Role r = new Role();
						r.setName(RoleConstants.BOOK_SITE_ADMIN);
						initUser.setRoles(new ArrayList<Role>());
						initUser.getRoles().add(r);
					}else{
						Role r = new Role();
						r.setName(RoleConstants.BOOK_SITE_COMMON);
						initUser.setRoles(new ArrayList<Role>());
						initUser.getRoles().add(r);
					}
				}
				invocation.getInvocationContext().getSession().put("user",initUser);
			}else{
				return Action.LOGIN;
			}
		}
		return invocation.invoke();
	}
	
	public void setUserRoleBiz(BSUserRoleBiz userRoleBiz) {
		this.userRoleBiz = userRoleBiz;
	}
	
}

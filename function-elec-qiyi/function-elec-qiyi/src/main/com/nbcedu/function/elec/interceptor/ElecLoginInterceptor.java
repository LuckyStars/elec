package com.nbcedu.function.elec.interceptor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nbcedu.function.elec.action.ECSignAction;
import com.nbcedu.function.elec.action.IndexAction;
import com.nbcedu.function.elec.util.CommonUtil;
import com.nbcedu.function.elec.vo.ECUser;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登录验证拦截器
 * 
 * @author qinyuan
 * @since 2013-2-26
 */
@Component(value="elecLoginInterceptor")
@Scope("prototype")
public class ElecLoginInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ECUser user = CommonUtil.getCurrentUser();
		if (user != null) {
			if(user.isParent()){//家长
				if(!(invocation.getProxy().getAction() instanceof ECSignAction) 
						&& !(invocation.getProxy().getAction() instanceof IndexAction)){
					return "noRole";
				}else {
					return invocation.invoke();
				}
			}
			
			if(!user.isAdmin() && !user.isManager() && !user.isCourseTeacher() && !user.isClassMaster() && !user.isCourseTeacherViewable()){//不可查看权限的教师
				return "noRole";
			}else {
				return invocation.invoke();
			}
			
		} else {
			return "noRole";
		}
	}
}

package com.nbcedu.function.booksite.intercept;

import java.util.Map;

import com.nbcedu.core.privilege.model.Role;
import com.nbcedu.core.privilege.model.User;
import com.nbcedu.function.booksite.constants.RoleConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class AdminIntercept extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		Map session = context.getSession();
		User user = (User) session.get("user");
		// Map<String,Object> map = new HashMap<String,Object>();
		// //测试开始
		// // User u1 = new User();
		// // u1.setName("admin");
		// // u1.setUserType("100");
		// // u1.setUid("1");
		// // map.put("user", u1);
		// // context.getSession().put("bookSite_init", map);
		// // //测试结束
		// HttpServletRequest request = ServletActionContext.getRequest();
		// HttpSession sessions = request.getSession();
		// map = (Map<String,Object>)sessions.getAttribute("bookSite_init");
		// if(!map.isEmpty()){
		// User user1 = (User)map.get("user");
		// map.clear();
		// map = (Map<String,Object>)this.load(user1);
		// user1 = (User)map.get("user");
		// context.getSession().put("user", user1);
		// }
		if (user != null) {
			if(user.getUserType().equals("100")){
				return invocation.invoke();
			}
			if(user.getRoles()!=null&&user.getRoles().size()>0){
				for (Role r : user.getRoles()) {
					if (r.getName().equals(RoleConstants.BOOK_SITE_ADMIN)) {
						return invocation.invoke();
					}
				}
			}
			if(user.getUid()!=null){
				return invocation.invoke();
			}
		}
		context.put("tip", "长时间未操作，请重新登录");
		return Action.LOGIN;
	}
}

package com.nbcedu.function.elec.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.util.CommonUtil;
import com.nbcedu.function.elec.util.Constants;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Controller("indexAction")
@Scope("prototype")
public class IndexAction extends ElecBaseAction {

	public String execute() {
		// 报名策略
		ActionContext.getContext().getSession().put(Constants.SESSION_SIGN_TYPE_KEY, Constants.SIGN_TYPE);
		if (CommonUtil.getCurrentUser().isParent()) {
			return "ecsign";
		}
		return "teacher";
	}
}

package com.nbcedu.function.elec.interceptor;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.biz.ECTermBiz;
import com.nbcedu.function.elec.model.ECTerm;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;


@Component(value="termStartInterceptor")
@Scope("prototype")
public class TermStartInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = 1L;
	@Resource(name="elecTermBiz")
	private ECTermBiz termbiz;
	@Resource(type=ECCourseBiz.class)
	private ECCourseBiz courseBiz;
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ECTerm term = this.termbiz.findCurrentTerm();
		if(term==null){
			return invocation.invoke();
		}
		String methodName = invocation.getProxy().getMethod();
		if (methodName.equals("gotoAdd")||methodName.equals("add")){
			if(this.termbiz.termStart(term)){//如果当前学期正处于开放时间段则不允许新增
				return "timeup";
			}
		}else if(term.getOpenDateStart().before(new Date())){//已经开放后不可以更改
			return "timeup";
		}else {//如果学期下已有课程则不允许修改操作
			if(this.courseBiz.findCountByTerm(term.getId())>0){
				return "hascourse";
			}
		}
		return invocation.invoke();
	}

}

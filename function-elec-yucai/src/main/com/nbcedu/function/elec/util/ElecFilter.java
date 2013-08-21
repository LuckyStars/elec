package com.nbcedu.function.elec.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nbcedu.core.framework.filter.ServiceInfoLoader;

public class ElecFilter implements Filter{
		 
	private static Logger logger = Logger.getLogger(ElecFilter.class);

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String serviceName = getServiceName(req);

		if (serviceName.equals("exclusive")) {
			if(logger.isInfoEnabled()){
				logger.info("exclusive:" + serviceName);
			}
			chain.doFilter(req, response);
			return;
		}

		if (req.getSession().getAttribute(getAttributeName(serviceName)) == null) {
			Map paramMap = new HashMap();
			String uid = (String) req.getSession().getAttribute("edu.yale.its.tp.cas.client.filter.user");
			if ((uid == null) || ("".equals(uid))) {
				logger.info("===单点登录中获取的UID为null，无法载入loader...");
				resp.sendRedirect(req.getContextPath() + "/teacherIndex.jsp");
				return;
			}
			if(logger.isInfoEnabled()){
				logger.info("登陆 UID" + uid);
			}
			paramMap.put("uid", uid);
			Object obj = getLoader(serviceName, req).load(paramMap);
			req.getSession().setAttribute(getAttributeName(serviceName), obj);
		}

		resp.setHeader("P3P","CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
		chain.doFilter(req, response);
	}
	
	private String getServiceName(HttpServletRequest request) {
		String functionName = null;
	
	    if (request.getContextPath().contains("schoolapp"))
	      functionName = request.getRequestURI().replaceFirst(request.getContextPath() + "/", "");
	    else {
	      functionName = "elec";
	    }

	    if (functionName.contains("/")) {
	    	int i = functionName.indexOf("/");
	    	functionName = functionName.substring(0, i);
	    }
	    return functionName;
	}
	
	private String getAttributeName(String serviceName) {
		return serviceName + "_init";
	}
	
	private String getLoaderName(String serviceName) {
		return serviceName + "Loader";
	}
	
	private ServiceInfoLoader getLoader(String serviceName, HttpServletRequest request) {
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		return (ServiceInfoLoader)ac.getBean(getLoaderName(serviceName));
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {}
	public void destroy() {}
}


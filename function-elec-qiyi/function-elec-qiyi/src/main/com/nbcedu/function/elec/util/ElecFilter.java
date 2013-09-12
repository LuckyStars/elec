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

public class ElecFilter implements Filter {

	private static Logger logger = Logger.getLogger(ElecFilter.class);

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String serviceName = getServiceName(req);

		if (serviceName.equals("exclusive")) {
			chain.doFilter(req, response);
			return;
		}
/*		
 * 71 filter
 * if (req.getSession().getAttribute(getAttributeName(serviceName)) == null) {
			Map paramMap = new HashMap();

			Map attrs = (Map) req.getSession().getAttribute("client.user.attributes");
			
			if(logger.isInfoEnabled()){
				for (Object key : attrs.keySet()) {
					logger.info( "key:" + key + "|| " + "value:" + attrs.get(key));
				}
			}
			
			if ((attrs == null) || ("".equals(attrs.get("exuserid")))) {
				resp.sendRedirect(req.getContextPath() + "/teacherIndex.jsp");
				return;
			}
			
			String uid = (String) attrs.get("exuserid");
			if ((uid == null) || ("".equals(uid))) {
				chain.doFilter(req, response);
				return;
			}
			
			paramMap.put("uid", uid);
			Object obj = getLoader(serviceName, req).load(paramMap);
			req.getSession().setAttribute(getAttributeName(serviceName), obj);
		}*/
		
		if (req.getSession().getAttribute(getAttributeName(serviceName)) == null) {
			Map paramMap = new HashMap();
			String uid = (String) req.getSession().getAttribute("edu.yale.its.tp.cas.client.filter.user");
			if ((uid == null) || ("".equals(uid))) {
				logger.info("===单点登录中获取的UID为null，无法载入loader...");
				resp.sendRedirect(req.getContextPath() + "/teacherIndex.jsp");
				return;
			}
			paramMap.put("uid", uid);
			Object obj = getLoader(serviceName, req).load(paramMap);
			req.getSession().setAttribute(getAttributeName(serviceName), obj);
		}

		resp.setHeader("P3P", "CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
		chain.doFilter(req, response);
	}

	private String getServiceName(HttpServletRequest request) {
		return "elec";
	}

	private String getAttributeName(String serviceName) {
		return serviceName + "_init";
	}

	private String getLoaderName(String serviceName) {
		return serviceName + "Loader";
	}

	private ServiceInfoLoader getLoader(String serviceName, HttpServletRequest request) {
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getSession()
				.getServletContext());
		return (ServiceInfoLoader) ac.getBean(getLoaderName(serviceName));
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}
}

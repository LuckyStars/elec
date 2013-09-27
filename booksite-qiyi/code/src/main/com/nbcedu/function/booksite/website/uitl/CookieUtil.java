package com.nbcedu.function.booksite.website.uitl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * <p>Cookie 操作</p>
 * @author 黎青春
 * Create at:2012-4-18 下午03:36:02
 */
public class CookieUtil {

	/**
	 * 读取 Cookie
	 * @author 黎青春
	 * @param request
	 * @param key
	 * @return
	 */
	public static String readCookie(HttpServletRequest request,String key){
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
		    for (int i = 0; i < cookies.length; i++)  {
		       Cookie c = cookies[i];     
		       if(c.getName().equalsIgnoreCase(key)) {
		          return c.getValue();
		        }
		    } 
		  }
		return null;
		
	}
	/**
	 * 写入 Cookie
	 * @author 黎青春
	 * @param response
	 * @param key
	 * @return
	 */
	public static void addCookie(HttpServletResponse response,String key,String value){
		if(value!=null && !"".equals(value)){
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(60*60*24*365);  // 默认1年
			cookie.setPath("/"); 
			response.addCookie(cookie);
		}
	}
	
	
}

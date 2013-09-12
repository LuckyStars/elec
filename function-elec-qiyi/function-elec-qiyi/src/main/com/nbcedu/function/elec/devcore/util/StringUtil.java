package com.nbcedu.function.elec.devcore.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author 作者 黎巍 测试通过
 * @version 版本 0.01
 * @filename 文件名 StringUtil.java
 * @date 创建日期 Feb 15, 2011
 * @description 描述 字符串处理类
 */
public class StringUtil {

	private StringUtil() {
	}

	/**
	 * 方法名称:isInteger 作者:黎巍 创建日期:Oct 22, 2010 方法描述:判断是否为整数
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * common自带的 isNumeric 只能判断正数（负数、小数点没法判断）
	 * 
	 * @author qinyuan
	 * @time 2011-11-29
	 * @version 1.0
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	

	/**
	 * 字符串转换到html语法
	 * 
	 * @author qinyuan
	 * @return
	 */
	public static String toHtml(String s) {
		if(StringUtils.isBlank(s)){
			return s;
		}
		
		s = s.replaceAll("&", "&amp;")
			 .replaceAll("<", "&lt;")
			 .replaceAll(">", "&gt;")
			 .replaceAll("\"", "&quot;");
			 //.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
			 //.replaceAll("\r\n", "\n")
			 // Replace(s,"\"","'");
			 //.replaceAll("\n", "<br>")
			 //.replaceAll("  ", "&nbsp;&nbsp;")
			 // Replace(s,"","'");
			 //.replaceAll("'", "&#39;");
			 //s.replaceAll("\\", "&#92;");
		return s;
	}

	/**
	 * 相对于上一接口的反转换
	 * 
	 * @author qinyuan
	 * @return
	 */
	public static String unHtml(String s) {
		if(StringUtils.isBlank(s)){
			return s;
		}
		
		s = s.replaceAll("<br>", "\n")
			.replaceAll("&lt;", "<")
			.replaceAll("&nbsp;", " ")
			.replaceAll("&gt;", ">");
		return s;
	}
	
	
	/**
	 * windows文件名不允许的特殊字符
	 * @author qinyuan
	 */
	public static String clearWinChars(String s) {
		if(StringUtils.isBlank(s)){
			return s;
		}
		
		s = s.replace("\\", "")
			 .replace("/", "")
			 .replace(":", "")
			 .replace("*", "")
			 .replace("?", "")
			 .replace("\"", "")
			 .replace("<", "")
			 .replace(">", "")
			 .replace("|", "");
		return s;
	}
	
	/**
	 * Excel不允许的特殊字符
	 * @author qinyuan
	 */
	public static String clearExlChars(String s) {
		if(StringUtils.isBlank(s)){
			return s;
		}
		
		s = s.replace("\\", "")
		.replace("/", "")
		.replace(":", "")
		.replace("*", "")
		.replace("?", "")
		.replace("[", "")
		.replace("]", "");
		return s;
	}
	
	
	/**
	 * 方法名称:getRandom 作者:黎巍 创建日期:Oct 25, 2010 方法描述:返回一个i位随机数
	 * 
	 * @param i
	 * @return String
	 */
	public static String getRandom(int i) {
		Random jjj = new Random();
		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 方法名称:changeNull 作者:黎巍 创建日期:Oct 25, 2010 方法描述:字符串是null就转化为“”
	 * 
	 * @param target
	 * @return String
	 */
	public static String changeNull(String target) {
		if (target == null)
			return "";
		return target;
	}

	/**
	 * url下一个链接符号
	 * 
	 * @param url
	 * @return
	 */
	public static String nextFgf(String url) {
		if (url != null && !"".equals(url)) {
			if (url.indexOf("?") != -1) {
				return "&";
			} else {
				return "?";
			}

		}
		return "";
	}

	/**
	 * 将字符串数组转换为Integer类型的，target为null或长度为0，则返回一个长度为0的Integer数组
	 * 
	 * @author 杜海瀛
	 * @param target 待转换的字符串数组
	 * @return
	 */
	public static Integer[] stringArrayToInteger(String[] target) {

		if (target == null) {
			return new Integer[] {};
		}

		int l = target.length;

		Integer[] ids = new Integer[l];

		for (int i = 0; i < l; i++) {
			ids[i] = new Integer(target[i]);
		}
		return ids;

	}

	/**
	 * 判断是否为null或“”
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	/**
	 * 方法名称:changeNull 作者:黎巍 创建日期:Oct 25, 2010 方法描述:字符串是null就转化为“”
	 * 
	 * @param target
	 * @return String
	 */
	public static String convertNull(String target) {
		if (target == null)
			return "";
		return target;
	}
	
	public static String substringBefore(String str, String separator) {
		if ((isEmpty(str)) || (separator == null)) {
			return str;
		}
		if (separator.length() == 0) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? false : str2 == null ? true : str1.equalsIgnoreCase(str2);
	}


	/**
	 * 采用正则表达式将包含有 单引号(')，分号(;) 和 注释符号(--)的语句给替换掉来防止SQL注入
	 * 
	 * @author dhy
	 * @param str 待验证的字符串
	 * @return 安全的字符串
	 */
	public static String transactSQLInjection(String str) {
		if (str == null) {
			return str;
		} else {
			return str.replaceAll(".*([';]+|(--)+).*", " ");
		}

	}
	
	/** 
	 * 返回指定Date对象转换成的时间字符串，格式为
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date 指定要转换的Date对象
	 * @return 格式化的时间字符串
	 */ 
	public static String getDateTimeString(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(date);
	}

}
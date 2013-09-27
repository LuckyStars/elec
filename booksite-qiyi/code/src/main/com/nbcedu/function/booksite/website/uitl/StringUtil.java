package com.nbcedu.function.booksite.website.uitl;


import java.io.UnsupportedEncodingException;
import java.text.StringCharacterIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author       作者 黎巍 测试通过
 * @version      版本 0.01
 * @filename     文件名 StringUtil.java
 * @date         创建日期 Feb 15, 2011
 * @description  描述 字符串处理类
 */
public class StringUtil {
	
	private StringUtil(){
		
	}
	
	private static Log log = LogFactory.getLog(StringUtil.class);

	/**
	 * 方法名称:isInteger
	 * 作者:黎巍
	 * 创建日期:Oct 22, 2010
	 * 方法描述:判断是否为整数  
	 * @param str
	 * @return boolean
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断一个数是否是双精度型数
	 * 
	 * @param validString
	 * @return
	 */
	public static boolean isDouble(String validString) {
		if (validString == null)
			return false;
		int k = 0;
		byte[] tempbyte = validString.getBytes();
		for (int i = 0; i < validString.length(); i++) {
			if (tempbyte[i] == 46) {
				k++;
			}
		}
		if (k > 1)
			return false; // k>1表示有两个以上的小数点,为非法数字
		for (int i = 0; i < validString.length(); i++) {
			if (tempbyte[i] != 46) { // 46为"."的ASCII码
				// ,48为"0"的ASCII码,57是"9"的ASCII码
				if ((tempbyte[i] < 48) || (tempbyte[i] > 57)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否只包括字母和数字
	 * 
	 * @param validString
	 * @return
	 */
	public static boolean isChar(String validString) {
		if (validString == null)
			return false;
		byte[] tempbyte = validString.getBytes();
		for (int i = 0; i < validString.length(); i++) {
			if ((tempbyte[i] < 48) || ((tempbyte[i] > 57) & (tempbyte[i] < 65))
					|| (tempbyte[i] > 122)
					|| ((tempbyte[i] > 90) & (tempbyte[i] < 97))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否只包含字母
	 * 
	 * @param validString
	 * @return
	 */
	public static boolean isLetter(String validString) {
		if (validString == null)
			return false;
		byte[] tempbyte = validString.getBytes();
		for (int i = 0; i < validString.length(); i++) {
			if ((tempbyte[i] < 65) || (tempbyte[i] > 122)
					|| ((tempbyte[i] > 90) & (tempbyte[i] < 97))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断一个字符串是否包含另一个字符串
	 * 
	 * @param source
	 * @param findString
	 * @return
	 */
	public static boolean isInclude(String source, String findString) {
		if (source == null)
			return false;
		if (findString == null)
			return false;
		int posStart = 0;
		int pos = source.indexOf(findString, posStart);
		boolean jj = false;
		if (pos >= 0) {
			jj = true;
		}
		return jj;
	}

	/**
	 * 字符串转换到html语法
	 * 
	 * @param s
	 * @return
	 */
	public static String toHtml(String s) {
		s.replaceAll("&", "&amp;");
		s.replaceAll("<", "&lt;");
		s.replaceAll(">", "&gt;");
		s.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		s.replaceAll("\r\n", "\n");
		// Replace(s,"\"","'");
		s.replaceAll("\n", "<br>");
		s.replaceAll("  ", "&nbsp;&nbsp;");
		// Replace(s,"","'");
		s.replaceAll("'", "&#39;");
		s.replaceAll("\\", "&#92;");
		return s;
	}

	/**
	 * 相对于上一接口的反转换
	 * 
	 * @param s
	 * @return
	 */
	public static String unHtml(String s) {
		s.replaceAll("<br>", "\n");
		s.replaceAll("&lt;", "<");
		s.replaceAll("&nbsp;", " ");
		s.replaceAll("&gt;", ">");
		return s;
	}

	/**
	 * 方法名称:getRandom
	 * 作者:黎巍
	 * 创建日期:Oct 25, 2010
	 * 方法描述:返回一个i位随机数  
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

	/***************************************************************************
	 * 判断是否是时间
	 **************************************************************************/
	public static boolean isdate(String strdate) {
		if (strdate == null || strdate.equals(""))
			return false;
		if (strdate.length() != 8 && strdate.length() != 10)
			return false;

		String strarray[];
		int intyear = 1990, intmonth = 12, intday = 10;
		strarray = strdate.split("-");

		if (strdate.length() == 10) {
			if (strarray.length != 3) {
				strarray = strdate.split("/");
				if (strarray.length != 3)
					return false;
			}
			intyear = Integer.parseInt(strarray[0]);
			intmonth = Integer.parseInt(strarray[1]);
			intday = Integer.parseInt(strarray[2]);
		}
		if (strdate.length() == 8) {
			intyear = Integer.parseInt(strdate.substring(0, 4));
			intmonth = Integer.parseInt(strdate.substring(4, 6));
			intday = Integer.parseInt(strdate.substring(6, 8));
		}
		if (intyear > 9999 || intyear < 1000) {
			return false;
		}
		if (intmonth > 12 || intmonth < 1) {
			return false;
		}

		if (((intmonth == 1) || (intmonth == 3) || (intmonth == 5)
				|| (intmonth == 7) || (intmonth == 8) || (intmonth == 10) || (intmonth == 12))
				&& ((intday > 31) || (intday < 1))) {
			return false;
		}

		if ((intmonth == 4 || intmonth == 6 || intmonth == 9 || intmonth == 11)
				&& (intday > 30 || intday < 1)) {
			return false;
		}

		if (intmonth == 2) {
			if (intday < 1) {
				return false;
			}
			boolean boolLeapYear = false;
			if ((intyear % 100) == 0) {
				if ((intyear % 400) == 0)
					boolLeapYear = true;
			} else {
				if ((intyear % 4) == 0)
					boolLeapYear = true;
			}
			if (boolLeapYear) {
				if (intday > 29) {
					return false;
				}
			} else {
				if (intday > 28) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 方法名称:changeNull
	 * 作者:黎巍
	 * 创建日期:Oct 25, 2010
	 * 方法描述:字符串是null就转化为“”  
	 * @param target
	 * @return String
	 */
	public static String changeNull(String target) {
		if (target == null)
			return "";
		return target;
	}

	/**
	 * 方法名称:TranEncodeTOGB2312
	 * 作者:黎巍
	 * 创建日期:Oct 25, 2010
	 * 方法描述:将字符串编码格式转成GB2312  
	 * @param str
	 * @return String
	 */
	public static String TranEncodeTOGB2312(String str) {
		try {
			String strEncode = StringUtil.getEncoding(str);
			String temp = new String(str.getBytes(strEncode), "gb2312");
			return temp;
		} catch (java.io.IOException ex) {
			return null;
		}
	}

	/** 
	 * 判断输入字符是否为gb2312的编码格式 
	 * 
	 * @param c 输入字符 
	 * @return 如果是gb2312返回真，否则返回假 
	 */
	public static boolean isGB2312(char c) {
		Character ch = new Character(c);
		String sCh = ch.toString();
		try {
			byte[] bb = sCh.getBytes("gb2312");
			if (bb.length > 1) {
				return true;
			}
		} catch (java.io.UnsupportedEncodingException ex) {
			return false;
		}
		return false;
	}

	/**
	 * 方法名称:getEncoding
	 * 作者:黎巍
	 * 创建日期:Oct 25, 2010
	 * 方法描述:判断字符串的编码  
	 * @param str
	 * @return String
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode)))
				return "GB2312";
		} catch (UnsupportedEncodingException e) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode)))
				return "ISO-8859-1";
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode)))
				return "UTF-8";
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode)))
				return "GBK";
		} catch (Exception exception3) {
		}
		return "";
	}

    /**
     * 方法名称:reEncoding
     * 作者:黎巍
     * 创建日期:Oct 25, 2010
     * 方法描述:对字符串重新编码  
     * @param text  字符串
     * @param resEncoding 源编码
     * @param newEncoding 新编码 
     * @return String 重新编码后的字符串
     */
    public static String reEncoding(String text, String resEncoding, String newEncoding) { 
            String rs = null; 
            try { 
                    rs = new String(text.getBytes(resEncoding), newEncoding); 
            } catch (UnsupportedEncodingException e) { 
                    log.error("读取文件为一个内存字符串失败，失败原因是使用了不支持的字符编码"); 
                    throw new RuntimeException(e); 
            } 
            return rs; 
    } 


	/**
	 * 字符串替换
	 * 
	 * @param source
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static String Replace(String source, String oldString, String newString) {
		if (source == null)
			return null;
		StringBuffer output = new StringBuffer();
		int lengOfsource = source.length();
		int lengOfold = oldString.length();
		int posStart = 0;
		int pos;
		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));
			output.append(newString);
			posStart = pos + lengOfold;
		}
		if (posStart < lengOfsource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}



	/***************************************************************************
	 * 判断是否是时间
	 **************************************************************************/
	public static boolean Isdate(String strdate) {
		if (strdate == null || strdate.equals(""))
			return false;
		if (strdate.length() != 8 && strdate.length() != 10)
			return false;

		String strarray[];
		int intyear = 1990, intmonth = 12, intday = 10;
		strarray = strdate.split("-");

		if (strdate.length() == 10) {
			if (strarray.length != 3) {
				strarray = strdate.split("/");
				if (strarray.length != 3)
					return false;
			}
			intyear = Integer.parseInt(strarray[0]);
			intmonth = Integer.parseInt(strarray[1]);
			intday = Integer.parseInt(strarray[2]);
		}
		if (strdate.length() == 8) {
			intyear = Integer.parseInt(strdate.substring(0, 4));
			intmonth = Integer.parseInt(strdate.substring(4, 6));
			intday = Integer.parseInt(strdate.substring(6, 8));
		}
		if (intyear > 9999 || intyear < 1000) {
			return false;
		}
		if (intmonth > 12 || intmonth < 1) {
			return false;
		}

		if (((intmonth == 1) || (intmonth == 3) || (intmonth == 5) || (intmonth == 7) || (intmonth == 8)
				|| (intmonth == 10) || (intmonth == 12))
				&& ((intday > 31) || (intday < 1))) {
			return false;
		}

		if ((intmonth == 4 || intmonth == 6 || intmonth == 9 || intmonth == 11)
				&& (intday > 30 || intday < 1)) {
			return false;
		}

		if (intmonth == 2) {
			if (intday < 1) {
				return false;
			}
			boolean boolLeapYear = false;
			if ((intyear % 100) == 0) {
				if ((intyear % 400) == 0)
					boolLeapYear = true;
			} else {
				if ((intyear % 4) == 0)
					boolLeapYear = true;
			}
			if (boolLeapYear) {
				if (intday > 29) {
					return false;
				}
			} else {
				if (intday > 28) {
					return false;
				}
			}
		}
		return true;
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
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
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
	 * htmlSpecialChars:(将特殊字符串转换为HTML语法)
	 * @author Wang QingQiang
	 * Create at:Aug 2, 2011 10:23:14 AM
	 * @param text
	 * @param quotestyle
	 * @return
	 */
	public static String htmlSpecialChars(String text, int quotestyle) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			switch (character) {
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '"':
				if (quotestyle == 1 || quotestyle == 2) {
					sb.append("&quot;");
				} else {
					sb.append(character);
				}
				break;
			case '\'':
				if (quotestyle == 2) {
					sb.append("&#039;");
				} else {
					sb.append(character);
				}
				break;
			default:
				sb.append(character);
				break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	
	/**
	 * 采用正则表达式将包含有 单引号(')，分号(;) 和 注释符号(--)的语句给替换掉来防止SQL注入
	 * 
	 * @author dhy
	 * @param str 待验证的字符串
	 * @return 安全的字符串
	 */
	public static String transactSQLInjection(String str) 
	{ 
		if(str==null){
			return str;
		}else{
			 return str.replaceAll(".*([';]+|(--)+).*", ""); 
		}
	   
	}  
	
    /**
     * trimSpc:(去除左右空格)
     * @author Wang QingQiang
     * Create at:Aug 12, 2011 10:01:11 AM
     * @param val
     * @return
     */
	public static String trimSpc(String val) {
		if (val == null) {
			return "";
		} else {
			val = val.trim();
		}
		int iHead = 0;
		for (int i = 0; i < val.length(); i++) {
			if (val.charAt(i) == ' ' || val.charAt(i) == '　') {
				iHead++;
			} else {
				break;
			}
		}
		String valUse = val.substring(iHead);
		if (null != valUse) {
			int iEnd = valUse.length();
			for (int i = valUse.length() - 1; i >= 0; i--) {
				if (valUse.charAt(i) == ' ' || valUse.charAt(i) == '　') {
					iEnd--;
				} else {
					break;
				}
			}
			valUse = valUse.substring(0, iEnd);
		}
		return valUse;
	}
	
	/** 
	  * java实现不区分大小写替换 
	  * @param source 
	  * @param oldstring 
	  * @param newstring 
	  * @return 
	  */ 
	public static String ignoreCaseReplace(String source, String oldstring, 
	   String newstring){ 
	      Pattern p = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE); 
	      Matcher m = p.matcher(source); 
	      String ret=m.replaceAll(newstring); 
	      return ret; 
	} 
	
	/**
	 * 数字字符串转为int型整数
	 * 
	 * @author Du Haiying
	 * @param numStr 数字字符串
	 * @return
	 */
	public static int stringToInt(String numStr){
		
		if(isInteger(numStr)){
			return Integer.parseInt(numStr);
		}else{
			return 0;
		}
	}
	
}
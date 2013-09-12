package com.nbcedu.function.elec.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * 日期 --> 字符串
	 * @param pattern 转换格式，例如：yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date);
		}
		return null;
	}

	/**
	 * 字符串 --> 日期
	 * @param pattern 转换格式，例如：yyyy-MM-dd HH:mm:ss
	 */
	public static Date str2Date(String source, String pattern) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.parse(source);
	}

}
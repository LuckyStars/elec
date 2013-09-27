package com.nbcedu.function.booksite.convertor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class BookSiteConvertor extends StrutsTypeConverter{

	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		try {
			return df.parse(values[0]);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String convertToString(Map context, Object o) {
		return df.format(o);
	}

}

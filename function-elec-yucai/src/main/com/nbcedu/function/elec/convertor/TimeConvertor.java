package com.nbcedu.function.elec.convertor;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.ibm.icu.text.SimpleDateFormat;

public class TimeConvertor extends StrutsTypeConverter{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
        String str = values[0];  
        Date date = null;
        try {
			date = DateUtils.parseDate(str, new String[]{"HH:mm:ss"});
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	@Override
	public String convertToString(Map context, Object o) {
		Date date = (Date)o;
		return sdf.format(date);
	}

}

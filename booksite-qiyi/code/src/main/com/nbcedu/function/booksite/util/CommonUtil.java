package com.nbcedu.function.booksite.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	/**
	 * 得到去除HTML标签后的纯文本
	 * @param htmlstring html字符串
	 * @return 返回去除HTML标签后的纯文本字符串
	 */
	public static String htmlToText(String htmlstring) {
		Pattern p = Pattern.compile("</p([\\d\\D]*?)>",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(htmlstring);
		htmlstring = m.replaceAll("\r\n");
		p = Pattern.compile("<p([\\d\\D]*?)>", Pattern.CASE_INSENSITIVE);
		m = p.matcher(htmlstring);
		htmlstring = m.replaceAll("\r\n");
		p = Pattern.compile("<br([\\d\\D]*?)>", Pattern.CASE_INSENSITIVE);
		m = p.matcher(htmlstring);
		htmlstring = m.replaceAll("\r\n");
		p = Pattern.compile("<br([\\d\\D]*?)/>", Pattern.CASE_INSENSITIVE);
		m = p.matcher(htmlstring);
		htmlstring = m.replaceAll("\r\n");

		String code = "<.*?>(<script.*?>.*?</script.*?>)?(<style.*?>.*?</style.*?>)?";
		htmlstring = htmlstring.replaceAll(code, "");
		return htmlstring;
	}

	public static String toHTML(String line) {

		if (line == null)
			return "&nbsp;";

		final int RETURN = 0x0D;
		final int RETURN2 = 0X0A;
		final int BLANK = 32;
		final int FULLBLANK = 12288;

		char[] source = line.toCharArray();
		StringBuffer target = new StringBuffer("");

		for (int i = 0; i < source.length; i++) {

			char temp = source[i];

			if (temp == '"')
				target.append("&quot;");

			else if (temp == '<')
				target.append("&lt;");

			else if (temp == '>')
				target.append("&gt;");

			else if (temp == BLANK)
				target.append("&nbsp;");

			else if (temp == FULLBLANK)
				target.append("&nbsp;");

			else if (temp == RETURN) {
				if (source[i + 1] == RETURN2) {
					i++;
				}
				target.append("</p><p>");
			} else if (temp == RETURN2)
				target.append("</p><p>");
			else
				target.append(source[i]);
		}// end of while
		String contentString = target.toString();
		contentString = "<p>" + contentString + "</p>";
		contentString = contentString.replaceAll("<p>((&nbsp;)+)", "<p>");
		contentString = contentString.replaceAll("((&nbsp;)+)</p>", "</p>");
		contentString = contentString.replaceAll("<p>((&amp;nbsp;)+)", "<p>");
		contentString = contentString.replaceAll("((&amp;nbsp;)+)</p>", "</p>");
		contentString = contentString.replaceAll("<p></p>", "");

		return contentString;
	}
}

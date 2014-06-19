package com.hzth.myapp.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String formatToString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getCurrentDate2() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
	}

	public static Date getDate(String dateStr) {
		String pattern0 = "yyyy-MM-dd HH:mm:ss";
		String pattern1 = "yyyy-MM-dd HH:mm";
		String pattern2 = "yyyy-MM-dd";
		SimpleDateFormat sdf = null;
		if (pattern0.length() == dateStr.length()) {
			sdf = new SimpleDateFormat(pattern0);
		}
		if (pattern1.length() == dateStr.length()) {
			sdf = new SimpleDateFormat(pattern1);
		}
		if (pattern2.length() == dateStr.length()) {
			sdf = new SimpleDateFormat(pattern2);
		}
		Date result = null;
		try {
			result = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

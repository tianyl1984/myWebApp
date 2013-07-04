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
}

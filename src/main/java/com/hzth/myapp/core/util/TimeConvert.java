package com.hzth.myapp.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConvert {

	public static void main(String[] args) throws Exception {
		m1();
		// m2();
	}

	private static void m2() throws Exception {
		String timeStr1 = "14:42:04.302";
		String timeStr2 = "16:10:49.339";
		Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2015-09-21 " + timeStr1);
		Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2015-09-21 " + timeStr2);
		System.out.println((d2.getTime() - d1.getTime()) / 999);
	}

	private static void m1() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(1439969647));
		// System.out.println(date);
		int t1 = 1464920230;
		int t2 = 1449302858;
		System.out.println(sdf.format(new Date(t1 * 1000L)));
		System.out.println(sdf.format(new Date(t2 * 1000L)));
		System.out.println((t2 - t1) + "s:" + (t2 - t1) / 60 + "m");
	}

}

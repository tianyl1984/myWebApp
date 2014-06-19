package com.hzth.myapp.core.util;

import java.util.Date;

public class ThreadUtil {

	public static final void sleep(int t) {
		if (t < 0) {
			return;
		}
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static final void stopTo(String time) {
		String dateStr = DateUtil.getCurrentDate2();
		dateStr = dateStr.substring(0, dateStr.length() - time.length()) + time;
		Date toDate = DateUtil.getDate(dateStr);
		Date curDate = new Date();
		Long t = toDate.getTime() - curDate.getTime();
		sleep(t.intValue());
	}

	public static void main(String[] args) {
		stopTo("09:32");
		System.out.println(DateUtil.getCurrentDate());
	}
}

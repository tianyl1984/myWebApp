package com.hzth.myapp.core.util;

public class ThreadUtil {

	public static final void sleep(int t) {
		try {
			Thread.sleep(t * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

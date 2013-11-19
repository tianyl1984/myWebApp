package com.hzth.myapp.web;

public class ThreadLocalHolder {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	public static String get() {
		return threadLocal.get();
	}

	public static void set(String val) {
		threadLocal.set(val);
	}

}

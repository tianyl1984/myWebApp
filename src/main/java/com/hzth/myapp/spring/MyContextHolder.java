package com.hzth.myapp.spring;

public class MyContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setUserType(String customerType) {
		contextHolder.set(customerType);
	}

	public static String getUserType() {
		return contextHolder.get();
	}

	public static void clearUserType() {
		contextHolder.remove();
	}

}

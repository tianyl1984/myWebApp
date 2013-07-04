package com.hzth.myapp.core.util;

public class StringUtil {
	public static final String getRandomStr() {
		return getRandomStr(5);
	}

	public static String getRandomStr(int count) {
		char A = 'A';
		char z = 'z';
		String result = "";
		for (int i = 0; i < count; i++) {
			Double d = Math.random() * (z - A) + A;
			char aa = (char) d.intValue();
			result += String.valueOf(aa);
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(getRandomStr());
		System.out.println(getRandomStr());
		System.out.println(getRandomStr());
		System.out.println(getRandomStr());
	}
}

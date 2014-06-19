package com.hzth.myapp.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static final String getRandomStr() {
		return getRandomStr(5);
	}

	public static final List<String> getListInStr(String str) {
		List<String> result = new ArrayList<>();
		for (String s : str.split(",")) {
			if (StringUtils.isNotBlank(s)) {
				result.add(s);
			}
		}
		return result;
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

	public static String getRandomChineseStr(int count) {
		String result = "";
		for (int i = 0; i < count; i++) {
			Double d = Math.random() * (0x4e00 + 500 - 0x4e00) + 0x4e00;
			char aa = (char) d.intValue();
			result += String.valueOf(aa);
		}
		return result;
	}

	public static String getRandomChineseStr() {
		return getRandomChineseStr(5);
	}

	public static void main(String[] args) {
		System.out.println(getRandomStr());
		System.out.println(getRandomStr());
		System.out.println(getRandomStr());
		System.out.println(getRandomChineseStr());
	}

	public static String[] strToStrArray(String str) {
		return str.split(",");
	}

	public static void noop(String str) {

	}
}

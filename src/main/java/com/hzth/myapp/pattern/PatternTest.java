package com.hzth.myapp.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

	// 元字符
	// \ 转义
	// ^ 开始
	// $ 结束

	public static void main(String[] args) {
		// m1();
		m2();
	}

	private static void m2() {
		Pattern pattern = Pattern.compile("\\d{3}");
		System.out.println(pattern.matcher("d123").matches());
	}

	private static void m1() {
		Pattern pattern = Pattern.compile("\\d+");
		String aaa = "1ab-2c3d456e78f999y34/d23";
		Matcher matcher = pattern.matcher(aaa);
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
		System.out.println("".matches("\\d+"));
	}
}

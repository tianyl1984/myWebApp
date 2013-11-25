package com.hzth.myapp;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class Test2 {

	public static void main(String[] args) {
		// System.out.println("<a>saf></sa>asdsf<s.>".replaceAll("<.*?>", ""));
		// System.out.println(StringEscapeUtils.unescapeHtml("a&nbsp;b"));
		m1(5725d);
		m1(5715d);
		m1(5714d);
		m1(5716d);
		m1(5715.3d);
		m1(5715.9d);
		m1(5715.0d);
	}

	private static void m1(double dou) {
		double num = dou / 100d;
		int decimal = 1;
		String str = "" + num;
		String formatStr = "0.";
		for (int i = 0; i < decimal; i++) {
			formatStr += "0";
		}
		DecimalFormat df = new DecimalFormat(formatStr);
		df.setRoundingMode(RoundingMode.HALF_UP);
		try {
			str = "" + df.format(num);
		} catch (Exception e) {

		}
		System.out.println(str);

	}

	public static String toDecimalString(Double num, int decimal) {
		if (num == null) {
			return "";
		}
		String str = "" + num;
		String formatStr = "0.";
		for (int i = 0; i < decimal; i++) {
			formatStr += "0";
		}
		DecimalFormat df = new DecimalFormat(formatStr);
		try {
			str = "" + df.format(num);

		} catch (Exception e) {

		}
		return str;
	}

	private static void m1(String numStr) {
		boolean result = true;
		numStr = numStr.replaceAll("．", "").replaceAll("。", "").replaceAll("。", "").replaceAll("\\.", "");
		result = Pattern.matches("^[+]?\\d*$", numStr);
		System.out.println(result);
	}
}

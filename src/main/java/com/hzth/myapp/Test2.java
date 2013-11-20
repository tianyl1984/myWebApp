package com.hzth.myapp;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

public class Test2 {

	public static void main(String[] args) {
		System.out.println("<a>saf></sa>asdsf<s.>".replaceAll("<.*?>", ""));
		System.out.println(StringEscapeUtils.unescapeHtml("a&nbsp;b"));
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

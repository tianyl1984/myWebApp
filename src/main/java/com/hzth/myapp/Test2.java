package com.hzth.myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class Test2 {

	public static void main(String[] args) {
		String filePath = "E:/workspace3.7/dc-exam/src/main/java/com/unitever/dc/ex/module/score/dao/ScoreCountDAO.java";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.trim().startsWith("public") && !line.contains(" throws ")) {
					System.out.println(line.trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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

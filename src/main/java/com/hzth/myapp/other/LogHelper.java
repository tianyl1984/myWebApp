package com.hzth.myapp.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogHelper {

	public static void main(String[] args) {
		File srcFile = new File("C:\\Users\\tianyl\\Desktop\\日志\\catalina.2014-05-06.log");
		File targetFile = new File(srcFile.getParentFile().getAbsolutePath() + "\\result.log");
		findLogByTime("08:55:32", "08:55:36", srcFile, targetFile);
		// System.out.println("00:53:02".compareTo("08:03:21"));
		// System.out.println("00:53:02".compareTo("08:03:23"));
	}

	private static void findLogByTime(String start, String end, File srcFile, File targetFile) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile), "gbk"));
			String temp = null;
			boolean find = false;
			while ((temp = br.readLine()) != null) {
				if (temp.startsWith("2014-")) {
					String[] strs = temp.split(" ");
					if (strs.length > 1) {
						String timeStr = strs[1].trim();
						if (timeStr.contains(":")) {
							if (timeStr.length() < start.length()) {
								timeStr = "0" + timeStr;
							}
							if (timeStr.length() == start.length()) {
								int x = timeStr.compareTo(start);
								int y = timeStr.compareTo(end);
								if (x > 0 && y < 0) {
									System.out.println(timeStr + ":" + temp);
									find = true;
								} else {
									if (find) {
										break;
									}
								}
							} else {
								System.out.println("Error:" + temp);
							}
						} else {
							System.out.println("Error:" + temp);
						}
					} else {
						System.out.println("Error:" + temp);
					}
				} else {
					if (find) {
						System.out.println(temp);
					}
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
}

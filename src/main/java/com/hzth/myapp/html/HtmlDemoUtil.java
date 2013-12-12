package com.hzth.myapp.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HtmlDemoUtil {

	public static final File getHtmlFile() {
		return new File("C:/Users/tianyl/git/mywebapp/src/main/java/com/hzth/myapp/html/demo.html");
	}

	public static final File getHtmlFile2() {
		return new File("C:/Users/tianyl/git/mywebapp/src/main/java/com/hzth/myapp/html/demo2.html");
	}

	public static final String getHtmlStr() {
		StringBuffer result = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(getHtmlFile())));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				result.append(temp);
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
		return result.toString();
	}
}

package com.hzth.myapp.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebUtil {

	public static void downloadFile(String url, String saveFile) throws Exception {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(saveFile));
			InputStream is = new URL(url).openConnection().getInputStream();
			bis = new BufferedInputStream(is);
			byte[] b = new byte[512];
			int length = 0;
			while ((length = bis.read(b)) != -1) {
				fos.write(b, 0, length);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("文件下载错误！");
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void printHeader(HttpServletRequest req) {
		System.out.println("----------> Header start <----------");
		Enumeration e = req.getHeaderNames();
		while (e.hasMoreElements()) {
			String header = e.nextElement().toString();
			String val = "";
			Enumeration e2 = req.getHeaders(header);
			while (e2.hasMoreElements()) {
				val += "[" + e2.nextElement().toString() + "]";
			}
			System.out.println(header + ":" + val);
		}
		System.out.println("----------> Header end <----------");
	}

	@SuppressWarnings("rawtypes")
	public static void printParameter(HttpServletRequest req) {
		System.out.println("----------> Parameter start <----------");
		try {
			Enumeration e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String name = e.nextElement().toString();
				String value = "";
				String[] strs = req.getParameterValues(name);
				for (String str : strs) {
					value += "[" + str + "]";
				}
				System.out.println(name + ":" + value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("----------> Parameter end <----------");
	}

	public static void printSession(HttpSession session) {
		System.out.println("------------> Session start  <--------------");
		Enumeration e3 = session.getAttributeNames();
		while (e3.hasMoreElements()) {
			String name = e3.nextElement().toString();
			Object value = session.getAttribute(name);
			System.out.println(name + ":" + value);
		}
		System.out.println("------------>  Session end  <--------------");
	}

	public static void printStream(HttpServletRequest req) throws IOException {
		System.out.println("------------>  InputStream start  <--------------");
		InputStream in = req.getInputStream();
		byte[] buffer = new byte[1024];
		int hasRead = -1;
		while ((hasRead = in.read(buffer)) != -1) {
			System.out.println(new String(Arrays.copyOf(buffer, hasRead)));
		}
		System.out.println("------------>  InputStream end  <--------------");
	}

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("##.#");
		df.setRoundingMode(RoundingMode.HALF_UP);
		System.out.println(df.format(0.13) + ":" + df.getRoundingMode());
		System.out.println(df.format(0.25) + ":" + df.getRoundingMode());
		System.out.println(df.format(0.35) + ":" + df.getRoundingMode());
		System.out.println(df.format(0.45) + ":" + df.getRoundingMode());
		System.out.println(df.format(0.55) + ":" + df.getRoundingMode());
		System.out.println(df.format(0.65) + ":" + df.getRoundingMode());
	}
}

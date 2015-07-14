package com.hzth.myapp.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLConnectionTest {

	public static void main(String[] args) throws Exception {
		String url = "http://192.168.90.49:1377/diagnosis/passport/checkToken?token=123";
		System.out.println(getResponse(url));
	}

	private static String getResponse(String url) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 连接
		conn.connect();

		InputStream is = null;
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			System.out.println("异常响应编码：" + conn.getResponseCode());
			is = conn.getErrorStream();
		} else {
			is = conn.getInputStream();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}
		reader.close();
		conn.disconnect();
		return result;
	}

}

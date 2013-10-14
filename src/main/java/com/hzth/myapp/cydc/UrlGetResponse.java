package com.hzth.myapp.cydc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlGetResponse {

	private static ThreadLocal<Map<String, String>> cookieThreadLocal = new ThreadLocal<Map<String, String>>();

	public static void main(String[] args) throws Exception {
		// commonTest();
		ssoTest();
	}

	private static void ssoTest() throws Exception {
		clearCookies();
		String baseUrl = "http://192.168.1.122:8090/cydc";
		login(baseUrl, "baidonghua", "000000");
		mytest("http://192.168.1.122:8090/cydc/so/honor!show.action?model.id=20110511115841346122044228606376");
	}

	private static void commonTest() throws Exception {
		clearCookies();
		String baseUrl = "http://192.168.1.11/cydc";
		login(baseUrl, "baidonghua", "000000");
		mytest("http://192.168.1.11/cydc/so/task!toContentDetail.action?id=20110309114444554162152076425725");
	}

	private static void login(String baseUrl, String userName, String password) throws Exception {
		String loginUrl = baseUrl + "/j_spring_security_check";
		HttpURLConnection conn = (HttpURLConnection) (new URL(loginUrl).openConnection());
		conn.setRequestProperty("Cookie", getCookies());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
		// 连接
		conn.connect();

		String content = "j_username=" + URLEncoder.encode(userName.toString().trim(), "utf-8");
		content += "&j_password=" + URLEncoder.encode(password.toString().trim(), "utf-8");
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(content);
		out.flush();
		out.close();
		storeCookie(conn);

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_MOVED_TEMP) {
			System.out.println("异常响应编码：" + conn.getResponseCode());
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}
		reader.close();
		// System.out.println(result);
		conn.disconnect();
	}

	private static void mytest(String url) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(true);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Cookie", getCookies());
		// 连接
		conn.connect();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			System.out.println("异常响应编码：" + conn.getResponseCode());
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}
		reader.close();
		conn.disconnect();
		System.out.println(result);
	}

	private static void storeCookie(HttpURLConnection conn) {
		Map<String, List<String>> headerMap = conn.getHeaderFields();
		String cookies = "";
		for (String key : headerMap.keySet()) {
			if (key != null && key.toLowerCase().equals("set-cookie")) {
				cookies = conn.getHeaderField(key);
			}
		}
		Map<String, String> oldCookie = cookieThreadLocal.get();
		if (oldCookie == null) {
			oldCookie = new HashMap<String, String>();
		}
		for (String str : cookies.split(";")) {
			if (str.contains("=")) {
				String key = str.split("=")[0];
				if (key.trim().toLowerCase().equals("path")) {// cookie中的path不能提交
					continue;
				}
				oldCookie.put(key.trim(), str.split("=")[1].trim());
			}
		}
		cookieThreadLocal.set(oldCookie);
	}

	private static void clearCookies() {
		cookieThreadLocal.set(null);
	}

	private static String getCookies() {
		Map<String, String> map = cookieThreadLocal.get();
		String result = "";
		if (map != null) {
			for (String key : map.keySet()) {
				result += key + "=" + map.get(key) + " ";
			}
		}
		return result;
	}
}

package com.hzth.myapp.core.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionUtil {

	public static String getResponse(String url, Map<String, String> param, Map<String, String> cookieMap, boolean isPost) throws IOException {
		if (param == null) {
			param = new HashMap<String, String>();
		}
		if (cookieMap == null) {
			cookieMap = new HashMap<String, String>();
		}
		String result = "";
		String content = "";
		for (String key : param.keySet()) {
			content += key + "=" + URLEncoder.encode(param.get(key), "utf-8") + "&";
		}
		if (content.length() > 0) {
			content = content.substring(0, content.length() - 1);
		}
		if (!isPost) {
			if (url.contains("?")) {
				url += "&" + content;
			} else {
				url += "?" + content;
			}
		}
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		// 必须大写
		conn.setRequestMethod(isPost ? "POST" : "GET");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(true);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		if (!cookieMap.isEmpty()) {
			String cookieStr = "";
			for (String key : cookieMap.keySet()) {
				cookieStr += key + "=" + cookieMap.get(key) + ";";
			}
			conn.setRequestProperty("Cookie", cookieStr);
		}
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.connect();

		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes());
		out.flush();
		out.close();

		// System.out.println("-----Response Content-----");
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}
		reader.close();
		// System.out.println(result);
		// System.out.println("-----Response Content-----");

		conn.disconnect();
		return result;
	}
}

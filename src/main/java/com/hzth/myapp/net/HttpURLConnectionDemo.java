package com.hzth.myapp.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpURLConnectionDemo {

	public static void main(String[] args) throws Exception {
		String url = "http://mp.weixin.qq.com/mp/appmsg/show?__biz=MjM5Mzg3MzY2MA==&appmsgid=100062024&itemidx=1&sign=b1c434d57a37e23164f1c9530615d746&uin=MTUwNjE5";
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		// POST必须大写
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.connect();

		Map<String, List<String>> headerMap = conn.getHeaderFields();
		System.out.println("-----Header-----");
		for (String key : headerMap.keySet()) {
			// String val = conn.getHeaderField(key);
			String val = "";
			for (String v : headerMap.get(key)) {
				val += "[" + v + "]";
			}
			System.out.println(key + ":" + val);
		}
		System.out.println("-----Header-----");

		System.out.println("-----Response Content-----");
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}
		reader.close();
		System.out.println(result);
		System.out.println("-----Response Content-----");

		conn.disconnect();
	}
}

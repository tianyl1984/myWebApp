package com.hzth.myapp.dc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class DcUtil2 {

	private String url;

	private String schoolCode;

	private Map<String, String> cookiesMap = new HashMap<String, String>();

	/**
	 * 
	 * @param url
	 *            应用访问地址比如：http://127.0.0.1:8094/dc
	 * @param schoolCode
	 *            学校编码
	 */
	public DcUtil2(String url, String schoolCode) {
		this.url = checkUrl(url);
		this.schoolCode = schoolCode;
		login();
	}

	public void login() {
		clearCookies();
		String baseUrl = checkUrl(url);
		try {
			setDataSource(baseUrl, schoolCode);
			getUrlResponse(baseUrl + "/bd/welcome!login.action");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("网络异常！");
		}
	}

	private void setDataSource(String baseUrl, String dataSourceName) throws Exception {
		String loginUrl = baseUrl + "/bd/welcome!ajaxSetUserType.action?dataSourceName=" + dataSourceName;
		HttpURLConnection conn = (HttpURLConnection) (new URL(loginUrl).openConnection());
		conn.setRequestProperty("Cookie", getCookies());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(true);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 连接
		conn.connect();
		storeCookie(conn);
		conn.disconnect();
	}

	private String getCookies() {
		String result = "";
		for (String key : cookiesMap.keySet()) {
			String val = cookiesMap.get(key);
			if (StringUtils.isNotBlank(val)) {
				result += key + "=" + cookiesMap.get(key) + " ";
			}
		}
		return result;
	}

	private void storeCookie(HttpURLConnection conn) {
		Map<String, List<String>> headerMap = conn.getHeaderFields();
		String cookies = "";
		for (String key : headerMap.keySet()) {
			if (key != null && key.toLowerCase().equals("set-cookie")) {
				cookies = conn.getHeaderField(key);
			}
		}
		for (String str : cookies.split(";")) {
			if (str.contains("=")) {
				String key = str.split("=")[0];
				if (key.trim().toLowerCase().equals("path")) {// cookie中的path不能提交
					continue;
				}
				cookiesMap.put(key.trim(), str.split("=")[1].trim());
			}
		}
		// System.out.println("保存cookie：" + getCookies());
	}

	private void clearCookies() {
		cookiesMap.clear();
	}

	public String getUrlResponse(String url) {
		return getUrlResponse(url, null, getCookies());
	}

	public String getUrlResponse(String url, Map<String, String> map) {
		return getUrlResponse(url, map, getCookies());
	}

	private static String getUrlResponse(String url, Map<String, String> map, String cookies) {
		String result = "";
		try {
			HttpURLConnection conn = (HttpURLConnection) (new URL(checkUrl(url)).openConnection());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if (StringUtils.isNotBlank(cookies)) {// 设置cookie
				conn.setRequestProperty("Cookie", cookies);
			}
			// POST必须大写
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			// 仅对当前请求自动重定向
			conn.setInstanceFollowRedirects(true);
			// header 设置编码
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 连接
			conn.connect();
			writeParameters(conn, map);
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				// printHeader(conn);
				throw new IOException("response code:" + conn.getResponseCode());
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				result += temp + "\n";
			}
			reader.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("网络异常！");
		}
		return result.trim();
	}

	private static void writeParameters(HttpURLConnection conn, Map<String, String> map) throws IOException {
		if (map == null) {
			return;
		}
		String content = "";
		Set<String> keySet = map.keySet();
		int i = 0;
		for (String key : keySet) {
			String val = map.get(key);
			content += (i == 0 ? "" : "&") + key + "=" + URLEncoder.encode(val, "utf-8");
			i++;
		}
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(content);
		out.flush();
		out.close();
	}

	private static String checkUrl(String url) {
		String result = url;
		if (url.startsWith("http://")) {
			url = url.replaceFirst("http://", "");
			if (url.contains("//")) {
				url = url.replaceAll("//", "/");
			}
			result = "http://" + url;
		}
		return result;
	}

	public static String getSchoolInfoStr(String baseUrl) {
		return getUrlResponse(checkUrl(baseUrl) + "/bd/welcome!ajaxGetSchoolNames.action", null, "").trim();
	}

	public static void main(String[] args) {
		String baseUrl = "http://192.168.30.246:8085/dc-base";
		DcUtil2 util = new DcUtil2(baseUrl, "dataSource1");
		System.out.println(util.getUrlResponse(baseUrl + "/bd/decisionAnalysis!syncStudent.action"));
		// DcUtil util2 = new DcUtil(baseUrl, "dataSource2");
		// System.out.println(util2.getUrlResponse(baseUrl + "/bd/publisher!ajaxLoadData.action"));
	}
}
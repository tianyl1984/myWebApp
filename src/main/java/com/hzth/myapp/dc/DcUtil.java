package com.hzth.myapp.dc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DcUtil {

	private static final Map<String, String> cookiesMap = new HashMap<String, String>();

	private static boolean isSSO = false;
	private static String ssoSessionId = "";

	public static boolean login(String url, String userName, String password, String dataSourceName) {
		boolean result = false;
		String baseUrl = checkUrl(url);
		try {
			setDataSource(baseUrl, dataSourceName);
			result = ajaxCheckUser(baseUrl, userName, password);
			if (result) {
				String ssoLoginUrl = checkSSO(url);
				if (isSSO) {// 单点登录
					if (!StringUtils.isBlank(ssoLoginUrl)) {
						String castgc = ssoLogin(ssoLoginUrl, userName, password, dataSourceName).trim();
						String location = ssoGetLoginLocation(ssoLoginUrl, castgc);
						getUrlResponse(location);
						getUrlResponse(baseUrl + "/bd/welcome!login.action");
					} else {
						throw new Exception("系统错误");
					}
				} else {
					securityCheck(baseUrl, userName, password);
					// getUrlResponse(baseUrl + "/bd/welcome!login.action");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private static String ssoGetLoginLocation(String ssoLoginUrl, String castgc) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) (new URL(ssoLoginUrl).openConnection());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Cookie", "CASTGC=" + castgc + ";JSESSIONID=" + ssoSessionId);
		// 连接
		conn.connect();

		String result = "";
		if (conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
			Map<String, List<String>> headerMap = conn.getHeaderFields();
			for (String key : headerMap.keySet()) {
				if (key != null && key.toLowerCase().equals("location")) {
					result = conn.getHeaderField(key);
				}
			}
		}
		conn.disconnect();
		return result;
	}

	private static String ssoLogin(String ssoLoginUrl, String userName, String password, String dataSourceName) throws Exception {
		String[] strs = getSsoLoginForm(ssoLoginUrl);
		HttpURLConnection conn = (HttpURLConnection) (new URL(ssoLoginUrl).openConnection());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Cookie", "JSESSIONID=" + strs[0]);
		// 连接
		conn.connect();

		String content = "username=" + URLEncoder.encode(userName.toString().trim(), "utf-8");
		content += "&password=" + URLEncoder.encode(password.toString().trim(), "utf-8");
		content += "&schoolCode=" + URLEncoder.encode(dataSourceName.toString().trim(), "utf-8");
		content += "&lt=" + URLEncoder.encode(strs[1], "utf-8");
		content += "&execution=" + URLEncoder.encode(strs[2], "utf-8");
		content += "&_eventId=" + URLEncoder.encode("submit", "utf-8");
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(content);
		out.flush();
		out.close();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_MOVED_TEMP) {
			if (conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
				System.out.println(conn.getHeaderField("location"));
			}
			throw new IOException("response code:" + conn.getResponseCode());
		}
		printContent(conn);
		Map<String, List<String>> headerMap = conn.getHeaderFields();
		String cookies = "";
		for (String key : headerMap.keySet()) {
			if (key != null && key.toLowerCase().equals("set-cookie")) {
				cookies = conn.getHeaderField(key);
			}
		}
		String result = "";
		for (String str : cookies.split(";")) {
			if (str.contains("=")) {
				String key = str.split("=")[0];
				if (key.trim().toLowerCase().equals("castgc")) {
					result = str.split("=")[1].trim();
				}
			}
		}
		conn.disconnect();
		return result;
	}

	private static String[] getSsoLoginForm(String ssoLoginUrl) throws Exception {
		String[] strs = new String[3];
		HttpURLConnection conn = (HttpURLConnection) (new URL(ssoLoginUrl).openConnection());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 连接
		conn.connect();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			if (conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
				System.out.println(conn.getHeaderField("location"));
			}
			throw new IOException("response code:" + conn.getResponseCode());
		}
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
				if (key.trim().toLowerCase().equals("jsessionid")) {
					strs[0] = str.split("=")[1].trim();
				}
			}
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String resultTemp = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			resultTemp += temp + "\n";
		}
		reader.close();
		Document doc = Jsoup.parse(resultTemp);
		strs[1] = doc.getElementsByAttributeValue("name", "lt").first().val();
		strs[2] = doc.getElementsByAttributeValue("name", "execution").first().val();
		conn.disconnect();
		return strs;
	}

	private static void printContent(HttpURLConnection conn) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String resultTemp = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			resultTemp += temp + "\n";
		}
		reader.close();
		System.out.println(resultTemp);
	}

	/**
	 * 未登录才能调用，测试是否是sso
	 * 
	 * @param url
	 * @return
	 */
	private static String checkSSO(String baseUrl) throws Exception {
		String loginUrl = baseUrl + "/bd/welcome!welcome.action";
		HttpURLConnection conn = (HttpURLConnection) (new URL(loginUrl).openConnection());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Cookie", getCookies());
		// 连接
		conn.connect();

		String result = "";
		if (conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
			Map<String, List<String>> headerMap = conn.getHeaderFields();
			for (String key : headerMap.keySet()) {
				if (key != null && key.toLowerCase().equals("location")) {
					isSSO = true;
					result = conn.getHeaderField(key);
				}
			}
		}
		conn.disconnect();
		return result;
	}

	public static void logout(String url) {
		try {
			getUrlResponse(url + "/spring_logout");
		} catch (IOException e) {
			e.printStackTrace();
		}
		clearCookies();
	}

	private static boolean ajaxCheckUser(String baseUrl, String userName, String password) throws Exception {
		String loginUrl = baseUrl + "/bd/welcome!ajaxValidationUser.action";
		HttpURLConnection conn = (HttpURLConnection) (new URL(loginUrl).openConnection());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Cookie", getCookies());
		// 连接
		conn.connect();

		String content = "loginName=" + URLEncoder.encode(userName.toString().trim(), "utf-8");
		content += "&password=" + URLEncoder.encode(password.toString().trim(), "utf-8");
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(content);
		out.flush();
		out.close();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException("response code:" + conn.getResponseCode());
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String resultTemp = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			resultTemp += temp + "\n";
		}
		reader.close();
		printHeader(conn);
		storeCookie(conn);
		conn.disconnect();
		return resultTemp.contains("true");
	}

	private static void securityCheck(String baseUrl, String userName, String password) throws Exception {
		String loginUrl = baseUrl + "/j_spring_security_check";
		HttpURLConnection conn = (HttpURLConnection) (new URL(loginUrl).openConnection());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// conn.setRequestProperty("Cookie", getCookies());
		conn.setRequestProperty("Cookie", "JSESSIONID=65903E8A9C89F88275F0BA6594E87B47");
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
			throw new IOException("response code:" + conn.getResponseCode());
		}
		printContent(conn);
		printHeader(conn);
		conn.disconnect();
	}

	private static void setDataSource(String baseUrl, String dataSourceName) throws Exception {
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
		printHeader(conn);
		conn.disconnect();
	}

	public static String getCookies() {
		String result = "";
		for (String key : cookiesMap.keySet()) {
			String val = cookiesMap.get(key);
			if (StringUtils.isNotBlank(val)) {
				result += key + "=" + cookiesMap.get(key) + " ";
			}
		}
		return result;
	}

	private static void storeCookie(HttpURLConnection conn) {
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

	private static void clearCookies() {
		cookiesMap.clear();
		isSSO = false;
		ssoSessionId = "";
	}

	public static String getUrlResponse(String url) throws IOException {
		return getUrlResponse(url, null);
	}

	public static String getUrlResponse(String url, Map<String, ParameterValue> map) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) (new URL(checkUrl(url)).openConnection());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		String cookies = getCookies();
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}
		reader.close();
		conn.disconnect();
		return result.trim();
	}

	private static void printHeader(HttpURLConnection conn) {
		Map<String, List<String>> headerMap = conn.getHeaderFields();
		for (String key : headerMap.keySet()) {
			String str = conn.getHeaderField(key);
			System.out.println(key + ":" + str);
		}
	}

	private static void writeParameters(HttpURLConnection conn, Map<String, ParameterValue> map) throws IOException {
		if (map == null) {
			return;
		}
		String content = "";
		Set<String> keySet = map.keySet();
		int i = 0;
		for (String key : keySet) {
			for (String val : map.get(key).getValues()) {
				content += (i == 0 ? "" : "&") + key + "=" + URLEncoder.encode(val, "utf-8");
				i++;
			}
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

	public static String getSchoolInfoStr(String baseUrl) throws IOException {
		return getUrlResponse(checkUrl(baseUrl) + "/bd/welcome!ajaxGetSchoolNames.action").trim();
	}

	public static String uploadFileToServer(String baseUrl, File file) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) (new URL(checkUrl(baseUrl) + "/component/attachment!saveUploadFile.action").openConnection());
		conn.setReadTimeout(1000000);
		conn.setConnectTimeout(1000000);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setInstanceFollowRedirects(true);
		String cookies = getCookies();
		if (StringUtils.isNotBlank(cookies)) {// 设置cookie
			conn.setRequestProperty("Cookie", cookies);
		}
		conn.setRequestProperty("Charset", "utf-8");
		conn.setRequestProperty("connection", "keep-alive");
		String boundary = UUID.randomUUID().toString();
		conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		// 连接
		conn.connect();
		FileInputStream in = null;
		DataOutputStream out = null;
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("--" + boundary + "\r\n");
		strBuffer.append("Content-Disposition: form-data; name=\"Filename\"\r\n\r\n");
		strBuffer.append(file.getName() + "\r\n");
		strBuffer.append("--" + boundary + "\r\n");
		strBuffer.append("Content-Disposition: form-data; name=filedata; filename=" + file.getName() + "\r\n");
		strBuffer.append("Content-Type: application/octet-stream\r\n");
		strBuffer.append("\r\n");
		try {
			in = new FileInputStream(file);
			out = new DataOutputStream(conn.getOutputStream());
			out.write(strBuffer.toString().getBytes());
			byte[] buffer = new byte[1024];
			int hasRead = -1;
			while ((hasRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, hasRead);
			}
			out.write("\r\n".getBytes());
			out.write(("--" + boundary + "--\r\n").getBytes());
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				// out.close();
			}
		}
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp;
		}
		reader.close();
		conn.disconnect();
		return result;
	}

	public static void main(String[] args) throws IOException {
		// boolean aa = login("http://192.168.1.20:8080/dc/", "administrator", "000000", "datasource1");
		// System.out.println(aa);
		// // System.out.println(getUrlResponse("http://192.168.1.20:8080/dc/bd/mobile/calendarWeekData!getSchoolAllJson.action"));
		// System.out.println(getUrlResponse("http://192.168.1.20:8080/dc/bd/teachingMaterial!list.action"));
		boolean aa = login("http://192.168.30.38:8093/dc-exam", "administrator", "000000", "dataSource1");
		System.out.println(aa);
		// System.out.println(getCookies());
		// System.out.println(getUrlResponse("http://192.168.1.20:8080/dc/bd/mobile/calendarWeekData!getSchoolAllJson.action"));
		System.out.println(getUrlResponse("http://192.168.30.38:8093/dc-exam//bd//teachingMaterial!list.action"));
	}

	static {
		// cookiesMap.put("JSESSIONID", "9588864F3D73681556FA19C923DB5649");
	}
}
package com.hzth.myapp.web;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;

import com.hzth.myapp.dc.ParameterValue;

public class NetUtil {

	public NetUtil() {
	}

	/**
	 * 获取mac地址
	 * 
	 * @return
	 */
	public static String getMACAddress() {
		String address = "";
		String os = System.getProperty("os.name");
		if ((os != null) && (os.startsWith("Windows")))
			try {
				String command = "cmd.exe /c ipconfig /all";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

				boolean flag = false;
				String line = "";
				while ((line = br.readLine()) != null) {
					if ((line.indexOf("Ethernet adapter") >= 0) || (line.indexOf("以太网适配器") >= 0))
						flag = true;
					if (((line.indexOf("Physical Address") < 0) && (line.indexOf("物理地址") < 0)) || (!flag))
						continue;
					int index = line.indexOf(":");
					index += 2;
					address = line.substring(index);
					flag = false;
				}

				br.close();
				return address.trim();
			} catch (IOException localIOException) {
			}
		return address;
	}

	/**
	 * 获取硬盘序列号
	 * 
	 * @return
	 */
	public static String getHdSerialInfo() {

		String line = "";
		String HdSerial = "";// 记录硬盘序列号

		try {

			Process proces = Runtime.getRuntime().exec("cmd /c dir c:");// 获取命令行参数
			BufferedReader buffreader = new BufferedReader(new InputStreamReader(proces.getInputStream()));

			while ((line = buffreader.readLine()) != null) {

				if (line.indexOf("卷的序列号是 ") != -1) { // 读取参数并获取硬盘序列号
					HdSerial = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length(), line.length());
					break;
					// System.out.println(HdSerial);
				} else if (line.indexOf("Volume Serial Number is ") != -1) { // 读取参数并获取硬盘序列号
					HdSerial = line.substring(line.indexOf("Volume Serial Number is ") + "Volume Serial Number is ".length(), line.length());
					break;
					// System.out.println(HdSerial);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return HdSerial;
	}

	/**
	 * 获取本机IP 多个ip以","分隔
	 * 
	 * @return
	 */
	public static String getIp() {
		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			String resultIp = "";
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = ips.nextElement();
					resultIp += ip.getHostAddress() + ",";
				}

			}
			return resultIp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		result = result.replaceAll("：", ":").replaceAll(" ", "");
		return result;
	}

	public static void downloadFileSimple(String url, File file) throws IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(checkUrl(url)).openConnection();
		con.setUseCaches(false);
		// if (StringUtils.isNotBlank(cookies)) {// 设置cookie
		// con.setRequestProperty("Cookie", cookies);
		// }
		if (con.getResponseCode() != HttpURLConnection.HTTP_OK && con.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
			throw new IOException();
		}
		InputStream is = con.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file);
		byte[] b = new byte[1024];
		int length = 0;
		while ((length = bis.read(b)) != -1) {
			fos.write(b, 0, length);
		}
		fos.close();
		bis.close();
	}

	public static String getUrlResponse(String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) (new URL(checkUrl(url)).openConnection());
		// if (StringUtils.isNotBlank(cookies)) {// 设置cookie
		// conn.setRequestProperty("Cookie", cookies);
		// }

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
			System.out.println(conn.getResponseCode());
			Map<String, List<String>> map = conn.getHeaderFields();
			for (String key : map.keySet()) {
				List<String> strList = map.get(key);
				String msg = key;
				if (strList != null) {
					msg += ":";
					for (String str : strList) {
						msg += str;
					}
				}
				System.out.println(msg);
			}
			// throw new IOException();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}

		reader.close();
		conn.disconnect();
		return result;
	}

	public static String getUrlResponse(String url, String cookie) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) (new URL(checkUrl(url)).openConnection());
		if (StringUtils.isNotBlank(cookie)) {// 设置cookie
			conn.setRequestProperty("Cookie", cookie);
		}

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
			System.out.println(conn.getResponseCode());
			Map<String, List<String>> map = conn.getHeaderFields();
			for (String key : map.keySet()) {
				List<String> strList = map.get(key);
				String msg = key;
				if (strList != null) {
					msg += ":";
					for (String str : strList) {
						msg += str;
					}
				}
				System.out.println(msg);
			}
			// throw new IOException();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}

		reader.close();
		conn.disconnect();
		return result;
	}

	public static String getRedirectUrl(String url, String cookies, Map<String, ParameterValue> map, boolean isGet) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) (new URL(checkUrl(url)).openConnection());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (StringUtils.isNotBlank(cookies)) {// 设置cookie
			conn.setRequestProperty("Cookie", cookies);
		}
		// POST必须大写
		conn.setRequestMethod(isGet ? "GET" : "POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 连接
		conn.connect();
		writeParameters(conn, map);
		if (conn.getResponseCode() != HttpURLConnection.HTTP_MOVED_TEMP) {
			// printHeader(conn);
			throw new IOException("response code:" + conn.getResponseCode());
		}
		String result = conn.getHeaderField("Location");
		conn.disconnect();
		return result.trim();
	}

	public static String getUrlResponse(String url, String cookies, Map<String, ParameterValue> map, boolean isGet) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) (new URL(checkUrl(url)).openConnection());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (StringUtils.isNotBlank(cookies)) {// 设置cookie
			conn.setRequestProperty("Cookie", cookies);
		}
		// POST必须大写
		conn.setRequestMethod(isGet ? "GET" : "POST");
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

	public static String getUrlResponse(String url, String param, boolean isGet) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) (new URL(checkUrl(url)).openConnection());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// POST必须大写
		conn.setRequestMethod(isGet ? "GET" : "POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(true);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 连接
		conn.connect();
		writeParameters(conn, param);
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

	private static void writeParameters(HttpURLConnection conn, String param) throws IOException {
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(param);
		out.flush();
		out.close();
	}

	public static final void printParameters(Map<String, ParameterValue> parMap) {
		for (String key : parMap.keySet()) {
			System.out.println(key + ":" + parMap.get(key).getValueStr());
		}
	}

	public static void main(String[] args) throws Exception {
		// String url = "https://git.oschina.net/login";
		String url = "https：//mail. lanxum.com/";
		String result = getHttpsResponse(url);
		System.out.println(result);
		// System.out.println(checkUrl(url));
	}

	public static String getHttpsResponse(String url) throws Exception {
		return getHttpsResponse(url, "");
	}

	public static String getHttpsResponse(String url, String authorization) throws Exception {
		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				// System.out.println("arg0:" + arg0);
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		trustAllHttpsCertificates();
		HttpsURLConnection conn = (HttpsURLConnection) (new URL(checkUrl(url)).openConnection());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// POST必须大写
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(true);
		// header 设置编码
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		if (StringUtils.isNotBlank(authorization)) {
			conn.setRequestProperty("Authorization", authorization);
		}
		// 连接
		conn.connect();
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

	public static void trustAllHttpsCertificates() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[1];
		TrustManager tm = new CustomTrustManager();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	static class CustomTrustManager implements TrustManager, X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}
}

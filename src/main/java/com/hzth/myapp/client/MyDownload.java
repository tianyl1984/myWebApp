package com.hzth.myapp.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyDownload {

	public static void main(String[] args) throws Exception {
		m1();
		System.out.println("完成");
	}

	private static void m1() throws Exception {
		String url = "http://192.168.1.122:9000/myWebApp/download";
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		long start = 1048575L;
		long end = 1048579L;
		con.setRequestProperty("RANGE", "bytes=" + start + "-" + end);
		if (con.getResponseCode() != HttpURLConnection.HTTP_OK && con.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
			throw new Exception("文件下载错误！");
		}
		InputStream is = con.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		RandomAccessFile raf = new RandomAccessFile(new File("e:/fff"), "rw");
		byte[] b = new byte[1024];
		int length = 0;
		while ((length = bis.read(b)) != -1) {
			raf.write(b, 0, length);
		}
		raf.close();
	}
}

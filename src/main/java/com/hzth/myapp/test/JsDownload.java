package com.hzth.myapp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsDownload {

	public static void main(String[] args) throws Exception {
		// downloadAll();
		downloadOther();
	}

	private static void downloadOther() throws Exception {
		// download("http://asset.gleasy.com/platform/os/assets/javascripts/core/reg.js?r=12");
		// 记事本
		// download("http://asset.gleasy.com/platform/os/assets/javascripts/softwares/gnotepad/js/GNotepad.class.js?v=2012110503");
		// download("http://asset.gleasy.com/platform/os/assets/javascripts/core/api/gleasy.api.js?v=2012092701");
		// download("http://asset.gleasy.com/platform/os/assets/javascripts/softwares/gnotepad/notepad/js/notepad.js?v=2012092701");
		// download("http://asset.gleasy.com/platform/os/assets/javascripts/softwares/gnotepad/js/jquery.printarea.js");
		// download("http://asset.gleasy.com/platform/os/assets/javascripts/softwares/gnotepad/js/undo.js");
		// download("http://asset.gleasy.com/platform/os/assets/javascripts/softwares/gnotepad/js/NotepadViewer.js??v=2012102602");
	}

	private static void downloadAll() throws Exception {
		String html = "";
		BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/tianyl/Desktop/aaa.txt")));
		String temp = "";
		while ((temp = br.readLine()) != null) {
			html += temp;
		}
		br.close();
		Document doc = Jsoup.parse(html);
		Elements eles = doc.getElementsByTag("script");
		for (int i = 0; i < eles.size(); i++) {
			Element ele = eles.get(i);
			String str = ele.attr("src");
			if (StringUtils.isNotBlank(str)) {
				download(str);
			}
		}
	}

	private static void download(String url) throws Exception {
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		if (fileName.contains("?")) {
			fileName = fileName.substring(0, fileName.indexOf("?"));
		}
		String path = url.replace("http://asset.gleasy.com", "");
		path = path.substring(0, path.lastIndexOf("/") + 1);
		saveTofile(url, "E:/workspace/gleasyjs" + path + fileName);
		// System.out.println("e:/gleasyjs" + path + fileName);
	}

	private static void saveTofile(String url, String fileName) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
		conn.setDoOutput(false);
		conn.setDoInput(true);
		// POST必须大写
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(false);
		// 连接
		conn.connect();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			System.out.println("异常响应编码：" + conn.getResponseCode());
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		File file = new File(fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileWriter fw = new FileWriter(file);
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			fw.append(temp + "\n");
		}
		fw.close();
		reader.close();
		conn.disconnect();
		System.out.println("完成下载：" + url);
	}

}

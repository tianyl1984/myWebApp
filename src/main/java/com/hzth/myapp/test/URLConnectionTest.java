package com.hzth.myapp.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

import com.hzth.myapp.core.util.FileUtil;

public class URLConnectionTest {

	public static void main(String[] args) throws Exception {
		start();
	}

	private static void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100000; i++) {
					try {
						System.out.println("--------------查询-----------");
						String result = getResponse("http://yun.developer.baidu.com/invitation");
						// System.out.println(result);
						Parser parser = Parser.createParser(result, "utf-8");
						NodeIterator ni = parser.elements();
						ni.nextNode();
						while (ni.hasMoreNodes()) {
							Node node = ni.nextNode();
							printNodeInfo(node);
						}
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private static void printNodeInfo(Node node) {
		if (node != null && node.getChildren() != null) {
			NodeList nodeList = node.getChildren();
			for (int i = 0; i < nodeList.size(); i++) {
				Node childNode = nodeList.elementAt(i);
				if (childNode.toHtml().trim().startsWith("<ul class=\"codelist\">")) {
					printTargetNode(childNode);
					break;
				} else {
					printNodeInfo(childNode);
				}
			}
		}
	}

	private static void printTargetNode(Node node) {
		for (int i = 0; i < node.getChildren().size(); i++) {
			Node childNode = node.getChildren().elementAt(i);
			if (childNode.toHtml().trim().length() == 0) {
				continue;
			}
			if (!childNode.toHtml().trim().contains("已被使用")) {
				System.out.println(childNode.toHtml());
				FileUtil.append("E:/result.txt", childNode.toHtml().trim() + "\n");
			}
		}
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
		return result;
	}

}

package com.hzth.myapp.ws;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import websocket4j.server.WebServerSocket;
import websocket4j.server.WebSocket;

public class WebSocketServerDemo {

	final static List<WebSocket> webSockets = new ArrayList<WebSocket>();

	public static void main(String[] args) throws Exception {
		final WebServerSocket ws = new WebServerSocket(9999);
		System.out.println("开启服务：9999");
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						final WebSocket client = ws.accept();
						System.out.println("接收到客户连接!!");
						webSockets.add(client);
						new Thread(new Runnable() {
							@Override
							public void run() {
								while (true) {
									try {
										String msg = client.getMessage();
										System.out.println("收到：" + msg);
										client.sendMessage("服务器消息：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
									} catch (IOException e) {
										e.printStackTrace();
										webSockets.remove(client);
										System.out.println("客户异常退出！");
										break;
									}
								}
							}
						}).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}

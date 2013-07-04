package com.hzth.myapp.core.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServerTest {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(9999);
		final List<Socket> allSockets = new ArrayList<Socket>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10000);
						System.out.println("共有客户端：" + allSockets.size());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		while (true) {
			final Socket s = ss.accept();
			System.out.println("接收:" + s.getInetAddress() + ",端口:" + s.getPort());
			allSockets.add(s);
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
							PrintWriter pw = new PrintWriter(s.getOutputStream());
							String temp = br.readLine();
							System.out.println(temp);
							pw.println(temp.toUpperCase());
							pw.flush();
							System.out.println("已发送");
						} catch (IOException e) {
							e.printStackTrace();
							allSockets.remove(s);
							break;
						}
					}
				}
			}).start();
		}
	}
}

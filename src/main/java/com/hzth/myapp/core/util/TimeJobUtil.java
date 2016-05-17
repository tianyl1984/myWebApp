package com.hzth.myapp.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TimeJobUtil {

	private static ServerSocket server = null;

	static {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * 以阻塞的方式输出当前时间。不使用sleep等方法，使用socket io阻塞
	 */
	public static void startPrintTimeSlow() {
		try {
			Socket socket = new Socket("localhost", 6789);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("over");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		startPrintTimeSlow();
	}

	private static void init() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					server = new ServerSocket(6789);
					while (true) {
						final Socket socket = server.accept();
						new Thread(new Runnable() {
							@Override
							public void run() {
								PrintWriter pw = null;
								try {
									pw = new PrintWriter(socket.getOutputStream());
									while (true) {
										pw.println(new Date().getTime());
										pw.flush();
										Thread.sleep(1500);
									}
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									if (pw != null) {
										pw.close();
									}
								}
							}
						}).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}).start();
	}

}

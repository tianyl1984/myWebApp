package com.hzth.myapp.net.socket5Proxy;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(8888);
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				new SocketThread(socket).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

package com.hzth.myapp.core.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
	public Client() {
		init();
	}

	public void init() {
		try {
			SocketAddress add = new InetSocketAddress("localhost", Server.port);
			// 返回SocketChannel实例，并绑定SocketAddress
			SocketChannel client = SocketChannel.open(add);
			client.configureBlocking(false);
			ByteBuffer buffer = ByteBuffer.allocate(20);
			// 从通道中读取
			int i = 0;
			do {
				i = client.read(buffer);
				System.out.println(i);
			} while (i == 0);
			// 为读取做准备
			buffer.flip();
			String result = "";
			// 每次读一个字符
			while (buffer.hasRemaining())
				result += String.valueOf((char) buffer.get());
			System.out.println(result);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Client();
			System.out.println("client " + i + " has connected");
		}
	}
}

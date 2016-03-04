package com.hzth.myapp.jdk7;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

public class AsynchronousSocket {

	public static void main(String[] args) throws Exception {
		new Server().start();
		Thread.sleep(2000);
		new Client1().start();
		new Client2().start();
	}
}

class Server {

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("bind...");
					AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(1234));
					while (true) {
						System.out.println("accept...");
						Future<AsynchronousSocketChannel> future = server.accept();
						System.out.println("get...");
						AsynchronousSocketChannel clientSocket = future.get();
						System.out.println("client socket:" + clientSocket.getRemoteAddress());
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						clientSocket.read(buffer).get();
						System.out.println("server read:" + new String(buffer.array()));
						clientSocket.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

}

class Client1 {

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AsynchronousSocketChannel clientSocket = AsynchronousSocketChannel.open();
					clientSocket.connect(new InetSocketAddress("127.0.0.1", 1234)).get();
					clientSocket.write(ByteBuffer.wrap("aaa".getBytes())).get();
					clientSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();
	}
}

class Client2 {

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final AsynchronousSocketChannel clientSocket = AsynchronousSocketChannel.open();
					clientSocket.connect(new InetSocketAddress("127.0.0.1", 1234), null, new CompletionHandler<Void, Void>() {
						@Override
						public void completed(Void result, Void attachment) {
							try {
								clientSocket.write(ByteBuffer.wrap("bbb".getBytes())).get();
								clientSocket.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						@Override
						public void failed(Throwable exc, Void attachment) {
							exc.printStackTrace();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

}
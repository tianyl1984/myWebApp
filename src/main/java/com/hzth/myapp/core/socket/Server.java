package com.hzth.myapp.core.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
	// 服务器端口
	public static int port = 9994;

	public Server() {
		init();
	}

	public void init() {
		Selector selector = null;
		try {
			// 获得Selector实例
			selector = Selector.open();
			// 获得ServerSocketChannel实例
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			InetSocketAddress add = new InetSocketAddress("localhost", port);
			// 设为非阻塞模式，默认为阻塞模式
			serverChannel.configureBlocking(false);
			// channel与一个InetSocketAddress绑定
			serverChannel.socket().bind(add);
			// 向selector注册
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		while (true) {
			try {
				// 如果没有准备好的channel，就在这一直阻塞
				// 注意刚启动时，没有客户端与服务器端连接，会阻塞
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			// 返回已经就绪的SelctionKey，然后迭代执行
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			for (Iterator<SelectionKey> it = readyKeys.iterator(); it.hasNext();) {
				SelectionKey key = (SelectionKey) it.next();
				// 为防止重复迭代要执行remove，在执行selector.select()时，会自动加入去掉的key
				it.remove();
				try {
					// 对应于注册的OP_ACCEPT管道，在这里即ServerSocketChannel
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						// 由于ServerSocketChannel为非阻塞模式，因此不会在这阻塞
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						// 表明接受到一个客户端连接，将其注册到selector
						// 执行selector.select()时可以自动选一个channel
						client.register(selector, SelectionKey.OP_WRITE);
						// 对应于注册的OP_WRITE管道，在这里即SocketChannel
					} else if (key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						// 开辟20个字节的缓冲区
						ByteBuffer buffer = ByteBuffer.allocate(20);
						String str = "hello";
						// 将"hello"封装到buffer
						buffer = ByteBuffer.wrap(str.getBytes());
						// 写入客户端
						client.write(buffer);
						// 写完hello后取消通道的注册
						key.cancel();
					}
				} catch (IOException e) {
					e.printStackTrace();
					key.cancel();
					try {
						// 关闭通道
						key.channel().close();
					} catch (IOException e1) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}

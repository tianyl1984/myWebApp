package com.hzth.myapp.core.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ProtoBufSocketServer {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(9999);
		final List<Socket> allSockets = new ArrayList<Socket>();
		//		new Thread(new Runnable() {
		//
		//			@Override
		//			public void run() {
		//				while (true) {
		//					try {
		//						Thread.sleep(10000);
		//						System.out.println("共有客户端：" + allSockets.size());
		//					} catch (InterruptedException e) {
		//						e.printStackTrace();
		//					}
		//				}
		//			}
		//		}).start();
		while (true) {
			final Socket s = ss.accept();
			System.out.println("接收:" + s.getInetAddress() + ",端口:" + s.getPort());
			allSockets.add(s);
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							//							Msg.Teacher teacher2 = Msg.Teacher.newBuilder().setId(1234).setName("abc").setEmail("abc@234.com").build();
							//							teacher2.writeDelimitedTo(s.getOutputStream());
							//							teacher2.writeDelimitedTo(s.getOutputStream());
							System.out.println("已发送");
							//							s.getOutputStream().flush();

							//							BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
							//							byte[] b = new byte[1024];
							//							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							//							while ((bis.read(b)) != -1) {
							//								baos.write(b);
							//							}

							//							Msg.Teacher teacher = Msg.Teacher.parseFrom(s.getInputStream());
							//							Msg.Teacher teacher = Msg.Teacher.parseFrom(baos.toByteArray());
							//							Msg.Teacher teacher = Msg.Teacher.parseDelimitedFrom(s.getInputStream());
							//							System.out.println(teacher.getId() + ":" + teacher.getName() + ":" + teacher.getEmail());
							break;
						} catch (Exception e) {
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

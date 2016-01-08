package com.hzth.myapp.net.httpProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class HttpProxy extends Thread {
	static public int CONNECT_RETRIES = 5;
	static public int CONNECT_PAUSE = 5;
	private static int TIMEOUT = 50;
	private static int BUFSIZ = 1024;
	// static public boolean logging = false;
	private static OutputStream log = null;
	// 传入数据用的Socket
	protected Socket socket;

	private static Set<String> urls = new HashSet<>();

	// 上级代理服务器，可选
	// static private String parent = null;
	// static private int parentPort = -1;

	// static public void setParentProxy(String name, int pport) {
	// parent = name;
	// parentPort = pport;
	// }

	// 在给定Socket上创建一个代理线程。
	public HttpProxy(Socket s) {
		socket = s;
		start();
	}

	private void writeLog(int c, boolean browser) throws IOException {
		// log.write(c);
	}

	private void writeLog(byte[] bytes, int offset, int len, boolean browser) throws IOException {
		for (int i = 0; i < len; i++) {
			writeLog((int) bytes[offset + i], browser);
		}
	}

	private String processHostName(String url, String host, int port, Socket sock) {
		// System.out.println(url);
		if (!urls.contains(url)) {
			if (url.contains("api/sa")) {
			}
		}
		System.out.println(url);
		urls.add(url);
		return host;
	}

	// 执行操作的线程
	public void run() {
		String line;
		String host;
		int port = 80;
		Socket outbound = null;
		try {
			socket.setSoTimeout(TIMEOUT);
			InputStream is = socket.getInputStream();
			OutputStream os = null;
			try {
				// 获取请求行的内容
				line = "";
				host = "";
				int state = 0;
				boolean space;
				while (true) {
					int c = is.read();
					if (c == -1) {
						break;
					}
					writeLog(c, true);
					space = Character.isWhitespace((char) c);
					switch (state) {
					case 0:
						if (space) {
							continue;
						}
						state = 1;
					case 1:
						if (space) {
							state = 2;
							continue;
						}
						line = line + (char) c;
						break;
					case 2:
						if (space) {
							continue; // 跳过多个空白字符
						}
						state = 3;
					case 3:
						if (space) {
							state = 4;
							// 只取出主机名称部分
							String host0 = host;
							int n;
							n = host.indexOf("//");
							if (n != -1) {
								host = host.substring(n + 2);
							}
							n = host.indexOf('/');
							if (n != -1) {
								host = host.substring(0, n);
							}
							// 分析可能存在的端口号
							n = host.indexOf(":");
							if (n != -1) {
								port = Integer.parseInt(host.substring(n + 1));
								host = host.substring(0, n);
							}
							host = processHostName(host0, host, port, socket);
							int retry = CONNECT_RETRIES;
							while (retry-- != 0) {
								try {
									outbound = new Socket(host, port);
									break;
								} catch (Exception e) {
								}
								// 等待
								Thread.sleep(CONNECT_PAUSE);
							}
							if (outbound == null) {
								break;
							}
							outbound.setSoTimeout(TIMEOUT);
							os = outbound.getOutputStream();
							os.write(line.getBytes());
							os.write(' ');
							os.write(host0.getBytes());
							os.write(' ');
							pipe(is, outbound.getInputStream(), os, socket.getOutputStream());
							break;
						}
						host = host + (char) c;
						break;
					}
				}
			} catch (IOException e) {
			}

		} catch (Exception e) {
		} finally {
			try {
				socket.close();
			} catch (Exception e1) {
			}
			try {
				outbound.close();
			} catch (Exception e2) {
			}
		}
	}

	void pipe(InputStream is0, InputStream is1, OutputStream os0, OutputStream os1) throws IOException {
		try {
			int ir;
			byte bytes[] = new byte[BUFSIZ];
			while (true) {
				try {
					if ((ir = is0.read(bytes)) > 0) {
						os0.write(bytes, 0, ir);
						writeLog(bytes, 0, ir, true);
					}
					else if (ir < 0)
						break;
				} catch (InterruptedIOException e) {
				}
				try {
					if ((ir = is1.read(bytes)) > 0) {
						os1.write(bytes, 0, ir);
						writeLog(bytes, 0, ir, false);
					}
					else if (ir < 0)
						break;
				} catch (InterruptedIOException e) {
				}
			}
		} catch (Exception e0) {
			System.out.println("Pipe异常: " + e0);
		}
	}

	public static void startProxy(int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		while (true) {
			Socket socket = server.accept();
			new HttpProxy(socket);
		}
	}

	// 测试用的简单main方法
	static public void main(String args[]) throws IOException {
		System.out.println("在端口8778启动代理服务器\n");
		HttpProxy.log = System.out;
		HttpProxy.startProxy(8778);
	}
}

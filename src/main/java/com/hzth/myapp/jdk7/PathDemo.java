package com.hzth.myapp.jdk7;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class PathDemo {

	public static void main(String[] args) throws Exception {
		// watchDemo();
		// fileVisitorDemo();
	}

	private static void fileVisitorDemo() {
		try {
			Files.walkFileTree(Paths.get("C:\\Users\\tianyl\\Desktop\\1"), new CustomFileVisitor());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void watchDemo() throws Exception {
		Path path = Paths.get("C:\\Users\\tianyl\\Desktop\\1");
		final WatchService ws = FileSystems.getDefault().newWatchService();
		path.register(ws, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("take...");
						WatchKey wk = ws.take();
						System.out.println("poll...");
						for (WatchEvent<?> we : wk.pollEvents()) {
							System.out.println("contentClass:" + we.context().getClass());
							System.out.println("content:" + we.context());
							System.out.println("kindName:" + we.kind().name());
						}
						boolean rs = wk.reset();
						if (!rs) {
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}

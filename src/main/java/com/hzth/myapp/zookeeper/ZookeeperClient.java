package com.hzth.myapp.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

import com.hzth.myapp.core.util.ThreadUtil;

public class ZookeeperClient {

	public static void main(String[] args) throws Exception {
		final ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
			}
		});
		watch(zk, "/root");
		ThreadUtil.hold();
	}

	private static void watch(final ZooKeeper zk, String path) throws Exception {
		zk.exists(path, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				String path = event.getPath();
				EventType type = event.getType();
				System.out.println(path + ":" + type);
				if (!type.equals(EventType.NodeDeleted)) {
					try {
						System.out.println(new String(zk.getData(path, false, null)));
					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					watch(zk, path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

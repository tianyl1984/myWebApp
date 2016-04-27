package com.hzth.myapp.zookeeper;

import java.util.Date;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperDemo {

	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 1000000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("process:" + event);
			}
		});
		if (zk.exists("/root", false) == null) {
			zk.create("/root", "rootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} else {
			zk.setData("/root", new String("rootData-" + new Date().getTime()).getBytes(), -1);
		}
		byte[] bs = zk.getData("/root", false, null);
		System.out.println(new String(bs));
		// zk.delete("/root", -1);
		if (zk.exists("/abc", false) != null) {
			System.out.println(new String(zk.getData("/abc", false, null)));
		}
		zk.close();
	}

}

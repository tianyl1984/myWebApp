package com.hzth.myapp.thread;

public class VolatileDemo {

	// transient 禁止序列号
	private volatile static boolean flag = false;

	public static void main(String[] args) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!flag) {

				}
			}
		}).start();
		Thread.sleep(200);
		flag = true;
	}
}

package com.hzth.myapp.thread;

public class MultiThreadTest {

	public static void main(String[] args) {
		final MultiThreadTest test = new MultiThreadTest();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						if (test.index >= 100) {
							break;
						}
						test.add();
					}
				}
			}).start();
		}
	}

	private int index = 0;

	public void add() {
		synchronized (this) {
			if (index >= 100) {
				return;
			}
			index++;
		}
		System.out.println(index);
	}

	public synchronized void add2() {
		if (index >= 100) {
			return;
		}
		index++;
		System.out.println(index);
	}
}

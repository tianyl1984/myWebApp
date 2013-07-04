package com.hzth.myapp.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyThreadTest {

	public static void main(String[] args) {
		List<Object> objList = Collections.synchronizedList(new ArrayList<Object>());
		MyThreadTest test = new MyThreadTest();
		test.new AddThread(objList).start();
		test.new DelThread(objList).start();
	}

	class AddThread extends Thread {

		private List<Object> objList;

		public AddThread(List<Object> objList) {
			this.objList = objList;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (objList) {
					while (objList.size() > 5) {
						try {
							objList.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					int count = new Random().nextInt(10);
					System.out.println("start add:" + count + ",current:" + objList.size());
					for (int i = 0; i < count; i++) {
						objList.add(new Object());
					}
					objList.notifyAll();
				}
			}
		}
	}

	class DelThread extends Thread {

		private List<Object> objList;

		public DelThread(List<Object> objList) {
			this.objList = objList;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (objList) {
					while (objList.size() < 5) {
						try {
							objList.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					int count = new Random().nextInt(objList.size());
					System.out.println("start del:" + count + ",current:" + objList.size());
					for (int i = 0; i < count; i++) {
						objList.remove(0);
					}
					objList.notifyAll();
				}
			}
		}
	}
}

package com.hzth.myapp.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

	public static void main(String[] args) throws InterruptedException {
		int jobs = 3;
		CountDownLatch cdl = new CountDownLatch(jobs);
		System.out.println("开始分配任务。。。");
		for (int i = 0; i < jobs; i++) {
			new Worker(cdl).start();
		}
		System.out.println("等待完成任务。。。");
		// 等待计数为0
		cdl.await();
		System.out.println("任务已经全部完成。。。");
	}

	static class Worker extends Thread {

		CountDownLatch cdl = null;

		public Worker(CountDownLatch cdl) {
			this.cdl = cdl;
		}

		public void run() {
			System.out.println("Thread:" + Thread.currentThread().getName() + " is doing job ... ");
			try {
				Thread.sleep(3000 + new Random().nextInt(5000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread:" + Thread.currentThread().getName() + " finished job ");
			// 计数减1
			cdl.countDown();
		}

	}
}
package com.hzth.myapp.thread;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

	public static void main(String[] args) {
		int resource = 4;
		Semaphore semaphore = new Semaphore(resource);
		System.out.println("开始分配任务。。。");
		for (int i = 0; i < resource + 3; i++) {
			new Worker(semaphore).start();
		}
	}

	static class Worker extends Thread {

		private Semaphore semaphore;

		public Worker(Semaphore semaphore) {
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				System.out.println("Thread:" + Thread.currentThread().getName() + " try to get resource ");
				semaphore.acquire();
				System.out.println("Thread:" + Thread.currentThread().getName() + " got resource and working ... ");
				Thread.sleep(3000 + new Random().nextInt(5000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.release();
				System.out.println("Thread:" + Thread.currentThread().getName() + " release resource");
			}
		}

	}

}

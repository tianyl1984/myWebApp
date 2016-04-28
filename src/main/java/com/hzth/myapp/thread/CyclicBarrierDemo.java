package com.hzth.myapp.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

	public static void main(String[] args) {
		int jobs = 4;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
			@Override
			public void run() {
				System.out.println("所有任务已完成，开始退出。。。");
			}
		});
		System.out.println("开始分配任务。。。");
		for (int i = 0; i < jobs; i++) {
			new Worker(cyclicBarrier).start();
		}
		System.out.println("任务已分配完。。。");
	}

	static class Worker extends Thread {

		private CyclicBarrier cyclicBarrier;

		public Worker(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			System.out.println("Thread:" + Thread.currentThread().getName() + " is doing job ... ");
			try {
				Thread.sleep(3000 + new Random().nextInt(5000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread:" + Thread.currentThread().getName() + " finished job,wait other worker ... ");
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("Thread:" + Thread.currentThread().getName() + " exit ... ");
		}

	}

}
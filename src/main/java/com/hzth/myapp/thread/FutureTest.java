package com.hzth.myapp.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTest {

	public static void main(String[] args) throws Exception {
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(500);
				System.out.println("Thread:" + Thread.currentThread().getName());
				return new Random().nextInt(100);
			}
		};
		FutureTask<Integer> future = new FutureTask<Integer>(callable);
		System.out.println("Thread:" + Thread.currentThread().getName());
		new Thread(future).start();
		m1();
		// 只有FutureTask结束才能返回
		System.out.println(future.get());
	}

	private static void m1() throws Exception {
		System.out.println("start my things");
		Thread.sleep(1000);
		System.out.println("end my things");
	}
}

package com.hzth.myapp.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.hzth.myapp.core.util.TimeJobUtil;

public class ExecutorServiceDemo {

	public static void main(String[] args) throws Exception {
		// oneTask();
		// groupTaks();
		// threadTask();
		shutDownNow();
	}

	private static void shutDownNow() {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				TimeJobUtil.startPrintTimeSlow();
			}
		});
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 不保证一运行的线程能终止
		threadPool.shutdownNow();
		System.out.println("over");
		System.out.println("over");
		System.out.println("over");
	}

	private static void groupTaks() throws Exception {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Callable<String>> tasks = new ArrayList<Callable<String>>();
		for (int i = 0; i < 5; i++) {
			final int ii = i;
			tasks.add(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.sleep(1500);
					System.out.println("execute in thread (" + Thread.currentThread().getName() + ")");
					return "task:" + ii;
				}
			});
		}
		List<Future<String>> futures = threadPool.invokeAll(tasks);
		m1();
		for (Future<String> future : futures) {
			System.out.println(future.get());
		}
		threadPool.shutdown();
	}

	private static void oneTask() throws Exception {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Future<Integer> future = threadPool.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(500);
				System.out.println("Thread:" + Thread.currentThread().getName());
				return new Random().nextInt(100);
			}
		});
		m1();
		System.out.println(future.get());
		threadPool.shutdown();
	}

	private static void threadTask() {
		long t1 = System.currentTimeMillis();
		ExecutorService threadPool = Executors.newFixedThreadPool(9);
		for (int i = 0; i < 100; i++) {
			final String aa = i + "";
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + ":" + aa);
				}
			});
		}
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(1000, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("结束:" + (System.currentTimeMillis() - t1));
	}

	private static void m1() throws Exception {
		System.out.println("start my things in thread (" + Thread.currentThread().getName() + ")");
		Thread.sleep(1000);
		System.out.println("end my things in thread (" + Thread.currentThread().getName() + ")");
	}
}

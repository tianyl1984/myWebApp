package com.hzth.myapp.thread;

public class ThreadTest {

	public static void main(String[] args) {
		// m1();
		m2();
	}

	private static void m2() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("123");
				System.out.println(new String[] {}[1]);
				System.out.println("456");
			}
		});
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("出现异常：" + t.getId() + ":" + t.getName());
				System.out.println(Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
				System.out.println(new String[] {}[1]);
			}
		});
		thread.start();
		System.out.println("主线程：" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
		System.out.println("子线程：" + thread.getId() + ":" + thread.getName());
	}

	private static void m1() {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("出现异常：" + t.getId());
				System.out.println(new String[] {}[1]);
				System.out.println(Thread.currentThread().getId());
			}
		});
		System.out.println("123");
		System.out.println(new String[] {}[1]);
		System.out.println("456");
	}
}

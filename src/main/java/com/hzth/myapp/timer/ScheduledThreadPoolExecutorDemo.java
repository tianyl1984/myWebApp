package com.hzth.myapp.timer;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorDemo {

	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable task1 = new Task1();
		Runnable task2 = new Task2();
		executor.scheduleAtFixedRate(task1, 1, 3, TimeUnit.SECONDS);
		executor.scheduleAtFixedRate(task2, 1, 5, TimeUnit.SECONDS);
	}
}

class Task1 implements Runnable {

	@Override
	public void run() {
		// 抛出异常，当前任务不再执行，定时触发的也一并取消，其他添加的继续执行
		System.out.println("Thread:" + Thread.currentThread().getName() + "\ttask1:" + new Date());
		throw new RuntimeException("stop");
	}

}

class Task2 implements Runnable {

	@Override
	public void run() {
		System.out.println("Thread:" + Thread.currentThread().getName() + "\ttask2:" + new Date());
	}

}

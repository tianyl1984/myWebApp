package com.hzth.myapp.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Thread:" + Thread.currentThread().getName() + "job1:" + new Date());
				// 抛出异常，程序退出，所有任务都不再执行
				throw new RuntimeException("stop");
			}
		}, 1000, 1000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Thread:" + Thread.currentThread().getName() + "job2:" + new Date());
			}
		}, 1000, 2000);
	}
}

package com.hzth.myapp.thread;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {

	class MyTimerTask extends TimerTask {

		private int time;

		public MyTimerTask(int time) {
			this.time = time;
		}

		@Override
		public void run() {
			System.out.println("time run... thread:" + Thread.currentThread().getName());
			int time2 = new Random().nextInt(10000);
			System.out.println("next time:" + time2);
			// 内存泄漏 java.lang.OutOfMemoryError: unable to create new native thread
			new Timer().schedule(new MyTimerTask(time2), time);
		}

	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		MyTimerTask task = new TimerDemo().new MyTimerTask(2000);
		new Timer().schedule(task, 0);
		while (true) {
			System.out.println(new Date().getSeconds());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

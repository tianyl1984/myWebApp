package com.hzth.myapp.javaAgent;

import java.lang.management.ManagementFactory;
import java.util.Random;

public class JavaAgentTest {

	// -javaagent:C:\Users\tianyl\git\mywebapp\src\main\java\com\hzth\myapp\javaAgent\agent.jar

	public static void main(String[] args) throws Exception {
		// startTest();
		startTest2();
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(name);
	}

	public static void startTest2() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						startTest();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public static void startTest() throws Exception {
		Random r = new Random();
		int i = Math.abs(r.nextInt() % 5);
		System.out.println("start test");
		Thread.sleep(1000 * i);
		System.out.println("end test");
	}
}

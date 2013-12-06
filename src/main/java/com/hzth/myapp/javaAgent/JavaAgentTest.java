package com.hzth.myapp.javaAgent;

import java.util.Random;

public class JavaAgentTest {

	// -javaagent:C:\Users\tianyl\git\mywebapp\src\main\java\com\hzth\myapp\javaAgent\agent.jar

	public static void main(String[] args) throws Exception {
		startTest();
	}

	public static void startTest() throws Exception {
		Random r = new Random();
		int i = Math.abs(r.nextInt() % 10);
		System.out.println("start test");
		Thread.sleep(1000 * i);
		System.out.println("end test");
	}
}

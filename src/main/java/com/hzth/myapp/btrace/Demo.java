package com.hzth.myapp.btrace;

import java.lang.management.ManagementFactory;
import java.util.Random;

public class Demo {

	public static void main(String[] args) throws Exception {
		Random random = new Random();
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(name);
		Counter counter = new Counter();
		while (true) {
			Foo foo = new Foo();
			counter.add(random.nextInt(10));
			counter.m1(foo);
			System.out.println(counter.getCount());
			Thread.sleep(2000);
		}
	}
}

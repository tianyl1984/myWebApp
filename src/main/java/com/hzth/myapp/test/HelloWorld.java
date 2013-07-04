package com.hzth.myapp.test;

import java.util.Random;

public class HelloWorld {
	public static String randomString(int i) {
		Random ran = new Random(i);
		StringBuilder sb = new StringBuilder();
		while (true) {
			int k = ran.nextInt(27);
			// System.out.println(k);
			if (k == 0) {
				break;
			}
			sb.append((char) ('`' + k));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out.println(randomString(-229985452) + " " + randomString(-147909649));
		Random ran = new Random(System.nanoTime());
		System.out.println(ran.nextInt(10));
		System.out.println(ran.nextInt(10));
		System.out.println(ran.nextInt(10));
		System.out.println(ran.nextInt(10));
	}
}

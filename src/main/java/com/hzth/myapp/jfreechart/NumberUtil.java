package com.hzth.myapp.jfreechart;

import java.util.Random;

public class NumberUtil {

	public static double getRandomNum() {
		return getRandomNum(100d);
	}

	public static double getRandomNum(double d) {
		Random random = new Random();
		return random.nextDouble() * d;
	}

	public static void main(String[] args) {
		System.out.println(getRandomNum());
		System.out.println(getRandomNum());
		System.out.println(getRandomNum());
	}

}

package com.hzth.myapp.jdk7;

public class SwitchDemo {

	public static void main(String[] args) {
		String str = "a";
		// switch 支持字符串
		switch (str) {
		case "a":
			System.out.println("is a");
			break;
		case "b":
			System.out.println("is b");
			break;
		default:
			break;
		}
	}

}

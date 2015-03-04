package com.hzth.myapp.test;

public class Demo {

	public static void main(String[] args) {
		m1();
	}

	private static void m1() {
		System.setSecurityManager(new SecurityManager() {
			@Override
			public void checkExit(int status) {
				System.out.println("checkExit");
				throw new ThreadDeath();
			}
		});
		try {
			System.exit(0);
		} finally {
			System.out.println("In the finally block");
		}
		System.out.println("finish");
	}
}

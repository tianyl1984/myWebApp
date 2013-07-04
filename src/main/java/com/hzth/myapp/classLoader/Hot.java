package com.hzth.myapp.classLoader;

public class Hot {

	public void m1() {
		System.out.println("m1:" + this.getClass().getClassLoader());
	}

	public void m2() {
		System.out.println("m2:" + this.getClass().getClassLoader());
	}
}

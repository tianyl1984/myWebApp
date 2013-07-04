package com.hzth.myapp.btrace;

import java.util.Random;

public class Foo {

	private int i;

	public Foo() {

	}

	public void change() {
		i = new Random().nextInt(10);
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

}

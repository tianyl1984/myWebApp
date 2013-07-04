package com.hzth.myapp.btrace;

public class Counter {

	private int count;

	public int add(int nextInt) {
		count += nextInt;
		return nextInt;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void m1(Foo foo) {
		foo.change();
	}

}

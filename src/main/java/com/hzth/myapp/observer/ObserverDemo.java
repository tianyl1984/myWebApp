package com.hzth.myapp.observer;

public class ObserverDemo {

	public static void main(String[] args) {
		Publisher pub1 = new Publisher("清华出版社");
		TV tv = new TV("CCTV");

		Reader zs = new Reader("张三");
		pub1.addObserver(zs);

		Reader ls = new Reader("李四");
		pub1.addObserver(ls);

		tv.addObserver(zs);

		pub1.publish("计算机科学与技术");
		tv.play("新闻联播");
	}
}

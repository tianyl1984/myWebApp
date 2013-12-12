package com.hzth.myapp.other;

public class JavaParameterDemo {

	public static void main(String[] args) {
		// -Dkey=value 设置系统参数
		System.out.println(System.getProperty("aaa"));
		// -verbose:class 打印类装入的跟踪记录
		// -Djava.awt.headless=true 没有图形设备比如显示器等时使用，模拟显示功能
	}
}

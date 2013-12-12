package com.hzth.myapp.other;

import java.util.Properties;

public class SystemPropertyDemo {

	public static void main(String[] args) {
		// 通过-Dkey=value设置系统参数和值
		Properties prop = System.getProperties();
		for (Object obj : prop.keySet()) {
			System.out.println(obj.toString() + ":" + prop.getProperty(obj.toString()));
		}
	}
}

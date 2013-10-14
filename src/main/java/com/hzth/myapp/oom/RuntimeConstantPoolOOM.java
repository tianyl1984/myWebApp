package com.hzth.myapp.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args:-XX:PermSize=2M -XX:MaxPermSize=2M
 * 
 * @author tianyl
 * 
 *         运行时常量池溢出 java.lang.OutOfMemoryError: PermGen space
 * 
 *         JDK7为 Jave heap space(堆内存溢出)jdk7中将永久区(方法区和常量池)去掉
 */
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while (true) {
			list.add(String.valueOf(i++).intern());
		}
	}
}

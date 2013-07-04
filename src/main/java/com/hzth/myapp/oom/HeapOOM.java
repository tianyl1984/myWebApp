package com.hzth.myapp.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args:-Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=e:/oom.hprof
 * 
 * @author tianyl
 * 
 *         Java堆溢出 java.lang.OutOfMemoryError: Java heap space
 */
public class HeapOOM {

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		while (true) {
			list.add(new OOMObject());
			System.out.println(list.size());
		}
	}
}

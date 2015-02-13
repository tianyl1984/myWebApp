package com.hzth.myapp.test;

import java.util.Arrays;
import java.util.List;

public class ArraysDemo {

	public static void main(String[] args) {
		printList(Arrays.asList("a", "b"));
		printList(Arrays.asList(new String[] { "a", "b" }, "c"));
		printList(Arrays.asList(1, 2, 3));
		printList(Arrays.asList(new Integer[] { 1, 2, 3 }));
		printList(Arrays.asList(new int[] { 1, 2, 3 }));// 可变长参数的坑
	}

	private static <T> void printList(List<T> lists) {
		for (T t : lists) {
			System.out.println(t);
		}
		System.out.println("------------------");
	}

}

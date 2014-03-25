package com.hzth.myapp;

import java.io.File;

public class Test {

	public static void main(String[] args) throws Exception {
		String filePath = "E:/workspace3.7/ws2/src";
		File file = new File(filePath);
		del(file);
	}

	private static void del(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				del(f);
			}
		} else {
			if (file.getName().endsWith("class")) {
				file.delete();
			}
		}
	}

}

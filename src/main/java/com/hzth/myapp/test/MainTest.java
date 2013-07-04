package com.hzth.myapp.test;

import java.io.DataInputStream;
import java.io.IOException;

public class MainTest {

	public static void main(String[] args) {
		dataTest();
	}

	private static void dataTest() {
		DataInputStream in = null;
		try {
			in = new DataInputStream(ClassLoader.getSystemResourceAsStream("com/hzth/myapp/test/aaa.data"));
			byte[] buffer = new byte[6];
			int hasRead = -1;
			while ((hasRead = in.read(buffer)) != -1) {
				if (hasRead == 6) {
					XYCoordinate xyCoordinate = new XYCoordinate(buffer);
					System.out.println(xyCoordinate.toString());
				} else {
					System.out.println("错误数据");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

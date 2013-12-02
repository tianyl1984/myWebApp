package com.hzth.myapp.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class MyFile {

	public static void main(String[] args) throws Exception {
		createFile();
		// randomAccessFileTest();
		System.out.println("完成");
	}

	private static void randomAccessFileTest() throws Exception {
		RandomAccessFile raf = new RandomAccessFile(new File("e:/aaa"), "rw");
		raf.setLength(1024 * 1024 * 1);
		raf.close();
	}

	private static void createFile() throws Exception {
		FileOutputStream fos = new FileOutputStream(new File("e:/eee.zip"));
		byte[] b = new byte[1024];
		for (Integer i = 0; i < b.length; i++) {
			b[i] = i.byteValue();
		}
		for (int i = 0; i < 1024 * 1024 * 2 - 1; i++) {
			fos.write(b);
			if (i % 100 == 0) {
				fos.flush();
			}
		}
		fos.close();
	}
}

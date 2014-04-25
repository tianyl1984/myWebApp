package com.hzth.myapp.other;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FindJar {

	public static void main(String[] args) throws Exception {
		File file = new File("E:\\workspace3.7\\sms\\webapp\\WEB-INF\\lib");
		for (File f : file.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".jar")) {
				ZipFile zipFile = new ZipFile(f);
				Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
				while (enumeration.hasMoreElements()) {
					ZipEntry zipEntry = enumeration.nextElement();
					if (zipEntry.getName().equals("com/runqianapp/schedule/utils/RequestUtil.class")) {
						System.out.println(f.getName());
					}
				}
			}
		}
	}
}

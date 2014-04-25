package com.hzth.myapp.other;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class MargerClass {

	public static void main(String[] args) throws Exception {
		List<File> files = getFiles();
		File targetFile = new File("D:\\tomcat8084-test\\webapps\\dc\\WEB-INF\\lib\\runqian.jar");
		if (targetFile.exists()) {
			targetFile.delete();
		}
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(targetFile));
		Set<String> entryNames = new HashSet<>();
		List<String> repeatNames = new ArrayList<>();
		for (File file : files) {
			System.out.println("合并：" + file.getAbsolutePath());
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> enumZip = zipFile.entries();
			while (enumZip.hasMoreElements()) {
				ZipEntry zipEntry = enumZip.nextElement();
				if (entryNames.contains(zipEntry.getName())) {
					repeatNames.add(zipEntry.getName());
					continue;
				}
				entryNames.add(zipEntry.getName());
				ZipEntry newZipEntry = new ZipEntry(zipEntry.getName());
				zos.putNextEntry(newZipEntry);
				if (!zipEntry.isDirectory()) {
					InputStream is = zipFile.getInputStream(zipEntry);
					int len = -1;
					byte[] buffer = new byte[1024];
					while ((len = is.read(buffer)) != -1) {
						zos.write(buffer, 0, len);
					}
				}
				zos.closeEntry();
			}
			zos.flush();
		}
		zos.finish();
		zos.close();
		System.out.println("重复文件：");
		for (String str : repeatNames) {
			System.out.println(str);
		}
	}

	private static List<File> getFiles() {
		List<File> files = new ArrayList<>();
		File file = new File("D:\\tomcat8084-test\\webapps\\dc\\WEB-INF\\lib");
		for (File f : file.listFiles()) {
			if (f.getName().startsWith("gez") || f.getName().startsWith("report4")) {
				files.add(f);
			}
		}
		return files;
	}
}

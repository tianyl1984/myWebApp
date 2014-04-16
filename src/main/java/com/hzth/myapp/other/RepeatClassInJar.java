package com.hzth.myapp.other;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import com.hzth.myapp.encode.Md5Util;

public class RepeatClassInJar {

	public static void main(String[] args) throws Exception {
		findRepeatClass();
		// checkContains();
	}

	private static void checkContains() throws Exception {
		File file1 = new File("E:\\workspace3.7\\dc-framework\\target\\webapp\\WEB-INF\\lib\\aspectjrt-1.6.9.jar");
		File file2 = new File("E:\\workspace3.7\\dc-framework\\target\\webapp\\WEB-INF\\lib\\aspectjweaver-1.6.9.jar");
		ZipFile zipFile1 = null;// 被包含的
		ZipFile zipFile2 = null;
		if (file1.length() < file2.length()) {
			zipFile1 = new ZipFile(file1);
			zipFile2 = new ZipFile(file2);
		} else {
			zipFile1 = new ZipFile(file2);
			zipFile2 = new ZipFile(file1);
		}
		Map<String, ZipEntry> map2 = new HashMap<>();
		Enumeration<? extends ZipEntry> enumeration2 = zipFile2.entries();
		while (enumeration2.hasMoreElements()) {
			ZipEntry ze = enumeration2.nextElement();
			if (ze.isDirectory() || ze.isDirectory() || !ze.getName().contains(".")) {
				continue;
			}
			map2.put(ze.getName(), ze);
		}

		Enumeration<? extends ZipEntry> enumeration1 = zipFile1.entries();
		boolean flag = true;
		while (enumeration1.hasMoreElements()) {
			ZipEntry ze = enumeration1.nextElement();
			if (ignoreEntry(ze)) {
				continue;
			}
			if (!map2.containsKey(ze.getName())) {
				flag = false;
				System.out.println("文件不同:" + ze.getName());
				break;
			}
			String md51 = Md5Util.md5Encode(IOUtils.toByteArray(zipFile1.getInputStream(ze)));
			String md52 = Md5Util.md5Encode(IOUtils.toByteArray(zipFile1.getInputStream(map2.get(ze.getName()))));
			if (!md51.equals(md52)) {
				System.out.println("文件内容不同:" + ze.getName());
				flag = false;
			}
		}
		System.out.println("比较完成：" + (flag ? "相同" : "不同"));
	}

	private static void findRepeatClass() throws Exception {
		System.out.println("findRepeatClass");
		File file = new File("E:\\workspace3.7\\dc-framework\\target\\webapp\\WEB-INF\\lib");
		Map<String, List<File>> map = new HashMap<String, List<File>>();
		for (File jarFile : file.listFiles()) {
			if (!jarFile.getName().endsWith(".jar")) {
				continue;
			}
			ZipFile zipFile = new ZipFile(jarFile);
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();
				String name = zipEntry.getName();
				if (ignoreEntry(zipEntry)) {
					continue;
				}
				List<File> files = map.get(name);
				if (files == null) {
					files = new ArrayList<>();
					map.put(name, files);
				}
				files.add(jarFile);
			}
		}
		for (String key : map.keySet()) {
			List<File> files = map.get(key);
			if (files.size() > 1) {
				boolean flag = false;
				// flag = true;
				for (File f : files) {
					if (f.getName().equals("apktool-1.1.jar")) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("重复类：" + key);
					for (File f : files) {
						System.out.println(f.getName());
					}
				}
				checkFilesSame(key, files);
			}
		}
	}

	private static void checkFilesSame(String name, List<File> files) throws Exception {
		if (files.size() < 1) {
			return;
		}
		ZipFile zipFile0 = new ZipFile(files.get(0));
		String md50 = Md5Util.md5Encode(IOUtils.toByteArray(zipFile0.getInputStream(zipFile0.getEntry(name))));
		for (File file : files) {
			ZipFile zipFile1 = new ZipFile(file);
			String md51 = Md5Util.md5Encode(IOUtils.toByteArray(zipFile1.getInputStream(zipFile1.getEntry(name))));
			if (md50.equals(md51)) {
				continue;
			} else {
				System.out.println("不一致的类：" + name);
				for (File file2 : files) {
					System.out.println(file2.getName());
				}
				break;
			}
		}
	}

	private static boolean ignoreEntry(ZipEntry zipEntry) {
		String name = zipEntry.getName();
		// org/w3c/dom包jdk中有，jar包中的无效
		if (name.startsWith("org/w3c/dom") || name.startsWith("META-INF") || zipEntry.isDirectory() || !name.contains(".") || name.equals("LICENSE.txt") || name.equals("overview.html") || name.equals("struts-plugin.xml") || name.equals("NOTICE.txt")) {
			return true;
		}
		return false;
	}
}

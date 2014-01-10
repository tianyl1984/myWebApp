package com.hzth.myapp.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hzth.myapp.core.util.FileUtil;

public class LogProcess {

	public static void main(String[] args) {
		List<File> logFiles = new ArrayList<>();
		File file = new File("e:/log/");
		for (File f : file.listFiles()) {
			if (f.isDirectory() || f.getName().equals("target")) {
				for (File f2 : f.listFiles()) {
					logFiles.add(f2);
				}
			}
		}
		for (File f : logFiles) {
			List<String> strList = FileUtil.readLines(f);
			for (String str : strList) {
				if (StringUtils.isNotBlank(str) && !str.startsWith("Hibernate:")) {

				}
			}
		}
	}
}

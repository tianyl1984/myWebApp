package com.hzth.myapp.office;

import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

public class OpenOfficeDemo {

	private static OfficeDocumentConverter converter;

	private static OfficeManager manager;

	public static void main(String[] args) {
		init();
		File file1 = new File("E:/测试文件/员工信息表.doc");
		File file2 = new File("E:/测试文件/员工信息表.pdf");
		converter.convert(file1, file2);
		stop();
	}

	private static void stop() {
		if (manager != null) {
			manager.stop();
		}
	}

	private static void init() {
		DefaultOfficeManagerConfiguration conf = new DefaultOfficeManagerConfiguration();
		manager = conf.buildOfficeManager();
		manager.start();
		converter = new OfficeDocumentConverter(manager);
	}

}

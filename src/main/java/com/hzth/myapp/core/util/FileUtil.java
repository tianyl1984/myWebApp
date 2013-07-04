package com.hzth.myapp.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	public static void append(String fileName, String msg) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(fileName), true);
			fw.append(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

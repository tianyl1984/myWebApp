package com.hzth.myapp.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.hzth.myapp.core.util.UUID;

public class FileProcess {

	public static void main(String[] args) throws IOException {
		List<File> files = getNeedFiles();
		for (File file : files) {
			// makeChangeFile(new File("C:\\Users\\tianyl\\Desktop\\aaaa.jsp"));
			makeChangeFile(file);
		}
	}

	private static void makeChangeFile(File file) throws IOException {
		File bakFile = new File(file.getParentFile().getAbsolutePath() + "/" + UUID.getUUID() + ".jsp");
		FileUtils.copyFile(file, bakFile);
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile)));
			pw = new PrintWriter(file);
			String line = null;
			boolean start = false;
			while ((line = br.readLine()) != null) {
				if (line.contains("<div class=\"ks_report_changeMenu\">")) {
					start = true;
				}
				if (start) {
					if (line.contains(" class=\"ks_current_report\"")) {
						line = line.replace(" class=\"ks_current_report\"", "");
					}
					if (line.contains("><a href=\"javascript:void(0)\"")) {
						line = line.replace("><a href=\"javascript:void(0)\"", "");
						line = line.replace(",'window')\"", ",'window')\"><a href=\"javascript:void(0)\"");
					}
				}
				pw.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

	private static List<File> getNeedFiles() {
		File file = new File("C:\\Users\\tianyl\\Desktop\\新建文件夹");
		List<File> files = new ArrayList<File>();
		for (File f : file.listFiles()) {
			boolean flag = hasString(f, "<div class=\"ks_report_changeMenu\">");
			if (flag) {
				files.add(f);
			}
		}
		return files;
	}

	private static boolean hasString(File f, String str) {
		BufferedReader br = null;
		boolean flag = false;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.indexOf(str) != -1) {
					flag = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}

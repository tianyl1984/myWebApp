package com.hzth.myapp.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

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

	public static void appendln(String fileName, String msg) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(fileName), true);
			fw.append(msg + "\r\n");
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

	public static List<String> readLines(File file) {
		List<String> result = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.trim().equals("")) {
					result.add(line.trim());
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
		return result;
	}

	public static byte[] readBinary(File file) {
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		try {
			fis = new FileInputStream(file);
			baos = new ByteArrayOutputStream();
			byte[] bs = new byte[1024];
			int readLen = -1;
			while ((readLen = fis.read(bs)) != -1) {
				baos.write(bs, 0, readLen);
			}
			baos.flush();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static void createFile(int length) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(new File("e:/测试文件/1g.gif"), "rw");
			raf.setLength(1024 * 1024 * length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveToFile(byte[] data, File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getDesktop() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		return fsv.getHomeDirectory().getAbsolutePath();
	}

	public static void main(String[] args) throws Exception {
		String sql = "insert into foo(name,age) values('abcdefhihk',12);";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 500000; i++) {
			sb.append(sql);
		}
		FileUtil.saveToFile(sb.toString().getBytes("utf-8"), new File(FileUtil.getDesktop() + "/a.sql"));
	}
}

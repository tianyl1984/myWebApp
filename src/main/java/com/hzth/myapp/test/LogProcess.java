package com.hzth.myapp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.hzth.myapp.sql.SqlHelper;

public class LogProcess {

	public static void main(String[] args) {
		List<File> logFiles = new ArrayList<>();
		File file = new File("e:/logs");
		for (File f : file.listFiles()) {
			if (f.isDirectory() || f.getName().equals("target")) {
				for (File f2 : f.listFiles()) {
					if (f2.getName().startsWith("stdout")) {
						logFiles.add(f2);
					}
				}
			}
		}
		// logFiles.clear();
		// logFiles.add(new File("E:/logs/1/stdout_20140108.log"));

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = SqlHelper.getSqlServerConnection("localhost", "dc_all", "sa", "hzth-801");
			ps = conn.prepareStatement("insert into error_log(fileName,lineNum,info) values(?,?,?)");
			for (File f : logFiles) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
					String line = null;
					int lineNum = 0;
					while ((line = br.readLine()) != null) {
						lineNum++;
						if (!line.trim().equals("")) {
							if (Pattern.compile("com\\.unitever\\.dc\\.ex\\.module.*Action").matcher(line).find()) {
								String aa = line.trim().replaceFirst("at ", "");
								aa = aa.substring(0, aa.indexOf("("));
								ps.setString(1, f.getAbsolutePath());
								ps.setInt(2, lineNum);
								ps.setString(3, aa);
								ps.addBatch();
								System.out.println("添加：" + aa);
							}
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
			}
			ps.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
		}
	}
}

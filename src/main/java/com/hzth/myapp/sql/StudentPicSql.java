package com.hzth.myapp.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.hzth.myapp.core.util.UUID;

public class StudentPicSql {

	public static void main(String[] args) throws Exception {
		Map<String, Student> students = getStudents();
		List<File> files = getFiles();
		// Set<String> stuCodes = new HashSet<String>();
		// List<File> repeatFiles = new ArrayList<File>();
		// for (File file : files) {
		// if (file.getName().equals("20090900101.jpg")) {
		// System.out.println("sdfsd");
		// }
		// if (stuCodes.contains(file.getName())) {
		// System.out.println("重复");
		// repeatFiles.add(file);
		// FileUtils.copyFile(file, new File("C:/Documents and Settings/Administrator/桌面/ccc/" + file.getName() + UUID.getUUID() + ".jpg"));
		// }
		// stuCodes.add(file.getName());
		// }
		// for (File file : repeatFiles) {
		// System.out.println(file.getAbsolutePath());
		// }
		// if (true) {
		// return;
		// }
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Statement st = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "";
			url = "jdbc:sqlserver://192.168.1.122;database=dc;sendStringParametersAsUnicode=false";
			String userName = "sa";
			String password = "hzth-801";
			conn = DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("insert into fw_attachment(id,id_owner,path,serverFileName,sourceFilename,fileNum,status) values(?,?,?,?,?,?,?);");
			int i = 0;
			List<File> notExist = new ArrayList<File>();
			List<String> hasExport = new ArrayList<String>();
			for (File f : files) {
				Student stu = students.get(f.getName().substring(0, f.getName().indexOf(".")));
				if (stu == null) {
					System.out.println("存在照片，不存在学生：" + f.getName());
					notExist.add(f);
					continue;
				}
				if (hasExport.contains(stu.getId())) {
					continue;
				}
				i++;
				ps.setString(1, UUID.getUUID());
				ps.setString(2, stu.getId());
				ps.setString(3, "2012/09/06/");
				File newFile = new File("D:/temp/2012/09/06/" + UUID.getUUID() + ".jpg");
				FileUtils.copyFile(f, newFile);
				ps.setString(4, newFile.getName());
				ps.setString(5, f.getName());
				ps.setInt(6, 0);
				ps.setString(7, "1");
				ps.addBatch();
				hasExport.add(stu.getId());
				System.out.println("导入。。。" + i + "。。。" + stu.getId());
			}
			ps.executeBatch();
			conn.commit();
			System.out.println("共导入" + i + "个学生");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (st != null) {
				st.close();
			}
			conn.close();
		}
	}

	private static List<File> getFiles() {
		List<File> result = new ArrayList<File>();
		File file = new File("C:/Documents and Settings/Administrator/桌面/aaa/");
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				for (File f1 : f.listFiles()) {
					if (f1.isFile()) {
						result.add(f1);
					}
				}
			} else {
				result.add(f);
			}
		}
		return result;
	}

	private static Map<String, Student> getStudents() throws Exception {
		Map<String, Student> result = new HashMap<String, Student>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet resultSet = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "";
			url = "jdbc:sqlserver://192.168.1.122;database=dc;sendStringParametersAsUnicode=false";
			String userName = "sa";
			String password = "hzth-801";
			conn = DriverManager.getConnection(url, userName, password);
			ps = conn.prepareStatement("select * from bd_student");
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Student stu = new Student();
				stu.setId(resultSet.getString("id"));
				stu.setStudentCode(resultSet.getString("studentCode").trim());
				result.put(stu.getStudentCode().trim(), stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (st != null) {
				st.close();
			}
			conn.close();
		}
		return result;
	}
}

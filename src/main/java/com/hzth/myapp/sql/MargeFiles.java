package com.hzth.myapp.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class MargeFiles {

	private static final List<String> sqlFiles = new ArrayList<String>();
	static {
		sqlFiles.add("af-sqlserver.sql");// 文件直通车
		sqlFiles.add("ac-sqlserver.sql");// 排课
		sqlFiles.add("base-sqlserver.sql");// 基础
		sqlFiles.add("wc-sqlserver.sql");// 周历
		sqlFiles.add("cm-sqlserver.sql");// 车辆管理
		sqlFiles.add("ca-sqlserver.sql");// 课堂考勤
		sqlFiles.add("od-sqlserver.sql");// 公文流转
		sqlFiles.add("ec-sqlserver.sql");// 选课
		sqlFiles.add("es-sqlserver.sql");// 电子书包
		sqlFiles.add("ex-sqlserver.sql");// 考试
		sqlFiles.add("er-sqlserver.sql");// 对外招考
		sqlFiles.add("fa-sqlserver.sql");// 固定资产
		sqlFiles.add("fw-sqlserver.sql");// framework
		sqlFiles.add("ma-sqlserver.sql");// 材料管理
		sqlFiles.add("mo-sqlserver.sql");// 心情
		sqlFiles.add("mc-sqlserver.sql");// 德育常规检查
		sqlFiles.add("no-sqlserver.sql");// 通知
		// sqlFiles.add("pm-sqlserver.sql");//人事管理
		sqlFiles.add("pu-sqlserver.sql");// 公示
		sqlFiles.add("qn-sqlserver.sql");// 调查问卷
		sqlFiles.add("sd-sqlserver.sql");// 自我展示
		sqlFiles.add("so-sqlserver.sql");// 学生社团
		sqlFiles.add("ss-sqlserver.sql");// 潜能工程
		sqlFiles.add("sa-sqlserver.sql");// 学生评价
		// sqlFiles.add("sf-sqlserver.sql");//学生档案
		sqlFiles.add("ns-sqlserver.sql");// 招生管理
		// sqlFiles.add("tf-sqlserver.sql");//教师档案
		// sqlFiles.add("tr-sqlserver.sql");//教科研
		sqlFiles.add("fi-sqlserver.sql");// 工资
		// sqlFiles.add("-sqlserver.sql");//
		// sqlFiles.add("-sqlserver.sql");//
	}

	public static void main(String[] args) {
		String baseDir = "E:/workspacedc";
		margeSqlFiles(baseDir);
		// clear(baseDir);
		// copyJava(baseDir);
		// copyJsp(baseDir);
	}

	private static void margeSqlFiles(String baseDir) {
		File file = new File(baseDir);
		List<File> allFile = new ArrayList<File>();
		for (File f : file.listFiles()) {
			if (!f.getName().startsWith("dc-")) {
				continue;
			}
			File src = new File(f.getAbsolutePath() + "/doc");
			if (!src.exists()) {
				System.out.println("不存在：" + src.getAbsolutePath());
				return;
			}
			for (File sqlfile : src.listFiles()) {
				// if (sqlFiles.contains(sqlfile.getName()) && sqlfile.getName().endsWith(".sql") && !sqlfile.getName().equals("update.sql")) {
				if (sqlfile.getName().endsWith(".sql")) {
					allFile.add(sqlfile);
				}
				// }
			}
		}
		// base和fw排序
		File baseFile = null;
		File fwFile = null;
		for (File ff : allFile) {
			if (ff.getName().startsWith("base-")) {
				baseFile = ff;
			}
			if (ff.getName().startsWith("fw-")) {
				fwFile = ff;
			}
		}
		allFile.remove(baseFile);
		allFile.remove(fwFile);
		allFile.add(0, fwFile);
		allFile.add(1, baseFile);
		// 合并
		File newFile = new File("E:/workspace3.7/myWebApp/src/main/java/com/hzth/myapp/sql/all.sql");
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}
		if (newFile.exists()) {
			newFile.delete();
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(newFile);
			for (File sqlfile : allFile) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(sqlfile));
					String temp = null;
					while ((temp = br.readLine()) != null) {
						pw.println(temp);
					}
					pw.flush();
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	private static void copyJsp(String baseDir) {
		File file = new File(baseDir);
		for (File f : file.listFiles()) {
			if (!f.getName().startsWith("dc-")) {
				continue;
			}
			File webapp = new File(f.getAbsolutePath() + "/webapp");
			List<File> allFile = new ArrayList<File>();
			findFiles(webapp, allFile);
			for (File fromFile : allFile) {
				String path = fromFile.getAbsolutePath();
				path = path.replaceFirst(f.getName(), "dcAll");
				File toFile = new File(path);
				if (toFile.exists()) {
					System.out.println(f.getName() + ":" + fromFile.getName() + "已存在!");
				} else {
					try {
						FileUtils.copyFile(fromFile, toFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static void copyJava(String baseDir) {
		File file = new File(baseDir);
		for (File f : file.listFiles()) {
			if (!f.getName().startsWith("dc-")) {
				continue;
			}
			File src = new File(f.getAbsolutePath() + "/src");
			List<File> allFile = new ArrayList<File>();
			findFiles(src, allFile);
			for (File fromFile : allFile) {
				String path = fromFile.getAbsolutePath();
				path = path.replaceFirst(f.getName(), "dcAll");
				File toFile = new File(path);
				if (toFile.exists()) {
					System.out.println(f.getName() + ":" + fromFile.getName() + "已存在!");
				} else {
					try {
						FileUtils.copyFile(fromFile, toFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static void findFiles(File src, List<File> allFile) {
		for (File file : src.listFiles()) {
			if (file.getName().equals("WEB-INF")) {
				continue;
			}
			if (file.isFile()) {
				// if (file.getName().equals("jdbc.properties")) {
				// continue;
				// }
				allFile.add(file);
			} else {
				findFiles(file, allFile);
			}
		}
	}

	private static void clear(String baseDir) {
		// File file = new File(baseDir + "/dcAll/");
	}
}

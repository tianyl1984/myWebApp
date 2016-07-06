package com.hzth.myapp.sql;

import java.io.File;

import com.hzth.myapp.core.util.FileUtil;

public class SqlFileUtil {

	public static void main(String[] args) {
		String sql = "insert into foo(name,age) values('aaaaaaaaaa',10);";
		StringBuffer sqlSb = new StringBuffer();
		long max = 1 * 1024 * 1024;
		for (int i = 0; i < (max / sql.length() + 1); i++) {
			sqlSb.append(sql);
		}
		FileUtil.saveToFile(sqlSb.toString().getBytes(), new File(FileUtil.getDesktop() + "/aaaa.sql"));
	}

}

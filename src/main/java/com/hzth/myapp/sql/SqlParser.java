package com.hzth.myapp.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SqlParser {

	public static List<CommonSql> parse(File file) {
		BufferedReader br = null;
		List<CommonSql> sqlList = new ArrayList<CommonSql>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String temp = null;
			String sql = "";
			while ((temp = br.readLine()) != null) {
				temp = temp.trim();
				if (StringUtils.isBlank(temp)) {
					continue;
				}
				if (temp.startsWith("/*") || temp.startsWith("--")) {
					continue;
				}
				if (temp.toLowerCase().startsWith("insert ")) {
					sqlList.add(new CommonSql(temp));
					continue;
				}
				// 出现结束
				if (temp.startsWith(")") || temp.toLowerCase().equals("go") || temp.toLowerCase().startsWith("go/*") || temp.substring(temp.length() - 1, temp.length()).equals(";")) {
					if (sql.trim().toLowerCase().startsWith("alter table") || sql.trim().toLowerCase().startsWith("create table")) {
						if (!temp.toLowerCase().equals("go") && !temp.toLowerCase().startsWith("go/*")) {
							sql += " " + temp;
						}
						if (!temp.contains(";")) {
							sql += ";";
						}
						sql = sql.trim();
						sqlList.add(new CommonSql(sql));
					} else {
						if (temp.equals(";") || temp.toLowerCase().trim().equals("go") || temp.toLowerCase().trim().startsWith("go/*")) {

						} else {
							sqlList.add(new CommonSql(temp));
						}
					}
					sql = "";
				} else {
					sql += " " + temp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sqlList;
	}
}

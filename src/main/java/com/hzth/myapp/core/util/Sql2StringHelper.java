package com.hzth.myapp.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Sql2StringHelper {

	public static void sql2StringBuffer(String file) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
		System.out.println("StringBuffer buff = new StringBuffer();");
		//		StringBuffer sql = new StringBuffer();
		//		sql.append("");
		String temp = null;
		while ((temp = br.readLine()) != null) {
			System.out.println("buff.append(\" " + temp + " \");");
		}
	}

	public static void sql2String(String file) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
		System.out.println("String sql = new String();");
		String temp = null;
		while ((temp = br.readLine()) != null) {
			System.out.println("sql += \" " + temp + " \";");
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("ccc");
		sql2StringBuffer("C:\\Documents and Settings\\Administrator\\桌面\\sql\\mysql\\HScoreReportDAO.getGradeExamRank.sql");
		sql2String("C:\\Documents and Settings\\Administrator\\桌面\\sql\\mysql\\HScoreReportDAO.getGradeExamRank.sql");
	}
}

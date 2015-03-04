package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * jdbc 自动增长id
 * 
 * @author tianyl
 * 
 */
public class AutoIncrementId {

	public static void main(String[] args) throws Exception {
		// m1();
		m2();
	}

	private static void m2() throws Exception {
		Connection conn = SqlHelper.getMysqlConnection("192.168.90.185", 3308, "test", "root", "hzth-801");
		PreparedStatement ps = conn.prepareStatement("insert into tab1 (age,name) values(12,'123123'),(13,'232332')", Statement.RETURN_GENERATED_KEYS);
		int result = ps.executeUpdate();
		System.out.println("---------executeBatch----------");
		System.out.println(result);
		System.out.println("---------executeBatch----------");
		ResultSet rs = ps.getGeneratedKeys();
		System.out.println("----------GeneratedKeys----------");
		while (rs.next()) {
			System.out.println(rs.getInt(1));
		}
		System.out.println("----------GeneratedKeys----------");
		ResultSetMetaData rsmd = rs.getMetaData();
		System.out.println("----------GeneratedKeys MetaData----------");
		int count = rsmd.getColumnCount();
		for (int i = 0; i < count; i++) {
			String labName = rsmd.getColumnLabel(i + 1);
			System.out.println(labName);
		}
		System.out.println("----------GeneratedKeys MetaData----------");
		SqlHelper.close(conn);
	}

	private static void m1() throws Exception {
		Connection conn = SqlHelper.getMysqlConnection("192.168.90.185", 3308, "test", "root", "hzth-801");
		PreparedStatement ps = conn.prepareStatement("insert into tab1 (age,name) values(?,?)", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, 10);
		ps.setString(2, "lisi");
		ps.addBatch();
		ps.setInt(1, 20);
		ps.setString(2, "张三");
		ps.addBatch();
		int[] result = ps.executeBatch();
		System.out.println("---------executeBatch----------");
		for (int i : result) {
			System.out.println(i);
		}
		System.out.println("---------executeBatch----------");
		ResultSet rs = ps.getGeneratedKeys();
		System.out.println("----------GeneratedKeys----------");
		while (rs.next()) {
			System.out.println(rs.getInt(1));
		}
		System.out.println("----------GeneratedKeys----------");
		ResultSetMetaData rsmd = rs.getMetaData();
		System.out.println("----------GeneratedKeys MetaData----------");
		int count = rsmd.getColumnCount();
		for (int i = 0; i < count; i++) {
			String labName = rsmd.getColumnLabel(i + 1);
			System.out.println(labName);
		}
		System.out.println("----------GeneratedKeys MetaData----------");
		SqlHelper.close(conn);
	}
}

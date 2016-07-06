package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.Statement;

public class SqlTest {

	public static void main(String[] args) throws Exception {
		Connection conn = SqlHelper.getMysqlConnection("172.16.20.163", "demo", "root", "ufenqi@321");
		String sql = "insert into foo(name,age) value('a',10)";
		conn.setAutoCommit(false);
		Statement st = conn.createStatement();
		st.setQueryTimeout(5);
		int n = st.executeUpdate(sql);
		System.out.println(n);
		conn.commit();
		conn.close();
	}

}

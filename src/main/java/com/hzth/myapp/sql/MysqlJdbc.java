package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MysqlJdbc {

	public static void main(String[] args) throws Exception {
		Long t1 = System.currentTimeMillis();
		Connection conn = null;
		// PreparedStatement ps = null;
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://192.168.1.122:3306/mtg?createDatabaseIfNotExist=true&ampuseUnicode=true&amp;characterEncoding=utf-8";
		String userName = "root";
		String password = "hzth-801";
		conn = DriverManager.getConnection(url, userName, password);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into mytest(name) select replace(uuid(),'-','') from ex_exam;");
		Statement st = conn.createStatement();
		st.execute(sql.toString());
		// ps = conn.prepareStatement(sql.toString());
		// ps.execute();
		// ps.close();
		conn.close();
		System.out.println("用时:" + (System.currentTimeMillis() - t1));
	}
}

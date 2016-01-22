package com.hzth.myapp.mysql;

import java.sql.Connection;
import java.sql.ResultSet;

import com.hzth.myapp.sql.SqlHelper;

public class Mysql57 {

	public static void main(String[] args) throws Exception {
		Connection conn = SqlHelper.getMysqlConnection("localhost", 5700, "demo", "root", "hzth-801");
		ResultSet rs = conn.prepareStatement("select * from tab1").executeQuery();
		while (rs.next()) {
			System.out.println(rs.getInt("id") + ":" + rs.getString("data"));
		}
	}

}

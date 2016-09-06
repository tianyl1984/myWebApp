package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class MysqlDateTimeDemo {

	public static void main(String[] args) throws Exception {
		Connection conn = SqlHelper.getMysqlConnection("127.0.0.1", 3306, "demo", "root", "ufenqi@321");
		// Connection conn = SqlHelper.getMysqlConnection("tianice.51vip.biz", 3306, "demo", "root", "tyl123");
		Date date = new Date(1473146749912L);
		System.out.println(date.getTime());
		// System.out.println(new java.sql.Timestamp(date.getTime()).getTime());
		PreparedStatement ps = conn.prepareStatement("update test set d1 = ? where id = 1");
		ps.setObject(1, date);
		ps.execute();
		PreparedStatement queryPs = conn.prepareStatement("select * from test where d1 = ? ");
		queryPs.setObject(1, date);
		ResultSet rs = queryPs.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getLong(1));
		}
		rs.close();
		rs = conn.prepareStatement("select * from test").executeQuery();
		while (rs.next()) {
			System.out.println(((java.sql.Timestamp) rs.getObject(2)).getTime());
		}
		conn.close();
	}

}

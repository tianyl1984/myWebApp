package com.hzth.myapp;

import java.sql.Connection;
import java.sql.ResultSet;

import com.hzth.myapp.sql.SqlHelper;

public class Test {

	public static void main(String[] args) throws Exception {
		Connection conn1 = SqlHelper.getSqlServerSaConnection("127.0.0.1", "dc_all");
		ResultSet rs = conn1.prepareStatement("select * from fw_attachmentsetting").executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("aaa"));
		}
		// Connection conn2 = SqlHelper.getSqlServerSaConnection("127.0.0.1", "dc_all");
		// PreparedStatement ps = conn2.prepareStatement("update fw_attachmentsetting set aaa = ?");
		// ps.setString(1, aa);
		// ps.execute();
	}

}

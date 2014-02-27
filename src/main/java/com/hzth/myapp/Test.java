package com.hzth.myapp;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hzth.myapp.encode.Base64Util;
import com.hzth.myapp.sql.SqlHelper;

public class Test {

	public static void main(String[] args) throws Exception {
		Connection conn2 = SqlHelper.getSqlServerSaConnection("127.0.0.1", "dc_all");
		PreparedStatement ps = conn2.prepareStatement("update aaa set a6=? where a5 = '20140113150402814239422238999955'");

		byte[] b = new byte[1024 * 1024 * 50];
		for (Integer i = 0; i < b.length; i++) {
			b[i] = i.byteValue();
		}

		ps.setString(1, Base64Util.encryptBASE64(b));
		ps.execute();
	}

}

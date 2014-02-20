package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzth.myapp.core.util.UUID;

public class TransSqlDemo3 {

	public static void main(String[] args) {
		new TransSqlDemo3().thread2.start();
	}

	String sql1 = "insert into aaa(a1,a2,a3,a5) values(1,2,3,?)";
	String sql2 = "select * from bbb";
	String sql3 = "insert into bbb(b1,b2,b3,b5) values(1,2,3,?)";
	String sql4 = "select * from aaa";

	private Thread thread2 = new Thread(new Runnable() {
		@Override
		public void run() {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				conn = SqlHelper.getSqlServerConnection("192.168.30.123", "dc_all", "sa", "hzth-801");
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				// conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				System.out.println("0");
				ps = conn.prepareStatement(sql3);
				System.out.println("1");
				ps.setString(1, UUID.getUUID());
				System.out.println("2");
				ps.execute();
				System.out.println("3");

				rs = conn.prepareStatement(sql2).executeQuery();
				System.out.println("4");
				while (rs.next()) {
					System.out.println("5");
					System.out.println(rs.getString("b5"));
				}

				System.out.println("6");
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				SqlHelper.close(conn);
			}
		}
	});
}

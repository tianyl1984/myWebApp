package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzth.myapp.core.util.UUID;

public class TransSqlDemo2 {

	public static void main(String[] args) {
		// while (true) {
		// new TransSqlDemo2().thread1.start();
		new TransSqlDemo2().thread2.start();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// }
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
				ps = conn.prepareStatement(sql3);
				ps.setString(1, UUID.getUUID());
				ps.execute();
				ps = conn.prepareStatement(sql4);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println("Thread2:" + rs.getInt("a1"));
				}
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				SqlHelper.close(conn);
			}
		}
	});
}

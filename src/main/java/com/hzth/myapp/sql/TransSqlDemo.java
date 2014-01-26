package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzth.myapp.core.util.UUID;

public class TransSqlDemo {

	public static void main(String[] args) {
		// while (true) {
		new TransSqlDemo().thread1.start();
		// new TransSqlDemo().thread2.start();
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

	private Thread thread1 = new Thread(new Runnable() {
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
				ps = conn.prepareStatement(sql1);
				ps.setString(1, UUID.getUUID());
				ps.execute();
				System.out.println();
				ps = conn.prepareStatement(sql2);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println("Thread1:" + rs.getInt("b1"));
				}
				conn.commit();
				System.out.println("Thread1:5");
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
	private Thread thread2 = new Thread(new Runnable() {
		@Override
		public void run() {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				conn = SqlHelper.getSqlServerConnection("192.168.30.123", "dc_all", "sa", "hzth-801");
				conn.setAutoCommit(false);
				// conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				ps = conn.prepareStatement(sql3);
				ps.setString(1, UUID.getUUID());
				ps.execute();
				System.out.println("Thread2：插入。。。");
				Thread.sleep(5 * 1000);
				System.out.println("Thread2:1");
				ps = conn.prepareStatement(sql1);
				System.out.println("Thread2:2");
				ps.setString(1, UUID.getUUID());
				System.out.println("Thread2:3");
				ps.execute();
				System.out.println("Thread2:4");
				// rs = ps.executeQuery();
				// while (rs.next()) {
				// System.out.println("Thread2:" + rs.getInt("a1"));
				// }
				conn.commit();
				System.out.println("Thread2:5");
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

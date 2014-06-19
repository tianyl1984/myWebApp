package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LockDemo {

	public static void main(String[] args) throws Exception {
		// shareLock();
		deleteDemo();
	}

	private static void deleteDemo() throws Exception {
		Connection conn = SqlHelper.getSqlServerSaLocalConnection("nls");
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);

		String sql0 = "delete from ex_studentscore where id = ?";
		PreparedStatement ps = conn.prepareStatement(sql0);
		ps.setString(1, "20140425152619292603474432533831");
		boolean result = ps.execute();
		System.out.println("执行结果：" + result);
		conn.rollback();
		SqlHelper.close(conn);
	}

	private static void shareLock() throws Exception {
		Connection conn = SqlHelper.getSqlServerSaLocalConnection("nls");
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);

		// Statement st = conn.createStatement();
		// String sql = "select * from ex_studentscore where id_grade = '20130407175936117357843567573850'";
		// ResultSet rs = st.executeQuery(sql);
		// while (rs.next()) {
		// System.out.println(rs.getString("id"));
		// break;
		// }

		// String sql2 = "select * from ex_studentscore where id_grade = ?";
		// PreparedStatement ps = conn.prepareStatement(sql2);
		// ps.setString(1, "20130407175936117357843567573850");
		// ResultSet rs = ps.executeQuery();
		// while (rs.next()) {
		// System.out.println(rs.getString("id"));
		// }

		String sql2 = "select * from ex_studentscore where id_grade = '20130407175936117357843567573850'";
		PreparedStatement ps = conn.prepareStatement(sql2);
		// ps.setString(1, "20130407175936117357843567573850");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("id"));
		}
		conn.commit();
		SqlHelper.close(conn);
	}

}

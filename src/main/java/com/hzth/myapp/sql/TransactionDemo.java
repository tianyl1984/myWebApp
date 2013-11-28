package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hzth.myapp.core.util.DateUtil;
import com.hzth.myapp.core.util.UUID;

public class TransactionDemo {

	public static void main(String[] args) throws Exception {
		// insertBatch();

		search();
		search2();
		// delete();
	}

	private static void delete() throws Exception {
		List<String> deleteIds = getDeleteIds();
		Thread.sleep(1000);
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = SqlHelper.getSqlServerConnection("127.0.0.1", "nls2", "sa", "hzth-801");
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete ex_exam where id = ?");
			for (String id : deleteIds) {
				System.out.println("删除--->" + DateUtil.getCurrentDate() + ":" + id);
				ps.setString(1, id);
				ps.execute();
				Thread.sleep(500);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			SqlHelper.close(conn);
		}
		insertBatch();
	}

	private static List<String> getDeleteIds() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> result = new ArrayList<String>();
		try {
			conn = SqlHelper.getSqlServerConnection("127.0.0.1", "nls2", "sa", "hzth-801");
			ps = conn.prepareStatement("select * from ex_exam where id_schoolterm is null");
			rs = ps.executeQuery();
			while (rs.next()) {
				result.add(rs.getString("id"));
				// System.out.println(":" + rs.getString("startDate") + ":");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
		}
		return result;
	}

	private static void insertBatch() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Connection conn = null;
				PreparedStatement ps = null;
				try {
					conn = SqlHelper.getSqlServerConnection("127.0.0.1", "nls2", "sa", "hzth-801");
					System.out.println("插入--->" + DateUtil.getCurrentDate());
					ps = conn.prepareStatement("insert into ex_exam(id) values(?)");
					int i = 0;
					while (true) {
						ps.setString(1, UUID.getUUID());
						ps.execute();
						// Thread.sleep(500);
						i++;
						if (i == 30) {
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					SqlHelper.close(conn);
				}
			}
		}).start();
	}

	private static void search() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Connection conn = null;
					PreparedStatement ps = null;
					ResultSet rs = null;
					try {
						conn = SqlHelper.getSqlServerConnection("127.0.0.1", "nls2", "sa", "hzth-801");
						// conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
						System.out.println("查询1--->" + DateUtil.getCurrentDate());
						// ps = conn.prepareStatement("select eg.* from ex_examgrade eg left join ex_exam e on e.id = eg.id_exam");
						// ps = conn.prepareStatement("select * from ex_exam ");
						ps = conn.prepareStatement("select e.* from ex_exam e left join bd_schoolterm st on e.id_schoolterm = st.id ");
						rs = ps.executeQuery();
						while (rs.next()) {
							rs.getString("id");
							// System.out.println(":" + rs.getString("startDate") + ":");
						}
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						SqlHelper.close(conn);
					}
				}
			}
		}).start();

	}

	private static void search2() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (true) {
					Connection conn = null;
					PreparedStatement ps = null;
					ResultSet rs = null;
					try {
						conn = SqlHelper.getSqlServerConnection("127.0.0.1", "nls2", "sa", "hzth-801");
						// conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
						System.out.println("查询2--->" + DateUtil.getCurrentDate());
						// ps = conn.prepareStatement("select eg.* from ex_examgrade eg left join ex_exam e on e.id = eg.id_exam");
						// ps = conn.prepareStatement("select * from ex_exam ");
						ps = conn.prepareStatement("select e.* from ex_exam e left join bd_schoolterm st on e.id_schoolterm = st.id ");
						rs = ps.executeQuery();
						while (rs.next()) {
							rs.getString("id");
							System.out.println(i + "查询2--->:" + rs.getString("startDate") + ":");
							Thread.sleep(500);
						}
						i++;
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						SqlHelper.close(conn);
					}
				}
			}
		}).start();

	}
}

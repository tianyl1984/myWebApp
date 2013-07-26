package com.hzth.myapp.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SqlHelper {

	public static void main(String[] args) throws Exception {
		// printSql();
		Connection conn = null;
		try {
			// conn = getSqlServerConnection("192.168.1.122", "dc_empty", "sa", "hzth-801");
			// conn = getMysqlConnection("192.168.1.122", "aaa", "root", "hzth-801");
			conn = getOracleConnection("192.168.1.194", "orcl", "hzth", "hzth-801");
			Map<String, TableInfo> tabMap = CreateDBSchema.getTableInfo(conn);
			for (String key : tabMap.keySet()) {
				TableInfo tableInfo = tabMap.get(key);
				System.out.println(tableInfo.getName());
				for (String colkey : tableInfo.getColumnInfoMap().keySet()) {
					ColumnInfo info = tableInfo.getColumnInfoMap().get(colkey);
					System.out.println(info.getName() + ":" + info.getType());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
	}

	public static String getSqlValue(String val) {
		return val.replaceAll("'", "''");
	}

	public static List<String> getColumnLabel(ResultSet rs) throws SQLException {
		List<String> result = new ArrayList<String>();
		ResultSetMetaData metaData = rs.getMetaData();
		for (int col = 1; col <= metaData.getColumnCount(); col++) {
			String label = metaData.getColumnLabel(col);
			result.add(label);
		}
		return result;
	}

	public static List<Integer> getColumnType(ResultSet rs) throws SQLException {
		List<Integer> result = new ArrayList<Integer>();
		ResultSetMetaData metaData = rs.getMetaData();
		for (int col = 1; col <= metaData.getColumnCount(); col++) {
			result.add(metaData.getColumnType(col));
		}
		return result;
	}

	private static void printSql() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\tianyl\\Desktop\\sql\\按班级统计两率均分.sql")), "utf-8"));
		String temp = null;
		System.out.println("StringBuffer sqlBuffer = new StringBuffer();");
		while ((temp = br.readLine()) != null) {
			System.out.println("sqlBuffer.append(\" " + temp + " \");");
		}
		br.close();
	}

	public static Connection getSqlServerConnection(String ip, String database, String userName, String password) throws ClassNotFoundException, SQLException {
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://" + ip + ";database=" + database + ";sendStringParametersAsUnicode=false";
		return getConnection(driver, url, userName, password);
	}

	public static Connection getMysqlConnection(String ip, String database, String userName, String password) throws ClassNotFoundException, SQLException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" + ip + ":3306/" + database + "?useUnicode=true&characterEncoding=utf-8";
		return getConnection(driver, url, userName, password);
	}

	public static Connection getOracleConnection(String ip, String database, String userName, String password) throws ClassNotFoundException, SQLException {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@" + ip + ":1521:" + database;
		return getConnection(driver, url, userName, password);
	}

	public static Connection getConnection(String driver, String url, String userName, String password) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection(url, userName, password);
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<InsertSql> sortInsertSql(List<InsertSql> sqls) {
		int size = sqls.size();
		List<InsertSql> result = new ArrayList<InsertSql>();
		List<InsertSql> lastPart = new ArrayList<InsertSql>();
		Iterator<InsertSql> it = sqls.iterator();
		while (it.hasNext()) {
			InsertSql sql = it.next();
			if (!sql.getLabels().contains("id")) {
				lastPart.add(sql);
				it.remove();
			}
		}
		sortInsertSql(result, sqls);
		result.addAll(lastPart);
		if (result.size() != size) {
			throw new RuntimeException("排序InsertSql出错!" + size + ":" + result.size());
		}
		return result;
	}

	private static void sortInsertSql(List<InsertSql> result, List<InsertSql> sqls) {
		if (sqls.size() == 0) {
			return;
		}
		Iterator<InsertSql> it = sqls.iterator();
		while (it.hasNext()) {
			InsertSql sql = it.next();
			if (!sql.hasForeignKey()) {
				result.add(sql);
				it.remove();
				continue;
			}
			boolean has = false;
			List<String> foreignVal = sql.getForeignValues();
			List<String> ids = getIds(sqls);
			for (String val : foreignVal) {
				if (ids.contains(val)) {
					has = true;
					break;
				}
			}
			if (!has) {
				result.add(sql);
				it.remove();
				continue;
			}
		}
		sortInsertSql(result, sqls);
	}

	private static List<String> getIds(List<InsertSql> sqls) {
		List<String> ids = new ArrayList<String>();
		for (InsertSql sql : sqls) {
			ids.add(sql.getId());
		}
		return ids;
	}

	public static List<InsertSql> createInsertSql(Connection conn, Connection conn2, List<String> tables) throws Exception {
		List<InsertSql> sqls = new ArrayList<InsertSql>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		Map<String, TableInfo> tabMap2 = CreateDBSchema.getTableInfo(conn2);
		for (String tab : tables) {
			List<String> allIds = new ArrayList<String>();
			ps = conn.prepareStatement("select * from " + tab);
			rs = ps.executeQuery();
			List<String> labels = SqlHelper.getColumnLabel(rs);
			List<Integer> types = SqlHelper.getColumnType(rs);
			while (rs.next()) {
				if (labels.contains("id")) {
					allIds.add(rs.getString("id"));
				} else {
					String value = "";
					for (String label : labels) {
						value += rs.getString(label) + ":";
					}
					if (value.contains("null")) {
						throw new RuntimeException("中间表null值");
					}
					allIds.add(value);
				}
			}
			String sql = "";
			for (String id : allIds) {
				boolean hasThis = false;
				if (labels.contains("id")) {
					sql = "select * from " + tab + " where id='" + id + "'";
				} else {
					sql = "select * from " + tab + " where ";
					String[] ids = id.split(":");
					int i = 0;
					for (String label : labels) {
						if (i > 0) {
							sql += " and ";
						}
						sql += " " + label + "='" + ids[i] + "'";
						i++;
					}
				}
				if (tabMap2.containsKey(tab)) {
					ps2 = conn2.prepareStatement(sql);
					rs2 = ps2.executeQuery();
					hasThis = rs2.next();
				}
				if (!hasThis) {// 不存在的数据
					rs = conn.prepareStatement(sql).executeQuery();
					boolean flag = false;
					while (rs.next()) {
						InsertSql insertSql = new InsertSql();
						insertSql.setTable(tab);
						int i = 0;
						for (String label : labels) {
							Integer type = types.get(i);
							if (Types.INTEGER == type) {
								insertSql.add(type, rs.getObject(label), label);
							} else if (Types.CHAR == type || Types.VARCHAR == type) {
								insertSql.add(type, rs.getObject(label), label);
							} else {
								throw new RuntimeException("不支持类型:" + type);
							}
						}
						sqls.add(insertSql);
						flag = true;
					}
					if (!flag) {
						throw new RuntimeException("数据不存在。");
					}
				}
			}
		}
		sqls = SqlHelper.sortInsertSql(sqls);
		return sqls;
	}

	public static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

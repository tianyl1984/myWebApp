package com.hzth.myapp.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hzth.myapp.sql.model.ColumnInfo;
import com.hzth.myapp.sql.model.FKInfo;
import com.hzth.myapp.sql.model.TableInfo;

public class SqlHelper {

	public static void main(String[] args) throws Exception {
		printSql();
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
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:\\workspace3.7\\ScoreExport\\src\\com\\unitever\\cydc\\sql.txt")), "utf-8"));
		String temp = null;
		System.out.println("StringBuffer sqlBuffer = new StringBuffer();");
		while ((temp = br.readLine()) != null) {
			System.out.println("sqlBuffer.append(\" " + temp + " \");");
		}
		br.close();
	}

	public static Connection getSqlServerSaConnection(String ip, String database) throws ClassNotFoundException, SQLException {
		return getSqlServerConnection(ip, database, "sa", "hzth-801");
	}

	public static Connection getSqlServerSaLocalConnection(String database) throws ClassNotFoundException, SQLException {
		return getSqlServerConnection("127.0.0.1", database, "sa", "hzth-801");
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

	public static ResultSet getResultSet(Connection conn, String sql) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		return ps.executeQuery();
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

	public static Map<String, TableInfo> getTableInfo(Connection conn) throws Exception {
		Map<String, TableInfo> tableMap = new HashMap<String, TableInfo>();
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet tables = metaData.getTables(null, null, "%", new String[] { "TABLE" });
		// ResultSetMetaData resultSetMetaData = tables.getMetaData();
		// String column = "";
		// for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
		// column += resultSetMetaData.getColumnName(i) + "\t\t";
		// }
		// System.out.println(column);
		while (tables.next()) {
			TableInfo tableInfo = new TableInfo();
			// for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
			// System.out.print(tables.getString(i) + "\t\t");
			// }
			// System.out.println();
			String tabName = tables.getString("TABLE_NAME");
			// 列信息
			ResultSet coluResultSet = metaData.getColumns(null, null, tabName, null);
			tableInfo.setName(tabName);
			// ResultSetMetaData coluMetaData = coluResultSet.getMetaData();
			// String str = "";
			// for (int i = 1; i <= coluMetaData.getColumnCount(); i++) {
			// str += coluMetaData.getColumnName(i) + "\t\t";
			// }
			// System.out.println("----------" + tabName + "--------");
			// System.out.println(str);
			while (coluResultSet.next()) {
				// for (int i = 1; i <= coluMetaData.getColumnCount(); i++) {
				// System.out.print(coluResultSet.getString(i) + "\t\t");
				// }
				// System.out.println();
				String coluName = coluResultSet.getString("COLUMN_NAME");
				String type = coluResultSet.getString("TYPE_NAME").toLowerCase();
				Integer length = coluResultSet.getInt("COLUMN_SIZE");
				ColumnInfo columnInfo = new ColumnInfo();
				columnInfo.setLength(length);
				columnInfo.setName(coluName);
				columnInfo.setType(type);
				tableInfo.addColumnInfo(columnInfo);
			}

			// 主键信息
			ResultSet pkResultSet = metaData.getPrimaryKeys(null, null, tabName);
			// ResultSetMetaData pkMetaData = pkResultSet.getMetaData();
			// String str = "";
			// for (int i = 1; i <= pkMetaData.getColumnCount(); i++) {
			// str += pkMetaData.getColumnName(i) + "\t\t";
			// }
			// System.out.println("----------" + tabName + "--------");
			// System.out.println(str);

			while (pkResultSet.next()) {
				// for (int i = 1; i <= pkMetaData.getColumnCount(); i++) {
				// System.out.print(pkResultSet.getString(i) + "\t\t");
				// }
				// System.out.println();
				tableInfo.addPKInfo(pkResultSet.getString("PK_NAME"), pkResultSet.getString("COLUMN_NAME"));
			}
			// 外键信息
			ResultSet fkResultSet = metaData.getImportedKeys(null, null, tabName);
			// ResultSetMetaData fkMetaData = fkResultSet.getMetaData();
			// String str = "";
			// for (int i = 1; i <= fkMetaData.getColumnCount(); i++) {
			// str += fkMetaData.getColumnName(i) + "\t\t";
			// }
			// System.out.println("----------" + tabName + "--------");
			// System.out.println(str);

			while (fkResultSet.next()) {
				// for (int i = 1; i <= fkMetaData.getColumnCount(); i++) {
				// System.out.print(fkResultSet.getString(i) + "\t\t");
				// }
				// System.out.println();
				String pkTabName = fkResultSet.getString("PKTABLE_NAME");
				String pkColumnName = fkResultSet.getString("PKCOLUMN_NAME");
				String fkTabName = fkResultSet.getString("FKTABLE_NAME");
				String fkColumnName = fkResultSet.getString("FKCOLUMN_NAME");
				String fkName = fkResultSet.getString("FK_NAME");
				String pkName = fkResultSet.getString("PK_NAME");
				FKInfo fkInfo = new FKInfo();
				fkInfo.setFkColumnName(fkColumnName);
				fkInfo.setFkTabName(fkTabName);
				fkInfo.setName(fkName);
				fkInfo.setPkName(pkName);
				fkInfo.setPkColumnName(pkColumnName);
				fkInfo.setPkTabName(pkTabName);
				tableInfo.addFKInfo(fkInfo);
			}
			tableMap.put(tabName.toLowerCase(), tableInfo);
		}
		return tableMap;
	}

	/**
	 * 比较指定的table中的数据，以conn为标准
	 * 
	 * @param conn
	 * @param conn2
	 * @param tables
	 * @return
	 * @throws Exception
	 */
	public static List<InsertSql> createInsertSql(Connection conn, Connection conn2, List<String> tables) throws Exception {
		List<InsertSql> sqls = new ArrayList<InsertSql>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		Map<String, TableInfo> tabMap2 = getTableInfo(conn2);
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
							i++;
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

	public static List<String> simpleSqlQueryAndCloseConn(Connection conn, String sql) throws Exception {
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<String> result = new ArrayList<>();
		while (rs.next()) {
			result.add(rs.getString(1));
		}
		close(conn);
		return result;
	}

	public static void executeSimpleSqlsAndCloseConn(Connection conn, String... sqls) throws Exception {
		for (String sql : sqls) {
			conn.prepareStatement(sql).execute();
		}
		close(conn);
	}

	public static Connection getMysqlConnection(String ip, int port, String database, String userName, String password) throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf-8&treatUtilDateAsTimestamp";
		return getConnection(driver, url, userName, password);
	}
}

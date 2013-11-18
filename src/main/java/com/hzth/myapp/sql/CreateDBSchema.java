package com.hzth.myapp.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hzth.myapp.sql.model.ColumnInfo;
import com.hzth.myapp.sql.model.PKInfo;
import com.hzth.myapp.sql.model.TableInfo;

/**
 * 对比数据库，并生成sql补丁
 * 
 * @author tianyl
 * 
 */
public class CreateDBSchema {

	public static void main(String[] args) {
		// createDB();
		// showDatabases();
		// executeSQL();
		checkSchema();
		// printSql(null);
	}

	private static void checkSchema() {
		Connection conn = null;
		Connection conn2 = null;
		try {
			// 标准库
			conn = SqlHelper.getSqlServerConnection("127.0.0.1", "aa", "sa", "hzth-801");
			// 需对比的库
			conn2 = SqlHelper.getSqlServerConnection("127.0.0.1", "dc_empty", "sa", "hzth-801");
			Map<String, TableInfo> tableMap = SqlHelper.getTableInfo(conn);
			Map<String, TableInfo> tableMap2 = SqlHelper.getTableInfo(conn2);
			showTableDiff(tableMap, tableMap2);
			showColumnDiff(tableMap, tableMap2);
			showData(conn, conn2);
			// showPKDiff(tableMap, tableMap2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
			SqlHelper.close(conn2);
		}
	}

	private static void showData(Connection conn, Connection conn2) throws Exception {
		Map<String, TableInfo> tab1Map = SqlHelper.getTableInfo(conn);
		List<String> tables = new ArrayList<String>();
		for (String tab : tab1Map.keySet()) {
			// 不比对学期和学年
			if (tab.equals("bd_schoolterm") || tab.equals("bd_schoolyear")) {
				continue;
			}
			tables.add(tab);
		}
		List<InsertSql> sqls = SqlHelper.createInsertSql(conn, conn2, tables);
		List<String> sqlList = new ArrayList<String>();
		for (InsertSql sql : sqls) {
			sqlList.add(sql.getSql());
		}
		addSqlToFile(sqlList);
	}

	private static void showPKDiff(Map<String, TableInfo> tableMap, Map<String, TableInfo> tableMap2) {
		for (String key : tableMap.keySet()) {
			TableInfo tableInfo = tableMap.get(key);
			TableInfo tableInfo2 = tableMap2.get(key);
			if (tableInfo2 == null) {
				continue;
			}
			for (String pkName : tableInfo.getPkInfoMap().keySet()) {
				PKInfo pkInfo = tableInfo.getPkInfoMap().get(pkName);
				PKInfo pkInfo2 = tableInfo2.getPkInfoMap().get(pkName);
				if (pkInfo2 == null) {
					System.out.println("表" + key + "---不存在主键：" + pkName);
					continue;
				}
				if (pkInfo.getColumnNames().size() != pkInfo2.getColumnNames().size()) {
					System.out.println("表" + key + "---主键列数不一致：" + pkName + pkInfo.getColumnNames().size() + ":" + pkInfo2.getColumnNames().size());
				}
				for (String colName : pkInfo.getColumnNames()) {
					if (!pkInfo2.getColumnNames().contains(colName)) {
						System.out.println("表" + key + "---不存在主键列：" + colName);
					}
				}
			}
		}
	}

	private static void showColumnDiff(Map<String, TableInfo> tableMap, Map<String, TableInfo> tableMap2) {
		List<String> sqls = new ArrayList<String>();
		for (String key : tableMap.keySet()) {
			TableInfo tableInfo = tableMap.get(key);
			TableInfo tableInfo2 = tableMap2.get(key);
			if (tableInfo2 == null) {
				// System.out.println("---------------------");
				// System.out.println("不存在表：" + key);
				// System.out.println("---------------------");
				continue;
			}
			for (String colKey : tableInfo.getColumnInfoMap().keySet()) {
				ColumnInfo columnInfo = tableInfo.getColumnInfoMap().get(colKey);
				ColumnInfo columnInfo2 = tableInfo2.getColumnInfoMap().get(colKey);
				if (columnInfo2 == null) {
					// System.out.println("表" + key + "----不存在列：" + colKey + "----");
					// alter table aaa add name varchar(100);
					String sql = "alter table " + tableInfo.getName() + " add " + colKey + " ";
					if (columnInfo.getType().equals("char")) {
						sql += " char(" + columnInfo.getLength() + ")";
					} else if (columnInfo.getType().equals("varchar")) {
						sql += " varchar(" + columnInfo.getLength() + ")";
					} else if (columnInfo.getType().equals("text")) {
						sql += " text ";
					} else if (columnInfo.getType().equals("int")) {
						sql += " int ";
					} else if (columnInfo.getType().equals("bigint")) {
						sql += " bigint ";
					} else {
						System.out.println("错误列类型:" + columnInfo.getType());
					}
					sql += ";";
					sqls.add(sql);
					continue;
				}
				if (!columnInfo.getType().equals(columnInfo2.getType())) {
					// System.out.println("表" + key + "----列类型不一致：" + colKey + "----");
					String sql = "alter table " + tableInfo.getName() + " alter column " + colKey + " ";
					if (columnInfo.getType().equals("char")) {
						sql += " char(" + columnInfo.getLength() + ")";
					} else if (columnInfo.getType().equals("varchar")) {
						sql += " varchar(" + columnInfo.getLength() + ")";
					} else if (columnInfo.getType().equals("text")) {
						sql += " text ";
					} else if (columnInfo.getType().equals("int")) {
						sql += " int ";
					} else {
						System.out.println("错误列类型:" + columnInfo.getType());
					}
					sql += ";";
					sqls.add(sql);
				}
				if (!columnInfo.getType().equals("text")) {
					if (columnInfo.getLength() - columnInfo2.getLength() != 0) {
						// alter table <表名> alter column <字段名> 新类型名(长度)
						// System.out.println("表" + key + "----列长度不一致：" + colKey + "----" + columnInfo.getLength() + ":" + columnInfo2.getLength());
						String sql = "alter table " + tableInfo.getName() + " alter column " + colKey + " ";
						if (columnInfo.getType().equals("char")) {
							sql += " char(" + columnInfo.getLength() + ")";
						} else if (columnInfo.getType().equals("varchar")) {
							sql += " varchar(" + columnInfo.getLength() + ")";
						} else if (columnInfo.getType().equals("text")) {
							sql += " text ";
						} else if (columnInfo.getType().equals("int")) {
							sql += " int ";
						} else {
							System.out.println("错误列类型:" + columnInfo.getType());
						}
						sql += ";";
						sqls.add(sql);
					}
				}
			}
		}
		addSqlToFile(sqls);
	}

	private static void addSqlToFile(List<String> sqls) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(new File("E:/workspace3.7/myWebApp/src/main/java/com/hzth/myapp/sql/all2.sql"), true));
			for (String sql : sqls) {
				pw.println(sql);
				pw.println("GO");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	private static void showTableDiff(Map<String, TableInfo> tableMap, Map<String, TableInfo> tableMap2) {
		boolean tableFlag = true;
		if (tableMap.keySet().size() != tableMap2.keySet().size()) {
			System.out.println("table个数不同！！" + tableMap.keySet().size() + ":" + tableMap2.keySet().size());
			tableFlag = false;
		}

		List<String> notExistTab = new ArrayList<String>();
		for (String key : tableMap.keySet()) {
			if (!tableMap2.containsKey(key)) {
				notExistTab.add(key);
				System.out.println("不存在表：" + key);
				tableFlag = false;
			}
		}
		System.out.println(tableFlag ? "表相同" : "表不同");
		printSql(notExistTab);
	}

	private static void printSql(List<String> notExistTab) {
		File file = new File("E:/workspace3.7/myWebApp/src/main/java/com/hzth/myapp/sql/all.sql");
		BufferedReader br = null;

		List<String> createSql = new ArrayList<String>();
		List<String> alterSql = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String temp = null;
			String sql = "";
			while ((temp = br.readLine()) != null) {
				temp = temp.trim();
				if (StringUtils.isBlank(temp)) {
					continue;
				}
				if (temp.startsWith("/*") || temp.startsWith("--")) {
					continue;
				}
				if (temp.toLowerCase().startsWith("insert ")) {
					continue;
				}
				if (temp.toLowerCase().equals("go") || temp.substring(temp.length() - 1, temp.length()).equals(";")) {
					sql += " ;";
					sql = sql.trim();
					if (sql.toLowerCase().startsWith("alter table") || sql.toLowerCase().startsWith("create table")) {
						String tableName = sql.trim().split(" ")[2].toLowerCase();
						if (notExistTab.contains(tableName)) {
							if (sql.toLowerCase().startsWith("alter table")) {
								alterSql.add(sql);
							} else {
								createSql.add(sql);
							}
						}
					} else {
						if (!sql.equals(";")) {
							System.out.println("错误:" + sql);
						}
					}
					sql = "";
				} else {
					sql += " " + temp;
				}
			}
			addSqlToFile(createSql);
			addSqlToFile(alterSql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void executeSQL() {
		Connection conn = null;
		BufferedReader br = null;
		boolean flag = true;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "";
			url = "jdbc:sqlserver://192.168.1.122;database=aaa;sendStringParametersAsUnicode=false";
			String userName = "sa";
			String password = "hzth-801";
			conn = DriverManager.getConnection(url, userName, password);
			br = new BufferedReader(new InputStreamReader(CreateDBSchema.class.getClassLoader().getResourceAsStream("com/hzth/myapp/sql/sqlserver.sql")));
			String line = null;
			String sql = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("/*")) {

				} else if (line.startsWith("--")) {

				} else if (line.length() > 0) {
					if (line.endsWith(";")) {
						sql = addSqlStatementPiece(sql, line);
						Statement st = conn.createStatement();
						try {
							st.execute(sql);
							System.out.println("成功执行\n" + sql);
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("执行失败\n" + sql);
							flag = false;
						} finally {
							SqlHelper.close(st);
							sql = null;
						}
					} else {
						sql = addSqlStatementPiece(sql, line);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
		}
		System.out.println(flag ? "全部成功执行" : "有执行错误");
	}

	private static void showDatabases() {
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "";
			url = "jdbc:sqlserver://192.168.1.122;database=master;sendStringParametersAsUnicode=false";
			String userName = "sa";
			String password = "hzth-801";
			conn = DriverManager.getConnection(url, userName, password);
			String sql = "Select * FROM sys.databases ";
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(resultSet);
			SqlHelper.close(ps);
			SqlHelper.close(conn);
		}
	}

	private static String addSqlStatementPiece(String sql, String line) {
		if (sql == null) {
			return line;
		}
		return sql + " \n" + line;
	}
}

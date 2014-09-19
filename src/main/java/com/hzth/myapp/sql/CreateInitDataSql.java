package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hzth.myapp.core.util.UUID;

public class CreateInitDataSql {

	public static void main(String[] args) throws Exception {
		String moduleId = "20140529154228609942351339341682";
		Connection conn = null;
		Connection conn2 = null;
		// System.setOut(new PrintStream(new File("e:/a.txt")));
		try {
			System.out.println("-----start-----");
			// 有数据的连接
			conn = SqlHelper.getSqlServerConnection("192.168.1.8", "dc_bd_zxf", "sa", "hzth-801");
			// 标准库连接
			conn2 = SqlHelper.getSqlServerConnection("192.168.1.8", "dc_bd_zd", "sa", "hzth-801");

			// conn = SqlHelper.getMysqlConnection("192.168.1.8", "minicourse", "hzth", "hzth-801");
			// createModule(moduleId, conn);
			// createOperation(moduleId, conn);
			// createConfiguration(conn, conn2);
			// createDict(conn, conn2);
			// createAttachmentconfig(conn, conn2);
			// createOperate(conn, conn2);

			// System.setOut(new PrintStream("e:/sql.txt"));

			// List<String> tables = new ArrayList<String>();
			// tables.add("privilegeoperation");
			// tables.add("bd_user");
			// tables.add("bd_student");
			// createTable(tables, conn, conn2);

			// List<String> ids = new ArrayList<String>();
			// ids.add("20140728112911476065399671422353");
			// for (String id : ids) {
			// createOperationById(id, conn);
			// }

			// List<String> ids2 = new ArrayList<String>();
			// ids2.add("20140709145856678739868013273950");
			// for (String id : ids2) {
			// createDictById(id, conn);
			// }

			// List<String> ids21 = new ArrayList<String>();
			// ids21.add("20140715154650091505392415170085");
			// for (String id : ids21) {
			// createDictValueById(id, conn);
			// }

			// List<String> ids3 = new ArrayList<String>();
			// ids3.add("20140402162028066679241789032434");
			// for (String id : ids3) {
			// createAttachmentconfig(id, conn);
			// }

			List<String> ids4 = new ArrayList<String>();
			ids4.add("20140718161543673842220339504041");
			for (String id : ids4) {
				createConfiguration(id, conn);
			}

			// List<String> ids5 = new ArrayList<String>();
			// ids5.add("20130606144207905516375601145758");
			// for (String id : ids5) {
			// updateSqlWithTableAndId("fw_attachmentconfig", id, conn);
			// }

			// String sql = "select * from qn_optionresults where id_questionresults in (select qq.id from qn_questionresults qq where id_question='20140520135758215421565988632372')";
			// createInsertSql(conn, sql, "bd_dictionaryvalue");
			System.out.println("-----end-----");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
			SqlHelper.close(conn2);
		}
	}

	private static void createInsertSql(Connection conn, String sql, String table) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into " + table + "(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}

	private static void createDictValueById(String id, Connection conn) {
		createSqlWithTableAndId("bd_dictionaryvalue", id, conn);
	}

	private static void updateSqlWithTableAndId(String table, String id, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + table + " where id = '" + id + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "update " + table + " set ";
				for (String label : labelList) {
					if (label.equals("id") || label.equals("ft") || label.equals("lt") || label.equals("fu") || label.equals("lu")) {
						continue;
					}
					if (table.equals("bd_operation") && label.equalsIgnoreCase("customTypeDict")) {
						continue;
					}
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += label + "=" + intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += label + "=" + str + ",";
						} else {
							sql2 += label + "=" + "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += " where id = '" + id + "';";
				System.out.println(sql2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}

	private static void createConfiguration(String id, Connection conn) {
		createSqlWithTableAndId("bd_configuration", id, conn);
		createSqlWithTableAndForeignKey("bd_configurationitem", "id_configuration", id, conn);
	}

	private static void createSqlWithTableAndForeignKey(String table, String foreignKey, String value, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + table + " where " + foreignKey + " = '" + value + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into " + table + "(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}

	private static void createSqlWithTableAndId(String table, String id, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + table + " where id = '" + id + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into " + table + "(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}

	private static void createAttachmentconfig(String id, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from fw_attachmentconfig where id = '" + id + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into fw_attachmentconfig(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}

	private static void createDictById2(String id, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from bd_operation where operationDict = '0'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			int i = 1;
			while (rs.next()) {
				String sql2 = "insert into bd_dictionaryvalue(id,id_dictionary,id_parent,code,value,num,description,enableFlag,editableFlag,systemFlag,ft,lt,fu,lu,valueI18n) values('" + UUID.getUUID() + "','20130411174451826521334266052750',null,'" + rs.getString("code") + "','" + rs.getString("name") + "'," + i + ",null,'1',null,null,'2013-04-11 17:45:39','2013-04-14 12:39:42','20120507134700000110225236178825','20120507134700000110225236178825',null);";
				i++;
				System.out.println(sql2);
				// FileUtil.append("C:/Users/tianyl/Desktop/aa.sql", sql2 + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
		createDictValueByDictId(id, conn);
	}

	private static void createDictById(String id, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from bd_dictionary where id = '" + id + "' order by id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into bd_dictionary(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (label.toLowerCase().equals("customtypedict")) {// 判断是否为操作的分类，分类设为空
							str = null;
						}
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
				// FileUtil.append("C:/Users/tianyl/Desktop/aa.sql", sql2 + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
		createDictValueByDictId(id, conn);
	}

	private static void createDictValueByDictId(String id, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from bd_dictionaryvalue where id_dictionary = '" + id + "' order by id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into bd_dictionaryvalue(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (label.toLowerCase().equals("customtypedict")) {// 判断是否为操作的分类，分类设为空
							str = null;
						}
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
				// FileUtil.append("C:/Users/tianyl/Desktop/aa.sql", sql2 + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}

	private static void createTable(List<String> tables, Connection conn, Connection conn2) throws Exception {
		List<InsertSql> sqls = SqlHelper.createInsertSql(conn, conn2, tables);
		sqls = SqlHelper.sortInsertSql(sqls);
		for (InsertSql sql : sqls) {
			// FileUtil.append("C:/Users/tianyl/Desktop/aa.sql", sql.getSql() + "\n");
			System.out.println(sql.getSql());
		}
	}

	private static void createAttachmentconfig(Connection conn, Connection conn2) throws Exception {
		List<String> tables = new ArrayList<String>();
		tables.add("fw_attachmentconfig");
		List<InsertSql> sqls = SqlHelper.createInsertSql(conn, conn2, tables);
		for (InsertSql sql : sqls) {
			System.out.println(sql.getSql());
		}
	}

	private static void createOperate(Connection conn, Connection conn2) throws Exception {
		List<String> tables = new ArrayList<String>();
		tables.add("bd_operation");
		List<InsertSql> sqls = SqlHelper.createInsertSql(conn, conn2, tables);
		for (InsertSql sql : sqls) {
			System.out.println(sql.getSql());
		}
	}

	private static void createDict(Connection conn, Connection conn2) throws Exception {
		List<String> tables = new ArrayList<String>();
		tables.add("bd_dictionary");
		tables.add("bd_dictionaryvalue");
		List<InsertSql> sqls = SqlHelper.createInsertSql(conn, conn2, tables);
		for (InsertSql sql : sqls) {
			if (sql.getSql().contains("20130322135215828141748352412096")) {
				continue;
			}
			System.out.println(sql.getSql());
		}
	}

	private static void createConfiguration(Connection conn, Connection conn2) throws Exception {
		List<String> tables = new ArrayList<String>();
		tables.add("bd_configuration");
		tables.add("bd_configurationitem");
		// tables.add("bd_configurationresult");
		// tables.add("bd_configitem_configresult");
		List<InsertSql> sqls = SqlHelper.createInsertSql(conn, conn2, tables);
		for (InsertSql sql : sqls) {
			System.out.println(sql.getSql());
		}
	}

	private static void createOperationById(String id, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from bd_operation where id = '" + id + "' order by id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into bd_operation(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (label.toLowerCase().equals("customtypedict")) {// 判断是否为操作的分类，分类设为空
							str = null;
						}
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}

	private static void createOperation(String moduleId, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from bd_operation where id_module = '" + moduleId + "' order by id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into bd_operation(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (label.toLowerCase().equals("customtypedict")) {// 判断是否为操作的分类，分类设为空
							str = null;
						}
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持的数据类型：" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}

	private static void createModule(String id, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from bd_module where id = '" + id + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> labelList = new ArrayList<String>();
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			for (int col = 1; col <= metaData.getColumnCount(); col++) {
				String label = metaData.getColumnLabel(col);
				labelList.add(label);
				typeMap.put(label, metaData.getColumnType(col));
			}
			while (rs.next()) {
				String sql2 = "insert into bd_module(";
				for (String label : labelList) {
					sql2 += label + ",";
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ") values(";
				for (String label : labelList) {
					if (typeMap.get(label) - Types.INTEGER == 0) {// int型
						Integer intResult = rs.getInt(label);
						if (rs.wasNull()) {
							intResult = null;
						}
						sql2 += intResult + ",";
					} else if (typeMap.get(label) - Types.CHAR == 0 || typeMap.get(label) - Types.VARCHAR == 0) {// char varchar
						String str = rs.getString(label);
						if (rs.wasNull()) {
							str = null;
						}
						if (str == null) {
							sql2 += str + ",";
						} else {
							sql2 += "'" + str + "',";
						}
					} else {
						throw new RuntimeException("不支持类型:" + typeMap.get(label));
					}
				}
				sql2 = sql2.substring(0, sql2.length() - 1);
				sql2 += ");";
				System.out.println(sql2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
	}
}

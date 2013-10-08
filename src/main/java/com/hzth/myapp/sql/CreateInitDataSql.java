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

	public static void main(String[] args) {
		String moduleId = "20130410161947084607867596720976";
		Connection conn = null;
		Connection conn2 = null;
		try {
			System.out.println("-----start-----");
			// 有数据的连接
			conn = SqlHelper.getSqlServerConnection("192.168.1.8", "dc_rs", "sa", "hzth-801");
			// 标准库连接
			conn2 = SqlHelper.getSqlServerConnection("localhost", "dc_empty", "sa", "hzth-801");
			// createModule(moduleId, conn);
			// createOperation(moduleId, conn);
			// createConfiguration(conn, conn2);
			// createDict(conn, conn2);
			createAttachmentconfig(conn, conn2);
			// createOperate(conn, conn2);
			// List<String> tables = new ArrayList<String>();
			// tables.add("bd_studentregistration");
			// tables.add("ca_performanceitem");
			// tables.add("ca_classroomattendance");
			// tables.add("ca_performancekind");
			// tables.add("ca_attendancerecord");
			// tables.add("ca_attendanceitem");
			// tables.add("ca_leaverecord");
			// tables.add("");
			// tables.add("fw_attachmentsetting");
			// tables.add("fw_attachmentconfig");
			// createTable(tables, conn, conn2);
			// List<String> ids = new ArrayList<String>();
			// ids.add("20130916153938547871117212098093");
			// ids.add("20130916153617462845585711988674");
			// ids.add("20130912153017548520032644811539");
			// ids.add("20130912152344292987224912224968");
			// ids.add("20130912152223291258097621944094");
			// ids.add("20130912091527946345728051737439");
			// for (String id : ids) {
			// createOperationById(id, conn);
			// }
			List<String> ids2 = new ArrayList<String>();
			// ids2.add("20130325191038222741788749418279");
			for (String id : ids2) {
				createDictById(id, conn);
			}
			System.out.println("-----end-----");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
			SqlHelper.close(conn2);
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

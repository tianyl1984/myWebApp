package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hzth.myapp.core.util.FileUtil;
import com.hzth.myapp.sql.model.ColumnInfo;
import com.hzth.myapp.sql.model.FKInfo;
import com.hzth.myapp.sql.model.TableInfo;

//同步数据库，使两个库结构内容完全一致
public class SyncDatabase {

	private static final boolean opt_exe = true;// 执行
	private static final boolean opt_print = true;// 打印
	private static final boolean opt_save = false;// 保存到文件

	private static final boolean isDebug = false;

	private static final List<String> allSql = new ArrayList<String>();
	private static final List<String> notExistTabs = new ArrayList<String>();

	public static void main(String[] args) {
		Connection fromConn = null;
		Connection toConn = null;
		try {
			// 标准
			fromConn = SqlHelper.getSqlServerConnection("127.0.0.1", "dc_develop", "sa", "hzth-801");
			// 错误库
			toConn = SqlHelper.getSqlServerConnection("127.0.0.1", "dc_all", "sa", "hzth-801");
			// fromConn = SqlHelper.getMysqlConnection("192.168.1.8", "cydc", "", "hzth-801");

			// String driver = "com.mysql.jdbc.Driver";
			// fromConn = SqlHelper.getConnection(driver, "jdbc:mysql://" + "192.168.1.8" + ":3306/" + "cydc" + "?useUnicode=true&characterEncoding=utf-8", "hzth", "hzth-801");
			// toConn = SqlHelper.getConnection(driver, "jdbc:mysql://" + "192.168.1.8" + ":3308/" + "cydc" + "?useUnicode=true&characterEncoding=utf-8", "hzth", "hzth-801");

			Map<String, TableInfo> fromTableInfos = SqlHelper.getTableInfo(fromConn);
			Map<String, TableInfo> toTableInfos = SqlHelper.getTableInfo(toConn);
			// 对比table
			processTable(fromTableInfos, toTableInfos);
			// 对比列
			processColumns(fromTableInfos, toTableInfos);
			// 主键、外键
			processPKFK(fromTableInfos, toTableInfos);
			// 新增表的外键
			processNotExistTabFK(fromTableInfos, toTableInfos);
			// 数据
			processData(fromConn, toConn, fromTableInfos);
			// 处理sql
			processSql(toConn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(fromConn);
			SqlHelper.close(toConn);
		}
	}

	private static void processData(Connection fromConn, Connection toConn, Map<String, TableInfo> fromTableInfos) throws Exception {
		List<String> tables = new ArrayList<String>();
		for (String tab : fromTableInfos.keySet()) {
			// 不比对学期和学年
			if (tab.equals("bd_schoolterm") || tab.equals("bd_schoolyear")) {
				continue;
			}
			tables.add(tab);
		}
		List<InsertSql> sqls = SqlHelper.createInsertSql(fromConn, toConn, tables);
		for (InsertSql sql : sqls) {
			allSql.add(sql.getSql());
		}
	}

	private static void processNotExistTabFK(Map<String, TableInfo> fromTableInfos, Map<String, TableInfo> toTableInfos) {
		for (String tabName : notExistTabs) {
			TableInfo tableInfo = fromTableInfos.get(tabName);
			Map<String, FKInfo> fkFromMap = tableInfo.getFkInfoMap();
			for (FKInfo fk : fkFromMap.values()) {
				String sql = fk.getAddSql(fromTableInfos);
				allSql.add(sql);
			}
		}
	}

	private static void processPKFK(Map<String, TableInfo> fromTableInfos, Map<String, TableInfo> toTableInfos) {
		// 已有表的PK
		for (String name : fromTableInfos.keySet()) {
			if (toTableInfos.containsKey(name)) {
				// 不会增加表的主键，先忽略
			}
		}
		// 已有表的FK
		for (String name : fromTableInfos.keySet()) {
			if (toTableInfos.containsKey(name)) {
				// if (name.equals("ca_performanceitem")) {
				// System.out.println();
				// }
				TableInfo fromTable = fromTableInfos.get(name);
				TableInfo toTable = toTableInfos.get(name);
				Map<String, FKInfo> fkFromMap = fromTable.getFkInfoMap();
				Map<String, FKInfo> fkToMap = toTable.getFkInfoMap();
				for (String fkName : fkFromMap.keySet()) {
					FKInfo fromFK = fkFromMap.get(fkName);
					if (!fromFK.hasSameFK(fkToMap)) {// 没有外键
						String sql = fromFK.getAddSql(fromTableInfos);
						allSql.add(sql);
						continue;
					}
					// 外键修改，不处理
				}
			}
		}
	}

	private static void processColumns(Map<String, TableInfo> fromTableInfos, Map<String, TableInfo> toTableInfos) {
		for (String name : fromTableInfos.keySet()) {
			if (toTableInfos.containsKey(name)) {
				// Map<String, ColumnInfo> fromColumnInfo = fromTableInfos.get(name).getColumnInfoMap();
				Map<String, ColumnInfo> toColumnInfo = toTableInfos.get(name).getColumnInfoMap();
				List<ColumnInfo> fromColumnInfoList = fromTableInfos.get(name).getColumnInfoList();
				// List<ColumnInfo> toColumnInfoList = toTableInfos.get(name).getColumnInfoList();

				// if (name.equals("fw_attachment")) {
				// System.out.println();
				// }
				for (ColumnInfo column : fromColumnInfoList) {
					if (!toColumnInfo.containsKey(column.getName().toLowerCase())) {// 没有列
						String sql = column.getAddSql();
						allSql.add(sql);
						continue;
					}
					ColumnInfo toColumn = toColumnInfo.get(column.getName().toLowerCase());
					if (!column.equals(toColumn)) {
						String sql = column.getAlterSql();
						allSql.add(sql);
						continue;
					}
				}
			}
		}
	}

	private static void processSql(Connection toConn) {
		for (String sql : allSql) {
			if (opt_exe) {
				try {
					PreparedStatement ps = toConn.prepareStatement(sql);
					ps.execute();
				} catch (SQLException e) {
					e.printStackTrace();
					FileUtil.append("C:/Users/tianyl/Desktop/error.sql", sql + "\n");
				}
			}
			if (opt_print) {
				System.out.println(sql + ";");
			}
			if (opt_save) {
				FileUtil.append("C:/Users/tianyl/Desktop/sql.sql", sql + ";\n");
			}
		}
	}

	private static void processTable(Map<String, TableInfo> fromTableInfos, Map<String, TableInfo> toTableInfos) throws Exception {
		for (String name : fromTableInfos.keySet()) {
			if (!toTableInfos.containsKey(name)) {
				if (isDebug) {
					System.out.println("-----table " + name + " not exists!-----");
				}
				TableInfo table = fromTableInfos.get(name);
				allSql.add(table.getCreateSql());
				notExistTabs.add(name);
			}
		}
		// 处理新增table的主键，外键等处理数据之前添加。
		for (String name : notExistTabs) {
			TableInfo table = fromTableInfos.get(name);
			if (table.hasPK()) {
				List<String> sql = table.getPKSql();
				allSql.addAll(sql);
			}
		}
	}

}

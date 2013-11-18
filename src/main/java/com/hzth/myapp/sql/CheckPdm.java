package com.hzth.myapp.sql;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.hzth.myapp.sql.model.ColumnInfo;
import com.hzth.myapp.sql.model.CommonSql;

/**
 * 检查pdm文件是否合格
 * 
 * @author tianyl
 * 
 */
public class CheckPdm {

	private static final List<String> abbreviationList = new ArrayList<String>();
	static {
		abbreviationList.add("ac");
		abbreviationList.add("af");
		abbreviationList.add("bd");
		abbreviationList.add("ca");
		abbreviationList.add("cm");
		abbreviationList.add("ec");
		abbreviationList.add("er");
		abbreviationList.add("es");
		abbreviationList.add("ex");
		abbreviationList.add("fa");
		abbreviationList.add("fi");
		abbreviationList.add("fw");
		abbreviationList.add("ma");
		abbreviationList.add("mc");
		abbreviationList.add("me");
		abbreviationList.add("mo");
		abbreviationList.add("no");
		abbreviationList.add("ns");
		abbreviationList.add("od");
		abbreviationList.add("pm");
		abbreviationList.add("pu");
		abbreviationList.add("qn");
		abbreviationList.add("sa");
		abbreviationList.add("sd");
		abbreviationList.add("so");
		abbreviationList.add("ss");
		abbreviationList.add("tf");
		abbreviationList.add("tr");
		abbreviationList.add("wc");
		abbreviationList.add("ce");
		abbreviationList.add("pl");
		abbreviationList.add("ct");
		abbreviationList.add("ol");
		abbreviationList.add("al");
		abbreviationList.add("ps");
		abbreviationList.add("ta");
		abbreviationList.add("la");
		abbreviationList.add("mh");
		abbreviationList.add("bl");
		abbreviationList.add("fe");
		abbreviationList.add("sc");
		abbreviationList.add("re");
		abbreviationList.add("lu");
		abbreviationList.add("sr");
		abbreviationList.add("cc");
		abbreviationList.add("rs");
		abbreviationList.add("va");
		abbreviationList.add("am");
		abbreviationList.add("sm");
		abbreviationList.add("il");
		abbreviationList.add("im");
		abbreviationList.add("et");
		abbreviationList.add("wl");
		abbreviationList.add("cs");
		abbreviationList.add("wp");
		abbreviationList.add("wk");
		abbreviationList.add("th");
		abbreviationList.add("mt");
		abbreviationList.add("ho");
		abbreviationList.add("ne");

		// abbreviationList.add("om");
		// abbreviationList.add("da");
	}

	private static final List<String> supportColumnType = new ArrayList<String>();
	static {
		supportColumnType.add("int");
		supportColumnType.add("bigint");
		supportColumnType.add("char");
		supportColumnType.add("text");
		supportColumnType.add("clob");
		supportColumnType.add("integer");
		supportColumnType.add("varchar");
		supportColumnType.add("varchar2");
	}

	private static final List<String> forbiddenChars = new ArrayList<String>();
	static {
		forbiddenChars.add("[");
		forbiddenChars.add("]");
		forbiddenChars.add("'");
		forbiddenChars.add("\"");
		forbiddenChars.add("`");
	}

	private static final List<String> mysqlExclued = new ArrayList<String>();
	static {
		mysqlExclued.add("qn");
	}

	private static final List<String> oracleExclued = new ArrayList<String>();
	static {
		// oracleExclued.add("ec");
		// oracleExclued.add("ex");
		// oracleExclued.add("fa");
		// oracleExclued.add("so");
		// oracleExclued.add("tf");
		// oracleExclued.add("tr");
	}

	/**
	 * mysql包含的。为了提升效率
	 */
	private static final List<String> mysqlInclued = new ArrayList<String>();
	static {
		mysqlInclued.add("fw");
		mysqlInclued.add("bd");
		mysqlInclued.add("ma");
		// mysqlInclued.add("fa");
	}

	public static void main(String[] args) {
		String dbType = "sqlserver";
		dbType = "mysql";
		// dbType = "oracle";
		File file = new File("C:/Users/tianyl/Desktop/sql/dc-" + dbType + ".sql");
		List<String> errorMsgs = checkGrammar(file);
		if (errorMsgs.size() == 0) {
			execute(file, dbType);
		}
	}

	private static void execute(File file, String dbType) {
		String newdbName = "checkpdmdb";
		List<CommonSql> commonSqls = SqlParser.parse(file);
		createDatabase(dbType, newdbName);
		System.out.println("执行sql");
		Connection conn = null;
		String curSql = "";
		try {
			if (dbType.equals("sqlserver")) {
				conn = SqlHelper.getSqlServerConnection("127.0.0.1", newdbName, "sa", "hzth-801");
			} else if (dbType.equals("mysql")) {
				conn = SqlHelper.getMysqlConnection("127.0.0.1", newdbName, "root", "hzth-801");
			} else if (dbType.equals("oracle")) {

			} else {
				throw new RuntimeException("不支持的数据库类型：" + dbType);
			}
			for (CommonSql sql : commonSqls) {
				if (dbType.equals("mysql")) {
					boolean flag = false;
					// 不包含的
					for (String temp : mysqlExclued) {
						if (sql.getTable().startsWith(temp)) {
							flag = true;
							continue;
						}
					}
					if (flag) {
						continue;
					}

					flag = true;
					// 包含的
					for (String temp : mysqlInclued) {
						if (sql.getTable().startsWith(temp)) {
							flag = false;
							break;
						}
					}
					if (flag) {
						continue;
					}
				}
				curSql = sql.getSql();
				conn.prepareStatement(sql.getSql()).execute();
			}
		} catch (Exception e) {
			System.out.println(curSql);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				SqlHelper.close(conn);
			}
		}
	}

	private static void createDatabase(String dbType, String newdbName) {
		System.out.println("创建数据库");
		Connection conn = null;
		try {
			if (dbType.equals("sqlserver")) {
				conn = SqlHelper.getSqlServerConnection("127.0.0.1", "master", "sa", "hzth-801");
				conn.prepareStatement("create database " + newdbName).execute();
			} else if (dbType.equals("mysql")) {
				conn = SqlHelper.getMysqlConnection("127.0.0.1", "mysql", "root", "hzth-801");
				conn.prepareStatement("CREATE DATABASE " + newdbName + " default charset utf8 COLLATE utf8_general_ci").execute();
			} else if (dbType.equals("oracle")) {

			} else {
				throw new RuntimeException("不支持的数据库类型：" + dbType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				SqlHelper.close(conn);
			}
		}
	}

	private static List<String> checkGrammar(File file) {
		List<CommonSql> commonSqls = SqlParser.parse(file);
		List<String> errorMsg = new ArrayList<String>();
		List<String> modelNames = new ArrayList<String>();
		for (CommonSql sql : commonSqls) {
			String tableName = sql.getTable();
			if (sql.isCreate()) {
				if (!tableName.toLowerCase().equals(tableName)) {// 校验表名大小写，都必须小写
					errorMsg.add("表名大小写错误:" + tableName);
				}
				if (tableName.charAt(2) != '_') {// 表名是否有缩写
					errorMsg.add("表名无缩写:" + tableName);
				}
				String modelName = tableName.substring(3, tableName.length());
				if (modelNames.contains(modelName)) {// 重复表名
					errorMsg.add("表名重复:" + tableName);
				}
				modelNames.add(modelName);
				if (!abbreviationList.contains(tableName.subSequence(0, 2))) {// 缩写是否存在
					errorMsg.add("表名缩写不存在:" + tableName);
				}
				for (ColumnInfo colu : sql.getColumnInfos()) {
					if (!supportColumnType.contains(colu.getType())) {// 类型支持
						errorMsg.add("不支持的类型:" + tableName + ":" + colu.getName() + ":" + colu.getType());
					}
					for (String temp : forbiddenChars) {// 字段名称校验
						if (colu.getName().contains(temp)) {
							errorMsg.add("字段名称错误:" + tableName + ":" + colu.getName() + ":" + colu.getType());
						}
					}
					if (colu.getName().length() > 30) {// 字段长度
						errorMsg.add("字段长度错误:" + tableName + ":" + colu.getName() + ":" + colu.getName().length());
					}
				}
			}
		}
		for (String msg : errorMsg) {
			System.out.println(msg);
		}
		System.out.println("完成校验");
		return errorMsg;
	}
}
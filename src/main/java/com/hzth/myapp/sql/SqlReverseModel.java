package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class SqlReverseModel {

	private static final Set<String> ignoreNames = new HashSet<String>();
	static {
		ignoreNames.addAll(Arrays.asList("id", "version", "createDate", "updateDate"));
	}

	public static void main(String[] args) throws Exception {
		Connection conn = SqlHelper.getMysqlConnection("172.16.53.244", "zhongshang_loanpayment", "root", "ufenqi@123");
		String tabName = "zs_repayment_plan";
		ResultSet columnSet = conn.getMetaData().getColumns(null, "%", tabName, "%");
		Map<String, String> commentMap = new HashMap<String, String>();
		while (columnSet.next()) {
			commentMap.put(columnSet.getString("COLUMN_NAME").toLowerCase(), columnSet.getString("REMARKS"));
		}
		String sql = "select * from " + tabName;
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		System.out.println("-------------start------------");
		for (int i = 1; i <= colCount; i++) {
			String name = rsmd.getColumnLabel(i);
			if (ignoreNames.contains(name)) {
				continue;
			}
			String comment = commentMap.get(name.toLowerCase());
			int type = rsmd.getColumnType(i);
			String clazzType = getJavaType(type);
			System.out.println("private " + clazzType + " " + name + ";" + (StringUtils.isBlank(comment) ? "" : ("//"
					+ comment)));
			System.out.println();
		}
		System.out.println("--------------end-------------");
	}

	private static String getJavaType(int type) {
		if (type == Types.BIGINT) {
			return "Long";
		} else if (type == Types.VARCHAR) {
			return "String";
		} else if (type == Types.TIMESTAMP) {
			return "Date";
		} else if (type == Types.INTEGER) {
			return "Integer";
		} else if (type == Types.BIT) {
			return "Boolean";
		}
		return "unkown type:" + type;
	}

}

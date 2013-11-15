package com.hzth.myapp.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BatchExecuteDemo {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			conn = SqlHelper.getSqlServerConnection("192.168.1.8", "dc_wp", "sa", "hzth-801");
			// 获取所有数据库名
			ps = conn.prepareStatement("Select Name FROM Master..SysDatabases order BY Name");
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
			SqlHelper.close(rs);
			SqlHelper.close(ps);
		}
		File file = new File("C:/Users/tianyl/Desktop/base新增sql.txt");
		List<CommonSql> commonSqls = SqlParser.parse(file);
		for (String name : list) {
			if (!name.startsWith("dc")) {
				continue;
			}
			Connection tempConn = null;
			try {
				tempConn = SqlHelper.getSqlServerConnection("192.168.1.8", name, "sa", "hzth-801");
				for (CommonSql sql : commonSqls) {
					tempConn.prepareStatement(sql.getSql()).execute();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				SqlHelper.close(tempConn);
			}
		}
		System.out.println("完成。。。。。。");
	}
}

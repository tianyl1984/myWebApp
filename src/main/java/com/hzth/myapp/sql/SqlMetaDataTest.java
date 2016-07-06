package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SqlMetaDataTest {

	public static void main(String[] args) throws Exception {
		String sql = "select b.name as name1,f.name as fname,(select name from foo where id = f.id) as name3 from bar b left join foo f on f.id = b.id ";
		sql = "select b.name2 as name2 from (select '***' as name2 from bar c) as b";
		sql = "select *,b.name from bar b";
		Connection conn = SqlHelper.getMysqlConnection("172.16.20.163", "demo", "root", "ufenqi@321");
		ResultSet rs = conn.createStatement().executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int i = 0; i < columns; i++) {
			String label = rsmd.getColumnLabel(i + 1);
			String name = rsmd.getColumnName(i + 1);
			System.out.println(label + "," + name);
		}
	}

}

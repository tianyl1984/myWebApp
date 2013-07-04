package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SqlTest {

	public static void main(String[] args) {
		// Connection conn = null;
		Connection conn2 = null;
		int max = 0;
		try {
			// conn = SqlHelper.getSqlServerConnection("192.168.1.8", "dc_empty", "sa", "hzth-801");
			conn2 = SqlHelper.getSqlServerConnection("192.168.1.8", "cydc", "sa", "hzth-801");
			List<String> codes = new ArrayList<String>();
			// codes.add("studentKind");
			// codes.add("polity");
			// codes.add("colony");
			// codes.add("citizenKind");
			// codes.add("studymode");
			// codes.add("health");
			// codes.add("cultureDegree");
			// codes.add("relation");
			// codes.add("studentplace");
			codes.add("inschoolMode");
			codes.add("bloodKind");
			codes.add("studentSource");
			for (String temp : codes) {
				ResultSet rs = conn2.prepareStatement("select * from ac_dict where code = '" + temp + "'").executeQuery();
				if (!rs.next()) {
					throw new RuntimeException();
				}
				String id = rs.getString("id");
				String name2 = rs.getString("name");
				String code2 = rs.getString("code");
				String kind2 = rs.getString("kind");
				String sql1 = "insert into bd_dictionary(id,name,code,kind,editableFlag) values('" + id + "','" + name2 + "','" + code2 + "','" + kind2 + "','1');";
				System.out.println(sql1);
				ResultSet rs2 = conn2.prepareStatement("select * from ac_dictValue where id_dict = '" + id + "'").executeQuery();
				while (rs2.next()) {
					String value = rs2.getString("value");
					String code = rs2.getString("code");
					String id2 = rs2.getString("id");
					String parentId = rs2.getString("id_parent");
					String sql = "insert into bd_dictionaryvalue(id,id_dictionary,id_parent,code,value,enableFlag) values('" + id2 + "','" + id + "',";
					if (StringUtils.isBlank(parentId)) {
						sql += "null";
					} else {
						sql += "'" + parentId + "'";
					}
					sql += ",'" + code + "','" + value + "','1');";
					System.out.println(sql);
				}
			}
		} catch (Exception e) {
			System.out.println(max);
			e.printStackTrace();
		} finally {
			// SqlHelper.close(conn);
			SqlHelper.close(conn2);
		}
	}
}

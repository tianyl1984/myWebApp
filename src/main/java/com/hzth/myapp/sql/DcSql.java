package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hzth.myapp.core.util.FileUtil;
import com.hzth.myapp.core.util.MapUtil;
import com.hzth.myapp.core.util.UUID;

public class DcSql {
	public static void main(String[] args) {
		// Connection conn = null;
		Connection conn2 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> examDetailIds = new ArrayList<String>();
		try {
			// conn = SqlHelper.getSqlServerConnection("192.168.1.8", "dc_empty", "sa", "hzth-801");
			conn2 = SqlHelper.getSqlServerConnection("127.0.0.1", "nls2", "sa", "hzth-801");

			ps = conn2.prepareStatement("select id from ex_examdetail");
			rs = ps.executeQuery();
			while (rs.next()) {
				examDetailIds.add(rs.getString("id"));
			}
			int index = 0;
			for (String examDetailId : examDetailIds) {
				Map<String, List<String>> examSubjectMap = new HashMap<String, List<String>>();
				// Map<String, String> examSubjectIdMap = new HashMap<>();
				List<String> kpIds = new ArrayList<>();

				ps = conn2.prepareStatement("select eskp.id_examsubject,eskp.id_knowledgepoint from ex_examsubjectknowledgepoint eskp left join ex_examsubject es on es.id=eskp.id_examsubject where es.id_examdetail = '" + examDetailId + "'");
				rs = ps.executeQuery();
				while (rs.next()) {
					String examSubjectId = rs.getString("id_examsubject");
					String kpId = rs.getString("id_knowledgepoint");
					MapUtil.getListInMap(examSubjectMap, kpId).add(examSubjectId);
				}

				ps = conn2.prepareStatement("select distinct eskp.id_knowledgepoint from ex_examsubjectknowledgepoint eskp left join ex_examsubject es on es.id=eskp.id_examsubject where es.id_examdetail = '" + examDetailId + "'");
				rs = ps.executeQuery();
				while (rs.next()) {
					kpIds.add(rs.getString("id_knowledgepoint"));
				}

				// ps = conn2.prepareStatement("select id,id_examdetail from ex_examsubject");
				// rs = ps.executeQuery();
				// while (rs.next()) {
				// examSubjectIdMap.put(rs.getString("id"), rs.getString("id_examdetail"));
				// }

				// 创建知识点
				for (String kpId : kpIds) {
					String id = UUID.getUUID();
					String sql = "insert into ex_examknowledgepoint(id,id_examdetail,id_knowledgepoint,fu,ft) values('" + id + "','" + examDetailId + "','" + kpId + "','20120507134700000110225236178825','2013-11-20 12:12:12');";
					System.out.println(sql);
					FileUtil.append("C:/Users/tianyl/Desktop/牛栏山更新知识点补丁.sql", sql + "\r\n");
					// 创建关联
					List<String> examSubjectIds = MapUtil.getListInMap(examSubjectMap, kpId);
					for (String examSubjectId : examSubjectIds) {
						String sql2 = "insert into ex_examknowledgepointsubject(id_examknowledgepoint,id_examsubject) values('" + id + "','" + examSubjectId + "');";
						System.out.println(sql2);
						FileUtil.append("C:/Users/tianyl/Desktop/牛栏山更新知识点补丁.sql", sql2 + "\r\n");
						index++;
					}
				}
			}
			System.out.println(index);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// SqlHelper.close(conn);
			SqlHelper.close(conn2);
		}
	}
}

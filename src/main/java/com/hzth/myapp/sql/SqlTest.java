package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlTest {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select questionre0_.id_participatenaire as col_0_0_,questionre0_.id_question as col_1_0_, questionre0_.result as col_2_0_, optionresu1_.id_option as col_3_0_, option2_.alternativeFlag as col_4_0_ from qn_questionresults questionre0_ left outer join qn_optionresults optionresu1_ on questionre0_.id=optionresu1_.id_questionresults,qn_option option2_ cross join qn_participatenaire participat3_ cross join qn_question question4_ where optionresu1_.id_option=option2_.id and questionre0_.id_participatenaire=participat3_.id and questionre0_.id_question=question4_.id and 1=1 and participat3_.id_questionnaire='20131211110745937818063402469286' and question4_.kind<>'3' and participat3_.id_participant='20131014221308076642103222677922' order by question4_.num asc";
		try {
			conn = SqlHelper.getSqlServerConnection("192.168.30.123", "dc_qn2", "sa", "hzth-801");
			long t1 = System.currentTimeMillis();
			boolean readFlag = false;
			ps = conn.prepareStatement(sql);
			ps.setQueryTimeout(4);
			// System.out.println(ps.getQueryTimeout());
			rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {
				if (!readFlag) {
					System.out.println("开始读取结果：" + (System.currentTimeMillis() - t1));
					readFlag = true;
				}
				// Thread.sleep(5000);
				rs.getString("col_0_0_");
				rs.getString("col_1_0_");
				System.out.println(rs.getString("col_0_0_"));
				count++;
			}
			System.out.println("共读取行数：" + count);
			System.out.println("结束读取结果：" + (System.currentTimeMillis() - t1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
		}
	}
}

package com.hzth.myapp.dc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hzth.myapp.core.util.UUID;
import com.hzth.myapp.sql.SqlHelper;

public class LogUtil {

	public static void main(String[] args) throws Exception {
		saveAccLog();
	}

	private static void saveAccLog() throws Exception {
		BufferedReader br = null;
		Connection conn = SqlHelper.getSqlServerSaLocalConnection("dc_all");
		String sql = "insert into acc_log(id,userId,method,startTime,endTime,startTimeInt,endTimeInt) values(?,?,?,?,?,?,?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\tianyl\\Desktop\\dc.2014-06-16.log"))));
			String temp = null;
			int count = 0;
			while ((temp = br.readLine()) != null) {
				if (temp.contains("com.unitever.dc.qn.action.QuestionresultsAction - userId")) {
					String userId = temp.replaceFirst(".*userId:", "").replaceFirst(",method:.*$", "");
					String method = temp.replaceFirst(".*,method:", "").replaceFirst(",startTime:.*$", "");
					String startTime = temp.replaceFirst(".*,startTime:", "").replaceFirst(",endTime:.*$", "");
					String endTime = temp.replaceFirst(".*,endTime:", "");
					ps.setString(1, UUID.getUUID());
					ps.setString(2, userId);
					ps.setString(3, method);
					ps.setString(4, startTime);
					ps.setString(5, endTime);
					ps.setLong(6, Long.valueOf(startTime));
					ps.setLong(7, Long.valueOf(endTime));
					ps.addBatch();
					count++;
				}
			}
			ps.executeBatch();
			ps.close();
			conn.close();
			System.out.println("共添加数据：" + count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

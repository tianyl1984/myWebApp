package com.hzth.myapp.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlServerReIndex {

	private static List<HScore> scores = new ArrayList<HScore>();

	public static void main(String[] args) throws Exception {
		Connection conn2 = getConnection();
		select(conn2);
		SqlHelper.close(conn2);
		for (int i = 0; i < 100; i++) {
			Connection conn = getConnection();
			// delete(conn);
			// insert(conn);
			update(conn);
			SqlHelper.close(conn);
			// update(conn);
		}
	}

	private static void insert(Connection conn) throws Exception {
		String insertSql = "insert into ra_hsscore(id,id_dept,id_grade,id_eclass,id_tbaseinfo,id_course,id_sbaseinfo,id_exam,id_gradeexam,id_entryscoreteacher,gradeNum,score,firstPartScore,secondPartScore,status,absentFlag,ts,df) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		for (HScore score : scores) {
			System.out.println("--------插入-------");
			PreparedStatement ps = conn.prepareStatement(insertSql);
			ps.setString(1, score.getId());
			ps.setString(2, score.getDeptId());
			ps.setString(3, score.getGradeId());
			ps.setString(4, score.getEclassId());
			ps.setString(5, score.getTbaseinfoId());
			ps.setString(6, score.getCourseId());
			ps.setString(7, score.getSbaseinfoId());
			ps.setString(8, score.getExamId());
			ps.setString(9, score.getGradeExamId());
			ps.setString(10, score.getEntryscoreTeacherId());
			ps.setObject(11, score.getGradeNum());
			ps.setObject(12, score.getScore());
			ps.setObject(13, score.getFirstPartScore());
			ps.setObject(14, score.getSecondPartScore());
			ps.setString(15, score.getStatus());
			ps.setString(16, score.getAbsentFlag());
			ps.setString(17, score.getTs());
			ps.setString(18, score.getDf());
			ps.execute();
			SqlHelper.close(ps);
		}
	}

	private static void delete(Connection conn) throws Exception {
		String deleteSql = "delete from ra_hsscore where id = ?";
		for (HScore score : scores) {
			System.out.println("--------删除-------");
			PreparedStatement ps = conn.prepareStatement(deleteSql);
			ps.setString(1, score.getId());
			ps.execute();
			SqlHelper.close(ps);
		}
	}

	private static void select(Connection conn) throws Exception {
		String sql = "select * from ra_hsscore";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			HScore score = new HScore();
			score.setId(rs.getString("id"));
			score.setEclassId(rs.getString("id_eclass"));
			score.setGradeId(rs.getString("id_grade"));
			score.setSbaseinfoId(rs.getString("id_sbaseinfo"));
			score.setTbaseinfoId(rs.getString("id_tbaseinfo"));
			score.setAbsentFlag(rs.getString("absentFlag"));
			score.setCourseId(rs.getString("id_course"));
			score.setDeptId(rs.getString("id_dept"));
			score.setDf(rs.getString("df"));
			score.setEntryscoreTeacherId(rs.getString("id_entryscoreteacher"));
			score.setExamId(rs.getString("id_exam"));
			Object firstPartScore = rs.getObject("firstPartScore");
			score.setFirstPartScore(firstPartScore != null ? Integer.valueOf(firstPartScore.toString()) : null);
			score.setGradeExamId(rs.getString("id_gradeexam"));
			score.setGradeNum(rs.getInt("gradeNum"));
			Object scoreInt = rs.getObject("score");
			score.setScore(scoreInt != null ? Integer.valueOf(scoreInt.toString()) : null);
			Object secondPartScore = rs.getObject("secondPartScore");
			score.setSecondPartScore(secondPartScore != null ? Integer.valueOf(secondPartScore.toString()) : null);
			score.setStatus(rs.getString("status"));
			score.setTbaseinfoId(rs.getString("id_tbaseinfo"));
			score.setTs(rs.getString("ts"));
			scores.add(score);
		}
		System.out.println("完成查询");
		SqlHelper.close(rs);
		SqlHelper.close(ps);
	}

	private static void update(Connection conn) throws Exception {
		String updateSql = "update ra_hsscore set id_dept=?,id_grade=?,id_eclass=?,id_tbaseinfo=?,id_course=?,id_sbaseinfo=?,id_exam=?,id_gradeexam=?,id_entryscoreteacher=?,gradeNum=?,score=?,firstPartScore=?,secondPartScore=?,status=?,absentFlag=?,ts=?,df=? where id = ?";
		for (HScore score : scores) {
			PreparedStatement ps = conn.prepareStatement(updateSql);
			ps.setString(1, score.getDeptId());
			ps.setString(2, score.getGradeId());
			ps.setString(3, score.getEclassId());
			ps.setString(4, score.getTbaseinfoId());
			ps.setString(5, score.getCourseId());
			ps.setString(6, score.getSbaseinfoId());
			ps.setString(7, score.getExamId());
			ps.setString(8, score.getGradeExamId());
			ps.setString(9, score.getEntryscoreTeacherId());
			ps.setObject(10, score.getGradeNum());
			ps.setObject(11, score.getScore());
			ps.setObject(12, score.getFirstPartScore());
			ps.setObject(13, score.getSecondPartScore());
			ps.setString(14, score.getStatus());
			ps.setString(15, score.getAbsentFlag());
			ps.setString(16, score.getTs());
			ps.setString(17, score.getDf());
			ps.setString(18, score.getId());
			ps.execute();
			SqlHelper.close(ps);
			System.out.println("update:" + score.getId());
		}
	}

	private static Connection getConnection() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = "jdbc:sqlserver://192.168.1.122;database=cydc;sendStringParametersAsUnicode=false";
		String userName = "sa";
		String password = "hzth-801";
		return DriverManager.getConnection(url, userName, password);
	}
}

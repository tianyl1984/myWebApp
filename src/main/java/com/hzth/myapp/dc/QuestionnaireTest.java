package com.hzth.myapp.dc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hzth.myapp.sql.SqlHelper;
import com.hzth.myapp.web.NetUtil;

public class QuestionnaireTest {

	private static final String questionnaireId = "20140509105950474484817301339443";

	private static Map<String, String> participatenaireMap = new HashMap<>();

	private static List<Question> questions = new ArrayList<>();

	private static final String appUrl = "http://192.168.30.82:8092/dc-questionnaire/";

	public static void main(String[] args) throws Exception {
		clearData();
		// initUserIds();
		// initQuestions();
		// startAnswer();
	}

	private static void startAnswer() throws Exception {
		long t1 = System.currentTimeMillis();
		ExecutorService threadPool = Executors.newFixedThreadPool(50);
		for (final String key : participatenaireMap.keySet()) {
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					doAnswer(key);
				}
			});
		}

		threadPool.shutdown();
		threadPool.awaitTermination(100000, TimeUnit.DAYS);
		System.out.println("测试结束，共用时：" + (System.currentTimeMillis() - t1));
	}

	private static void doAnswer(String key) {
		String userId = participatenaireMap.get(key);
		System.out.println("开始答题：" + userId);
		@SuppressWarnings("unchecked")
		Map<String, ParameterValue> map = new LinkedHashMap();
		map.put("sys_auto_authenticate", new ParameterValue("true"));
		map.put("sys_username", new ParameterValue(userId));
		map.put("sys_password", new ParameterValue("000000"));
		map.put("dataSourceName", new ParameterValue("dataSource1"));
		map.put("questionnaireId", new ParameterValue(questionnaireId));
		map.put("participatenaireId", new ParameterValue(key));
		int index = 0;
		for (Question question : questions) {
			if (question.getKind().equals("0") || question.getKind().equals("1")) {
				map.put("questionResultVos[" + index + "].resultList[0].result", new ParameterValue(question.getOneOptionsId()));
				map.put("questionResultVos[" + index + "].resultList[0].question.id", new ParameterValue(question.getId()));
				map.put("questionResultVos[" + index + "].kind", new ParameterValue(question.getKind()));
			}
			if (question.getKind().equals("2")) {
				int optionIndex = 0;
				for (String optionId : question.getOptionIds()) {
					map.put("questionResultVos[" + index + "].resultList[" + optionIndex + "].result", new ParameterValue(question.getName()));
					map.put("questionResultVos[" + index + "].resultList[" + optionIndex + "].question.id", new ParameterValue(question.getId()));
					map.put("questionResultVos[" + index + "].resultList[" + optionIndex + "].option.id", new ParameterValue(optionId));
					optionIndex++;
				}
				map.put("questionResultVos[" + index + "].question.id", new ParameterValue(question.getId()));
				map.put("questionResultVos[" + index + "].kind", new ParameterValue(question.getKind()));
			}
			if (question.getKind().equals("3")) {
				map.put("questionResultVos[" + index + "].resultList[0].result", new ParameterValue("问答答案"));
				map.put("questionResultVos[" + index + "].resultList[0].question.id", new ParameterValue(question.getId()));
				map.put("questionResultVos[" + index + "].kind", new ParameterValue(question.getKind()));
			}
			if (question.getKind().equals("4") || question.getKind().equals("5")) {
				int optionIndex = 0;
				for (String childQuestionId : question.getChildrenQuestionIds()) {
					map.put("questionResultVos[" + index + "].resultList[" + optionIndex + "].result", new ParameterValue(question.getOneOptionsId()));
					map.put("questionResultVos[" + index + "].resultList[" + optionIndex + "].question.id", new ParameterValue(childQuestionId));
					optionIndex++;
				}
				map.put("questionResultVos[" + index + "].kind", new ParameterValue(question.getKind()));
			}
			if (question.getKind().equals("8")) {
				map.put("questionResultVos[" + index + "].kind", new ParameterValue(question.getKind()));
			}
			index++;
		}
		try {
			String cookie = DcUtil.getLoginCookie(appUrl, userId, "000000");
			String url0 = appUrl + "qn/questionnaire!index.action?" + getUserLogin(userId);
			NetUtil.getUrlResponse(url0, cookie);
			String url1 = appUrl + "component/i18n!ajaxGetI18nValues.action?bundleName=qn-questionnaire&" + getUserLogin(userId);
			NetUtil.getUrlResponse(url1, cookie);
			String url2 = appUrl + "qn/questionnaire!toParticipation.action?" + getUserLogin(userId);
			NetUtil.getUrlResponse(url2, cookie);
			String url3 = appUrl + "questionnaire/theme/default/css/dy.css?" + getUserLogin(userId);
			NetUtil.getUrlResponse(url3, cookie);
			String url4 = appUrl + "qn/questionnaire!toListOfParticipation.action?kind=&" + getUserLogin(userId);
			NetUtil.getUrlResponse(url4, cookie);
			String url5 = appUrl + "qn/questionnaire!listOfParticipation.action?kind=&" + getUserLogin(userId);
			NetUtil.getUrlResponse(url5, cookie);
			String url6 = appUrl + "qn/questionresults!ajaxGetOvertimeFlag.action?questionnaireId=" + questionnaireId + "&" + getUserLogin(userId);
			NetUtil.getUrlResponse(url6, cookie);
			String url7 = appUrl + "qn/questionresults!toparticipatein.action?questionnaireId=" + questionnaireId + "&" + getUserLogin(userId);
			NetUtil.getUrlResponse(url7, cookie);
			String url8 = appUrl + "qn/questionresults!participatein.action?questionnaireId=" + questionnaireId + "&" + getUserLogin(userId);
			NetUtil.getUrlResponse(url8, cookie);
			String url9 = appUrl + "questionnaire/jsp/ui/css/nu3_5.css?" + getUserLogin(userId);
			NetUtil.getUrlResponse(url9, cookie);
			System.out.println("完成查询：" + userId);

			String url = appUrl + "qn/questionresults!saveOrUpdateResult.action";
			String result = NetUtil.getUrlResponse(url, "", map, false);
			System.out.println("完成调查(添加)：" + result + ",用户:" + userId);
			result = NetUtil.getUrlResponse(url, "", map, false);
			System.out.println("完成调查(修改)：" + result + ",用户:" + userId);
			result = NetUtil.getUrlResponse(url, "", map, false);
			System.out.println("完成调查(修改)：" + result + ",用户:" + userId);
		} catch (Exception e) {
			System.out.println("error:" + userId);
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static String getUserLogin(String userId) {
		return "1=1";
		// return "sys_auto_authenticate=true&sys_username=" + userId + "&sys_password=000000&dataSourceName=dataSource1";
	}

	private static void initQuestions() throws Exception {
		Connection conn = SqlHelper.getSqlServerSaConnection("192.168.1.8", "tyl_qn");
		String sql = "select * from qn_question where id_questionnaire = '" + questionnaireId + "' and kind != '6' and kind != '7'";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		while (rs.next()) {
			String id = rs.getString("id");
			String name = rs.getString("name");
			String kind = rs.getString("kind");
			String parentId = rs.getString("id_parent");
			Question question = new Question();
			question.setId(id);
			question.setName(name);
			question.setKind(kind);
			question.setParentId(parentId);
			questions.add(question);

			String sql2 = "select * from qn_option where id_question = '" + id + "'";
			ResultSet rs2 = conn.prepareStatement(sql2).executeQuery();
			while (rs2.next()) {
				question.addOptionId(rs2.getString("id"));
			}

			if (kind.equals("4") || kind.equals("5")) {
				String sql3 = "select * from qn_question where id_parent='" + id + "'";
				ResultSet rs3 = conn.prepareStatement(sql3).executeQuery();
				while (rs3.next()) {
					question.addChildrenQuestionId(rs3.getString("id"));
				}
			}
		}
		SqlHelper.close(conn);
	}

	private static void initUserIds() throws Exception {
		System.out.println("开始加载用户");
		Connection conn = SqlHelper.getSqlServerSaConnection("192.168.1.8", "tyl_qn");
		String sql = "select q.id as id,u.loginname loginname from qn_participatenaire q left join bd_user u on q.id_participant=u.id where id_questionnaire = '" + questionnaireId + "'";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		while (rs.next()) {
			participatenaireMap.put(rs.getString("id"), rs.getString("loginname"));
		}
		System.out.println("完成加载用户");
	}

	private static void clearData() throws Exception {
		System.out.println("开始清理");
		Connection conn = SqlHelper.getSqlServerSaConnection("192.168.1.8", "tyl_qn");
		String sql0 = "delete from qn_optionresults where id_questionresults in(select id from qn_questionresults where id_question in (select id from qn_question where id_questionnaire='" + questionnaireId + "'))";
		String sql1 = "delete from qn_questionresults where id_question in (select id from qn_question where id_questionnaire='" + questionnaireId + "')";
		SqlHelper.executeSimpleSqlsAndCloseConn(conn, sql0, sql1);
		System.out.println("清理完成");
	}
}

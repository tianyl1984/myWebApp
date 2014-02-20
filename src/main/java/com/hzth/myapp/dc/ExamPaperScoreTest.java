package com.hzth.myapp.dc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hzth.myapp.core.util.StringUtil;
import com.hzth.myapp.web.NetUtil;

public class ExamPaperScoreTest {

	private static final String app_url = "http://127.0.0.1:8090/dc-exam";
	private static final String app_url2 = "http://127.0.0.1:8090/dc-exam";
	private static final String examDetailId = "20140123103705244839054627219921";
	private static final String param_validate = "&sys_auto_authenticate=true&dataSourceName=dataSource1&sys_username=baimei&sys_password=000000";
	private static final String url_eclass = app_url2 + "/ex/subjectScore!ajaxGetEclass.action?examDetailId=" + examDetailId + param_validate;
	private static final String url_examStudent = app_url2 + "/ex/subjectScore!ajaxGetExamStudentId.action?examDetailId=" + examDetailId + param_validate + "&eclassId=";
	private static final String url_examSubject = app_url2 + "/ex/subjectScore!ajaxGetExamSubjectId.action?examDetailId=" + examDetailId + param_validate;
	private static final String url_examStudentScoreInfo = app_url + "/ex/subjectScore!loadExamStudent.action?examDetailId=" + examDetailId + param_validate + "&locationId=";
	private static final String url_saveLostScore = app_url + "/ex/subjectScore!saveSubjectScore.action?enterType=0" + param_validate;
	private static final String url_test = app_url + "/ex/subjectScore!ajaxTest.action?enterType=0" + param_validate;
	private static final String url_testJsp = app_url + "/ex/subjectScore!testJsp.action?enterType=0" + param_validate;
	private static final String url_checkStatus = app_url + "/ex/subjectScore!toSelectInputLocation.action?examDetailId=" + examDetailId + param_validate;
	private static final String url_toEnterHaveScore = app_url + "/ex/subjectScore!toEnterHaveScore.action?examDetailId=" + examDetailId + "&enterType=0&status=0" + param_validate + "&examStudentId=";

	public static void main(String[] args) throws Exception {
		// m1();
		m2();
		// System.out.println(NetUtil.getUrlResponse(url_toEnterHaveScore));
	}

	private static void m2() throws Exception {
		String eclassIds = NetUtil.getUrlResponse(url_eclass, null, null, true);
		final Map<String, String> examStuIdMap = new HashMap<>();
		for (String eclassId : StringUtil.getListInStr(eclassIds)) {
			String examStuIds = NetUtil.getUrlResponse(url_examStudent + eclassId, null, null, true);
			examStuIdMap.put(eclassId, examStuIds);
		}
		final String examSubjectIds = NetUtil.getUrlResponse(url_examSubject, null, null, true);

		int threadCount = 80;
		// search(examStuIdMap, examSubjectIds, threadCount);
		save(examStuIdMap, examSubjectIds, threadCount);
	}

	private static void search(final Map<String, String> examStuIdMap, final String examSubjectIds, final int threadCount) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				long t1 = System.currentTimeMillis();
				ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
				for (final String eclassId : examStuIdMap.keySet()) {
					for (final String examStuId : StringUtil.getListInStr(examStuIdMap.get(eclassId))) {
						threadPool.submit(new Runnable() {
							@Override
							public void run() {
								try {
									long t1 = System.currentTimeMillis();
									System.out.println("开始查询-------->>>>>>");
									NetUtil.getUrlResponse(url_examStudentScoreInfo + eclassId);
									NetUtil.getUrlResponse(url_checkStatus);
									System.out.println("完成查询-------->>>>>>" + (System.currentTimeMillis() - t1));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
				threadPool.shutdown();
				try {
					threadPool.awaitTermination(1000, TimeUnit.DAYS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("查询共用时:" + (System.currentTimeMillis() - t1));
			}
		}).start();
	}

	private static void save(final Map<String, String> examStuIdMap, final String examSubjectIds, final int threadCount) {
		// 几个人录同一人分数
		final int personCount = 10;
		new Thread(new Runnable() {
			@Override
			public void run() {
				long t1 = System.currentTimeMillis();
				ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
				for (final String eclassId : examStuIdMap.keySet()) {
					for (final String examStuId : StringUtil.getListInStr(examStuIdMap.get(eclassId))) {
						for (int i = 0; i < personCount; i++) {
							threadPool.submit(new Runnable() {
								@Override
								public void run() {
									try {
										long t1 = System.currentTimeMillis();
										System.out.println("保存<<<<--------------------");
										NetUtil.getUrlResponse(url_toEnterHaveScore + examStuId);
										// if (new Random().nextBoolean()) {
										saveLostScore(examStuId, examSubjectIds);
										// }
										System.out.println("完成保存>>>>--------------------" + (System.currentTimeMillis() - t1));
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}
					}
				}
				threadPool.shutdown();
				try {
					threadPool.awaitTermination(1000, TimeUnit.DAYS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("共用时:" + (System.currentTimeMillis() - t1));
			}
		}).start();
	}

	private static void m1() {
		// test();
		// testJsp();
	}

	private static void testJsp() throws Exception {
		// for (int i = 0; i < 5; i++) {
		long t1 = System.currentTimeMillis();
		String result = NetUtil.getUrlResponse(url_testJsp, null, null, true);
		System.out.println("testJsp()用时：" + (System.currentTimeMillis() - t1));
		// }
	}

	private static void test() throws Exception {
		// for (int i = 0; i < 5; i++) {
		long t1 = System.currentTimeMillis();
		String times = NetUtil.getUrlResponse(url_test, null, null, true);
		System.out.println("test()用时：" + (System.currentTimeMillis() - t1));
		// }
	}

	private static void saveLostScore(String examStuId, String examSubjectIds) throws Exception {
		Map<String, ParameterValue> map = new HashMap<>();
		map.put("examDetailId", new ParameterValue(examDetailId));
		map.put("examStudentId", new ParameterValue(examStuId));
		List<String> subjectIds = StringUtil.getListInStr(examSubjectIds);
		int i = 0;
		for (String str : subjectIds) {
			map.put("enterScoreList[" + i + "].examSubject.id", new ParameterValue(str));
			map.put("enterScoreList[" + i + "].scoreStr", new ParameterValue(new Random().nextBoolean() ? "1" : ""));
			i++;
		}
		String result = NetUtil.getUrlResponse(url_saveLostScore, null, map, false);
		System.out.println(result);
	}
}

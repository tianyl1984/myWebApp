package com.hzth.myapp.dc;

public class MyTest {

	public static void main(String[] args) throws Exception {
		String baseUrl = "http://127.0.0.1:8094/dc-exam";
		System.out.println(DcUtil.getSchoolInfoStr(baseUrl));
		// System.out.println(DcUtil.getCookies());
		System.out.println(DcUtil.login(baseUrl, "administrator", "000000", "dataSource1"));
		// System.out.println(DcUtil.getCookies());
		System.out.println(DcUtil.getUrlResponse(baseUrl + "/ex/exam!ajaxGetExam.action"));
		// System.out.println(DcUtil.getCookies());
		// DcUtil.logout(baseUrl);
		System.out.println(DcUtil.login(baseUrl, "administrator", "000000", "dataSource2"));
		System.out.println(DcUtil.getUrlResponse(baseUrl + "/ex/exam!ajaxGetExam.action"));
		// DcUtil.logout(baseUrl);
		// System.out.println(DcUtil.getUrlResponse(baseUrl + "/ex/exam!ajaxGetExam.action"));
	}
}

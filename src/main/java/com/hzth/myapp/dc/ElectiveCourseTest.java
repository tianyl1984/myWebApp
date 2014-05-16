package com.hzth.myapp.dc;

import java.sql.Connection;
import java.util.List;

import com.hzth.myapp.sql.SqlHelper;
import com.hzth.myapp.web.NetUtil;

public class ElectiveCourseTest {

	private static final String dcUrl = "http://192.168.30.123:8092/dc-electivecourse";

	private static final String adminAccount = "&sys_auto_authenticate=true&sys_username=administrator&sys_password=000000&dataSourceName=dataSource1";

	private static final String url_cancel_courseResult = dcUrl + "/ec/ecActivityCourse!changeStatus.action?_=1" + adminAccount + "&id=";

	public static void main(String[] args) throws Exception {
		reset();
	}

	private static void reset() throws Exception {
		Connection conn = SqlHelper.getSqlServerSaConnection("192.168.30.123", "nls2");
		List<String> ids = SqlHelper.executeSimpleSqlAndCloseConn(conn, "select id from ec_ecactivitycourse where id_ecactivity = '20140423101715509984110931875282'");
		System.out.println("开始撤销。。。");
		for (String id : ids) {
			System.out.println(id);
			NetUtil.getUrlResponse(url_cancel_courseResult + id);
		}
		System.out.println("开始恢复。。。");
		for (String id : ids) {
			System.out.println(id);
			NetUtil.getUrlResponse(url_cancel_courseResult + id);
		}
	}
}

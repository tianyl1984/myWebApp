package com.hzth.myapp.dc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hzth.myapp.core.util.StringUtil;
import com.hzth.myapp.sql.SqlHelper;
import com.hzth.myapp.sql.Student;
import com.hzth.myapp.web.NetUtil;

public class ElectiveCourseTest {

	private static final String dcUrl = "http://192.168.30.123:8092/dc-electivecourse";

	private static final String adminAccount = "&sys_auto_authenticate=true&sys_username=administrator&sys_password=000000&dataSourceName=dataSource1";

	private static final String url_cancel_courseResult = dcUrl + "/ec/ecActivityCourse!changeStatus.action?_=1" + adminAccount + "&id=";

	private static List<Student> students = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		reset();
		initStudent();
		// for (Student stu : students) {
		// startSelectCourse(stu.getLoginName(), stu.getId());
		// }

		ExecutorService threadPool = Executors.newFixedThreadPool(50);

		for (Student stu : students) {
			final String userName = stu.getLoginName();
			final String id = stu.getId();
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						startSelectCourse(userName, id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		}
		threadPool.shutdown();
		threadPool.awaitTermination(100000, TimeUnit.DAYS);
	}

	private static void startSelectCourse(String userName, String userId) throws Exception {
		System.out.println("开始选课：" + userName);
		long t1 = System.currentTimeMillis();
		String indexUrl = dcUrl + "/ec/ecStudentCourse!studentElectiveCourseIndex.action?1=1" + getAccount(userName);
		String indexResult = NetUtil.getUrlResponse(indexUrl);
		// System.out.println(indexResult);
		StringUtil.noop(indexResult);
		String toElectiveCourseNote = dcUrl + "/ec/ecStudentCourse!toElectiveCourseNote.action?_=1401172401332" + getAccount(userName);
		String toElectiveCourseNoteResult = NetUtil.getUrlResponse(toElectiveCourseNote);
		// System.out.println(toElectiveCourseNoteResult);
		StringUtil.noop(toElectiveCourseNoteResult);
		String toElectiveCourse = dcUrl + "/ec/ecStudentCourse!toElectiveCourse.action?ecActivityId=20140423101715509984110931875282&userId=" + userId + "&preview=&_=1401172401399" + getAccount(userName);
		String toElectiveCourseResult = NetUtil.getUrlResponse(toElectiveCourse);
		// System.out.println(toElectiveCourseResult);
		StringUtil.noop(toElectiveCourseResult);
		String saveElectiveCourse = dcUrl + "/ec/ecStudentCourse!saveElectiveCourse.action";
		String param = "courseIds=20140425194634897630180509918670&ecActivityId=20140423101715509984110931875282&alternativeCourse1=20140429144908787830160373543076&alternativeCourse2=";
		param += getAccount(userName);
		String saveElectiveCourseResult = NetUtil.getUrlResponse(saveElectiveCourse, param, false);
		System.out.println(saveElectiveCourseResult);
		StringUtil.noop(saveElectiveCourseResult);

		String toMyElectiveCourse = dcUrl + "/ec/ecStudentCourse!toMyElectiveCourse.action?_=1401173358824" + getAccount(userName);
		String toMyElectiveCourseResult = NetUtil.getUrlResponse(toMyElectiveCourse);
		// System.out.println(toMyElectiveCourseResult);
		StringUtil.noop(toMyElectiveCourseResult);
		System.out.println("结束选课：" + (System.currentTimeMillis() - t1) + "\t" + userName);
	}

	private static String getAccount(String userName) {
		return "&sys_auto_authenticate=true&sys_username=" + userName + "&sys_password=000000&dataSourceName=dataSource1";
	}

	private static void initStudent() throws Exception {
		Connection conn = SqlHelper.getSqlServerSaConnection("127.0.0.1", "nls2");
		String sql = "select id,name,loginName from bd_user where id in(select id from bd_student where id_eclass in(select id from bd_eclass where  id_schoolterm = '20140210141150718005103668479430' and id_school = '20130407173129953596110370600586' and id_grade = '20130407175932436670413298611305' and graduatedFlag = '0' and enableFlag = '1' and character2Kind = '0'));";
		ResultSet rs = SqlHelper.getResultSet(conn, sql);
		while (rs.next()) {
			String id = rs.getString("id");
			String loginName = rs.getString("loginName");
			Student stu = new Student();
			stu.setId(id);
			stu.setLoginName(loginName);
			students.add(stu);
		}
		SqlHelper.close(conn);
	}

	private static void reset() throws Exception {
		Connection conn = SqlHelper.getSqlServerSaConnection("192.168.30.123", "nls2");
		List<String> ids = SqlHelper.simpleSqlQueryAndCloseConn(conn, "select id from ec_ecactivitycourse where id_ecactivity = '20140423101715509984110931875282'");
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

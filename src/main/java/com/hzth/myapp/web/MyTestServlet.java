package com.hzth.myapp.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzth.myapp.core.util.WebUtil;

public class MyTestServlet extends HttpServlet {

	private static final long serialVersionUID = 620897380008527484L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("doGet");
		doBusiness(req, resp);
		// WebUtil.printHeader(req);
		// WebUtil.printParameter(req);
		// WebUtil.printSession(req.getSession());
		// System.out.println(Thread.currentThread().getName());
		// ThreadLocalHolder.set("aaaa");
		// System.out.println(ThreadLocalHolder.get());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("doPost");
		doBusiness(req, resp);
		WebUtil.printHeader(req);
	}

	private void doBusiness(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		String json = "[{'name':'datasource1','school':'学校1','driverClassName':'com.microsoft.sqlserver.jdbc.SQLServerDriver','url':'jdbc:sqlserver://192.168.1.122:1433;database=dc;sendStringParametersAsUnicode=false','username':'sa','password':'hzth-801'},{'name':'datasource2','school':'学校2','driverClassName':'com.microsoft.sqlserver.jdbc.SQLServerDriver','url':'jdbc:sqlserver://192.168.1.122:1433;database=dc;sendStringParametersAsUnicode=false','username':'sa','password':'hzth-801'},{'name':'datasource3','school':'学校3','driverClassName':'com.microsoft.sqlserver.jdbc.SQLServerDriver','url':'jdbc:sqlserver://192.168.1.122:1433;database=dc;sendStringParametersAsUnicode=false','username':'sa','password':'hzth-801'}]";
		pw.write(json);
		// pw.write("[\"carNum\",false,\"不可以使用\"]");
		pw.flush();
	}
}

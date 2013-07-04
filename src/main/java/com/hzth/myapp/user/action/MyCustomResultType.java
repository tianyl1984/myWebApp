package com.hzth.myapp.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class MyCustomResultType implements Result {

	private static final long serialVersionUID = 1L;

	private String param1;
	private String param2;

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		final PrintWriter out = response.getWriter();
		out.print("中文");
		out.close();
	}

}

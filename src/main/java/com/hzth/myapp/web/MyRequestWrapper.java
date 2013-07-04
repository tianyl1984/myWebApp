package com.hzth.myapp.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequestWrapper extends HttpServletRequestWrapper {

	public MyRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		if (path.indexOf("aaa.jsp") != -1) {
			return super.getRequestDispatcher("bbb.jsp");
		}
		return super.getRequestDispatcher(path);
	}
}

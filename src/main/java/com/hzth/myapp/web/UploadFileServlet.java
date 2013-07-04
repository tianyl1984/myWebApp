package com.hzth.myapp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzth.myapp.core.util.WebUtil;

public class UploadFileServlet extends HttpServlet {

	private static final long serialVersionUID = 6302411909482993555L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebUtil.printHeader(req);
		WebUtil.printParameter(req);
		WebUtil.printStream(req);
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write("<html>OK!<a href='javascript:history.go(-1)'>back</a></html>");
	};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}

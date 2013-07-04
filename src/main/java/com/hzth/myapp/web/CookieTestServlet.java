package com.hzth.myapp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieTestServlet extends HttpServlet {

	private static final long serialVersionUID = -3847519199015303882L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie cookie = new Cookie("ckey1", "cookievalue1");
		cookie.setMaxAge(10000);
		cookie.setSecure(true);
		Cookie cookie2 = new Cookie("ckey2", "cookievalue2");
		cookie2.setMaxAge(10000);
		resp.addCookie(cookie);
		resp.addCookie(cookie2);
		resp.addHeader("Set-Cookie", "ckey3=cookievalue3; Expires=Fri, 19-Oct-2012 08:12:48 GMT;HTTPOnly");
		req.getRequestDispatcher("/jsp/cookie.jsp").forward(req, resp);
	}
}

package com.hzth.myapp.springmvc.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hzth.myapp.springmvc.filter.RequestAndResponseHolder;

public class CommonController {

	public HttpServletRequest getRequest() {
		return RequestAndResponseHolder.getRequest();
	}

	public HttpServletResponse getResponse() {
		return RequestAndResponseHolder.getResponse();
	}

	public HttpSession getSession() {
		return getRequest().getSession(true);
	}

	public void print(String str) {
		try {
			this.getResponse().getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

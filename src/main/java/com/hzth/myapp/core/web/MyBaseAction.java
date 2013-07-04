package com.hzth.myapp.core.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class MyBaseAction extends ActionSupport {

	private static final long serialVersionUID = 9184118904400073619L;

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public void print(String str) {
		try {
			PrintWriter pw = ServletActionContext.getResponse().getWriter();
			pw.print(str);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

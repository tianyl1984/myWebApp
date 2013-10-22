package com.hzth.myapp.dc;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ThemeAutoSwitchFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String filePath = req.getRequestURI().replace(req.getContextPath(), "");
		String realPath = req.getRealPath("/");
		File file = new File(realPath + filePath);
		if (!file.exists() && filePath.contains("/theme/")) {// 不存在
			String str0 = filePath.substring(0, filePath.indexOf("/theme/")) + "/theme/";
			String temp = filePath.replace(str0, "");
			String str1 = temp.substring(temp.indexOf("/"));
			req.getRequestDispatcher(str0 + "default" + str1).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}

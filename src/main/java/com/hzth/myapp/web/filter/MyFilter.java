package com.hzth.myapp.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.hzth.myapp.spring.MyContextHolder;

public class MyFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// System.out.println("MyFilter");
		HttpServletRequest request = (HttpServletRequest) req;
		String userTpye = request.getParameter("userType");

		String aa = request.getQueryString();

		if (StringUtils.isNotBlank(userTpye)) {
			request.getSession().setAttribute("userType", userTpye);
		}
		Object ut = request.getSession().getAttribute("userType");
		MyContextHolder.setUserType(ut == null ? "" : ut.toString());
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

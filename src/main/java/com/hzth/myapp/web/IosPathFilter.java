package com.hzth.myapp.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class IosPathFilter implements javax.servlet.Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String agent = req.getHeader("USER-AGENT");
		if (agent == null) {
			agent = "";
		}
		//		System.out.println(agent);
		if (StringUtils.isNotBlank(req.getParameter("fromIpad"))) {
			System.out.println("ipad");
			//			HttpServletResponse rep = (HttpServletResponse) response;
			//			rep.sendRedirect("/bbb.jsp");
			chain.doFilter(new MyRequestWrapper(req), response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}

package com.hzth.myapp.springmvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestAndResponseHolderFilter implements Filter {

	@Override
	public void destroy() {
		// System.out.println("Filter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		RequestAndResponseHolder.setRequest((HttpServletRequest) request);
		RequestAndResponseHolder.setResponse((HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// System.out.println("Filter init");
	}

}

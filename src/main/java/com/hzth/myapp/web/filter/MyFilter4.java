package com.hzth.myapp.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
// @WebFilter(filterName = "aaa", urlPatterns = "/*")
public class MyFilter4 extends CustomFilter {

	public MyFilter4() {
		System.out.println("MyFilter4 Init");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// System.out.println("MyFilter4");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}

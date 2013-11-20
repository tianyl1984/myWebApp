package com.hzth.myapp.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
public class MyFilter3 extends CustomFilter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFilter3");
		chain.doFilter(request, response);
		// HttpServletRequest req = (HttpServletRequest) request;
		// req.getRequestDispatcher("/swfUploadServlet").forward(request, response);
		// HttpServletResponse res = (HttpServletResponse) response;
		// res.sendRedirect("http://www.baidu.com");
	}

	@Override
	public void destroy() {

	}

}

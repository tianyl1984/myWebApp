package com.hzth.myapp.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
public class MyFilter2 extends CustomFilter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFilter2");
		// HttpServletRequest request = (HttpServletRequest) req;
		// String userTpye = request.getParameter("userType");
		// if (StringUtils.isNotBlank(userTpye)) {
		// request.getSession().setAttribute("userType", userTpye);
		// }
		// Object ut = request.getSession().getAttribute("userType");
		// MyContextHolder.setUserType(ut == null ? "" : ut.toString());
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

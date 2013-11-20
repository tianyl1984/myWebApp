package com.hzth.myapp.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.hzth.myapp.core.util.SpringContextHolder;

public class CustomProxyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		List<CustomFilter> customFilters = new ArrayList<>();
		Map<String, CustomFilter> filterMap = SpringContextHolder.getApplicationContext().getBeansOfType(CustomFilter.class);
		for (CustomFilter customFilter : filterMap.values()) {
			customFilters.add(customFilter);
		}
		CustomFilterChain customFilterChain = new CustomFilterChain(chain, customFilters);
		customFilterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}

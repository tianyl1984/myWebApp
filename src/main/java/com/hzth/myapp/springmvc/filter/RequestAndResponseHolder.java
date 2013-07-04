package com.hzth.myapp.springmvc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestAndResponseHolder {

	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();

	public static void setResponse(HttpServletResponse response) {
		responseLocal.remove();
		RequestAndResponseHolder.responseLocal.set(response);
	}

	public static HttpServletResponse getResponse() {
		return RequestAndResponseHolder.responseLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.remove();
		RequestAndResponseHolder.requestLocal.set(request);
	}

	public static HttpServletRequest getRequest() {
		return RequestAndResponseHolder.requestLocal.get();
	}

	public static void clearAll() {
		responseLocal.remove();
		requestLocal.remove();
	}
}

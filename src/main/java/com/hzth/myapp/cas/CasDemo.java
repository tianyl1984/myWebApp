package com.hzth.myapp.cas;

import com.hzth.myapp.web.NetUtil;

/**
 * 模拟cas登录
 * 
 * @author tianyl
 * 
 */

// 登录(访问http://127.0.0.1:8090/casClient/index)
// 1.cas client给浏览器设置302跳转到http://127.0.0.1:8094/cas/login?service=http%3A%2F%2F127.0.0.1%3A8090%2FcasClient%2Findex
// 2.浏览器跳转到cas的login，用户登录
// 3.cas server设置浏览器302跳转到http://127.0.0.1:8090/casClient/index?ticket=ST-21-fNLJGiiSgfoocxybro2p-cas01.example.org
// 4.浏览器跳转到cas client服务。有了ticket
// 5.cas client请求地址http://127.0.0.1:8094/cas/serviceValidate?pgtUrl=&ticket=" + ticket + "&service=http%3A%2F%2F127.0.0.1%3A8090%2FcasClient%2F，获取到content
// 6.从content中解析出用户登录信息
// 7.保存session，以后再访问走session

// 已登录第一次访问
// 1.cas client给浏览器设置302跳转到http://127.0.0.1:8094/cas/login?service=http%3A%2F%2F127.0.0.1%3A8090%2FcasClient%2Findex
// 2.浏览器跳转到cas的login，此时由于从cas跳转过来，有已登录的cookie（CASTGC），cas server直接跳转回service地址，并带上ticket
// 3.cas client请求地址http://127.0.0.1:8094/cas/serviceValidate?ticket=" + ticket + "&service=http%3A%2F%2F127.0.0.1%3A8090%2FcasClient%2F，获取到content
// 4.从content中解析出用户登录信息
// 5.保存session，以后再访问走session

// 退出
// cas server退出时会给登录过的client发送用户退出的请求（请求中包含ticket值，根据ticket值找到session），client销毁session
public class CasDemo {

	private static String CAS_COOKIE = "JSESSIONID=56ABAA873D3A9D92D5E9104EBCB90E6A;CASTGC=TGT-8-fJLZjwEwTn01rvAYQx6z3SCjRY3Vt9AubLKvs64MEgAcs6FVXR-cas01.example.org";

	public static void main(String[] args) throws Exception {
		String ticket = getTicket();
		// System.out.println(ticket);
		String content = getContent(ticket);
		System.out.println(content);
	}

	private static String getContent(String ticket) throws Exception {
		String url = "http://127.0.0.1:8094/cas/serviceValidate?ticket=" + ticket + "&service=http%3A%2F%2F127.0.0.1%3A8090%2FcasClient%2F";
		String content = NetUtil.getUrlResponse(url, null, null, true);
		return content;
	}

	private static String getTicket() throws Exception {
		String redirectUrl = NetUtil.getRedirectUrl("http://127.0.0.1:8094/cas/login?service=http%3A%2F%2F127.0.0.1%3A8090%2FcasClient%2F", CAS_COOKIE, null, true);
		// http://127.0.0.1:8090/casClient/index?ticket=ST-21-fNLJGiiSgfoocxybro2p-cas01.example.org
		return redirectUrl.substring(redirectUrl.indexOf("ticket=") + 7, redirectUrl.length());
	}
}

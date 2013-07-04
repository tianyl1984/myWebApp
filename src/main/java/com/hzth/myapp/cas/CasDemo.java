package com.hzth.myapp.cas;

import com.hzth.myapp.web.NetUtil;

/**
 * 模拟cas登录
 * 
 * @author tianyl
 * 
 */
// 1.cas client给浏览器设置302跳转到http://127.0.0.1:8094/cas/login?service=http%3A%2F%2F127.0.0.1%3A8090%2FcasClient%2Findex
// 2.浏览器跳转到cas的login。（提交了当前的cookie）
// 3.cas server设置浏览器302跳转到http://127.0.0.1:8090/casClient/index?ticket=ST-21-fNLJGiiSgfoocxybro2p-cas01.example.org
// 4.浏览器跳转到cas client服务。提交了ticket
// 5.cas client请求地址http://127.0.0.1:8094/cas/serviceValidate?ticket=" + ticket + "&service=http%3A%2F%2F127.0.0.1%3A8090%2FcasClient%2F，获取到content
// 6.从content中解析出用户登录信息
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

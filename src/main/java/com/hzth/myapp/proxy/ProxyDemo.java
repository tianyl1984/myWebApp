package com.hzth.myapp.proxy;

import java.lang.reflect.Proxy;

/**
 * jdk动态代理类的使用
 * 
 * @author tianyl
 * 
 */
public class ProxyDemo {

	public static void main(String[] args) {
		// 创建handler，具体的实现在handler中
		DynicInvocationHandler handler = new DynicInvocationHandler();
		// 创建代理类
		Object obj = Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(), new Class[] { IEchoService.class }, handler);
		IEchoService echoService = (IEchoService) obj;
		// 执行
		echoService.echo("aaaa");
	}

}

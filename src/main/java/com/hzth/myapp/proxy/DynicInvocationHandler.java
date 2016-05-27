package com.hzth.myapp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynicInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 具体实现
		System.out.println("invoke method : " + method.getName());
		System.out.println("args[0]:" + args[0]);
		return null;
	}

}

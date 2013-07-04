package com.hzth.myapp.oom;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * VM Args:-XX:PermSize=2M -XX:MaxPermSize=2M
 * 
 * @author tianyl
 * 
 *         方法区溢出 java.lang.OutOfMemoryError: PermGen space
 * 
 *         JDK7为 Jave heap space(堆内存溢出)jdk7中将永久区(方法区和常量池)去掉
 */
public class JavaMethodAreaOOM {

	public static void main(String[] args) {
		while (true) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object object, Method arg1, Object[] args, MethodProxy proxy) throws Throwable {
					return proxy.invokeSuper(object, args);
				}
			});
			enhancer.create();
		}

	}
}

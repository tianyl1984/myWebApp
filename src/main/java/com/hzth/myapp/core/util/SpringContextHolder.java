package com.hzth.myapp.core.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 按类型获得所有实现类
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		checkApplicationContext();
		return applicationContext.getBeansOfType(clazz);
	}

	/**
	 * 按类型获得一个实现类，没有时返回null
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBeanOneOfType(Class<T> clazz) {
		checkApplicationContext();
		Collection<T> results = applicationContext.getBeansOfType(clazz).values();
		return results.size() > 0 ? results.iterator().next() : null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}

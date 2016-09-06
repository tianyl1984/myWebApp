package com.hzth.myapp.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectTest {

	public static void main(String[] args) throws Exception {
		// m1();
		// m2();
		m3();
	}

	private static void m3() throws Exception {
		Class.forName("org.springframework.stereotype.Controller");
	}

	private static void m2() throws Exception {
		Class<?> clazz = ReflectTest.class; // 取得 Class
		Method method = clazz.getDeclaredMethod("test", List.class); // 取得方法
		Type[] type = method.getGenericParameterTypes(); // 取得泛型类型参数集
		ParameterizedType ptype = (ParameterizedType) type[0];// 将其转成参数化类型,因为在方法中泛型是参数,且Number是第一个类型参数
		type = ptype.getActualTypeArguments(); // 取得参数的实际类型
		System.out.println(type[0]); // 取出第一个元素
	}

	public void test(List<String> strs) {

	}

	private static void m1() throws Exception {
		ReflectTest instance = new ReflectTest();
		Long t1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			instance.aaa();
		}
		System.out.println("直接调用用时：" + (System.currentTimeMillis() - t1));
		Method[] methods = ReflectTest.class.getMethods();
		Method targetMethod = null;
		for (Method method : methods) {
			if (method.getName().startsWith("test")) {
				targetMethod = method;
				break;
			}
		}
		if (targetMethod != null) {
			Long t2 = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				targetMethod.invoke(instance);
			}
			System.out.println("普通反射调用用时：" + (System.currentTimeMillis() - t2));
		}

		// MethodAccess ma = MethodAccess.get(ReflectTest.class);
		// Long t3 = System.currentTimeMillis();
		// for (int i = 0; i < 100; i++) {
		// ma.invoke(instance, "aaa");
		// }
		// System.out.println("ReflectAsm调用用时：" + (System.currentTimeMillis() - t3));
	}

	public static int test() {
		int i = 0;
		for (int ii = 0; ii < 100000000; ii++) {
			i += ii;
		}
		return i;
	}

	public int aaa() {
		return test();
	}
}

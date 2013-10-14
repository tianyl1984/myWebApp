package com.hzth.myapp.classLoader;

import java.lang.reflect.Method;

public class HotSwapTest {

	public static void main(String[] args) {
		new Thread(new Runnable() {
			// Hot就是用于修改，用来测试热加载
			private String className = "com.hzth.myapp.classLoader.Hot";
			@SuppressWarnings("rawtypes")
			private Class hotClazz = null;
			private HotSwapURLClassLoader hotSwapCL = null;

			/**
			 * 加载class
			 */
			void initLoad() throws Exception {
				hotSwapCL = HotSwapURLClassLoader.getClassLoader();
				// 如果Hot类被修改了，那么会重新加载，hotClass也会返回新的
				hotClazz = hotSwapCL.loadClass(className);
			}

			@Override
			public void run() {
				try {
					while (true) {
						initLoad();
						Object hot = hotClazz.newInstance();
						Method m = hotClazz.getMethod("m1");
						m.invoke(hot, null); // 打印出相关信息
						try {
							Method m2 = hotClazz.getMethod("m2");
							m2.invoke(hot, null); // 打印出相关信息
						} catch (NoSuchMethodException ee) {
							System.out.println("没有m2方法");
						}
						// Hot hot = new Hot();
						// hot.m1();
						// 每隔10秒重新加载一次
						Thread.sleep(3000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

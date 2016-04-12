package com.hzth.myapp.pkg;

import java.lang.annotation.Annotation;

public class PkgDemo {

	public static void main(String[] args) {
		// 获取包的注解
		Package pkg = Package.getPackage("com.hzth.myapp.pkg");
		Annotation[] annotations = pkg.getAnnotations();
		for (Annotation anno : annotations) {
			System.out.println(anno.toString());
		}

		// 访问包内变量
		System.out.println(PkgFoo.PKG_NAME);
		new PkgFoo().bar();
	}

}

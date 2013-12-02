package com.hzth.myapp.classLoader;

import java.net.URL;

public class ClassLoaderTest {

	public static void main(String[] args) {
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}
		ClassLoader extensionClassloader = ClassLoader.getSystemClassLoader().getParent();
		System.out.println("extension classloader:" + extensionClassloader);
		System.out.println(System.getProperty("java.ext.dirs"));
		System.out.println(System.getProperty("java.class.path"));
		System.out.println(String.class.getClassLoader());
	}
}

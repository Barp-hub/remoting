package com.mysql.service;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

public class SecondTest {

	@Test
	public void cast() {

		OneTest tester = null;
		OneTest one = OneTest.class.cast(tester);
		if (one == null) {
			System.out.println("空");
			tester = new OneTest();
			one = OneTest.class.cast(tester);
		}

		System.out.println(one.getClass().toString());
	}

	@Test
	public void classLoader() {
		System.out.println("BootstrapClassLoader 的加载路径: ");

		@SuppressWarnings("restriction")
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (URL url : urls)
			System.out.println(url);
		System.out.println("----------------------------");

		//取得扩展类加载器  
		URLClassLoader extClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader().getParent();

		System.out.println(extClassLoader);
		System.out.println("扩展类加载器 的加载路径: ");

		urls = extClassLoader.getURLs();
		for (URL url : urls)
			System.out.println(url);

		System.out.println("----------------------------");

		//取得应用(系统)类加载器  
		URLClassLoader appClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

		System.out.println(appClassLoader);
		System.out.println("应用(系统)类加载器 的加载路径: ");

		urls = appClassLoader.getURLs();
		for (URL url : urls)
			System.out.println(url);

		System.out.println("----------------------------");

	}
}

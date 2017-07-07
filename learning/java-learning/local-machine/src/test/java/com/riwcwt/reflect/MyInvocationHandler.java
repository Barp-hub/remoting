package com.riwcwt.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("method:" + method.getName());
		System.out.println("classloader:" + method.getDeclaringClass());

		return method.getName();
	}

}

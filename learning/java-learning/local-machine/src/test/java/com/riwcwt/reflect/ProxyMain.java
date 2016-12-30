package com.riwcwt.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyMain {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {

		//JDK proxy
		MyInterface instance = MyInterface.class.cast(Proxy.newProxyInstance(ProxyMain.class.getClassLoader(), new Class[] { MyInterface.class }, new MyInvocationHandler()));
		System.out.println(instance.random());

		//CGLib proxy
		MyClass object = MyClass.class.newInstance();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(object.getClass());
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy proxy) throws Throwable {
				System.out.print("you say: ");
				Object result = proxy.invokeSuper(arg0, arg2);
				System.out.println(" [" + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + " " + Calendar.getInstance().get(Calendar.SECOND) + "]");
				return result;
			}
		});
		MyClass proxy = MyClass.class.cast(enhancer.create());
		System.out.println(proxy.random());
		proxy.hello("world");
	}

}

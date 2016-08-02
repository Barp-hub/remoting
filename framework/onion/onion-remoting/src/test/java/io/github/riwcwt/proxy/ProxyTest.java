package io.github.riwcwt.proxy;

import java.lang.reflect.Method;

import org.junit.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyTest {

	@Test
	public void proxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Sample.class);
		enhancer.setCallback(new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return "";
			}
		});

		Sample sample = Sample.class.cast(enhancer.create());
		System.out.println(sample.hello("world!"));
	}

}

class Sample {

	public String hello(String name) {
		return "hello, " + name;
	}

}

class MethodProxy implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, net.sf.cglib.proxy.MethodProxy proxy)
			throws Throwable {
		return null;
	}
}
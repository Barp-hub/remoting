package io.github.riwcwt.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

@Component
public class Proxy {
	@SuppressWarnings("unchecked")
	public <T> T create(Class<Object> interfaces) {
		return (T) java.lang.reflect.Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class<?>[] { interfaces }, new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
				});
	}
}

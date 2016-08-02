package io.github.riwcwt.proxy.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import io.github.riwcwt.entity.ServiceRequest;

@Component
public class ProxyFactory implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		ServiceRequest request = new ServiceRequest();
		request.setService(method.getClass().getCanonicalName());
		request.setMethod(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParameters(args);

		return "proxy";
	}

}

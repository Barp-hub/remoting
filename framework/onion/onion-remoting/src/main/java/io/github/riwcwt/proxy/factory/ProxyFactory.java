package io.github.riwcwt.proxy.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.riwcwt.constant.MessageType;
import io.github.riwcwt.entity.Request;
import io.github.riwcwt.entity.ServiceRequest;
import io.github.riwcwt.netty.client.NettyClient;

@Component
public class ProxyFactory implements InvocationHandler {

	@Autowired
	private NettyClient client = null;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setService(method.getClass().getCanonicalName());
		serviceRequest.setMethod(method.getName());
		serviceRequest.setParameterTypes(method.getParameterTypes());
		serviceRequest.setParameters(args);

		Request request = new Request();
		request.setData(serviceRequest);
		request.setRequestId(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		request.setType(MessageType.NORMAL_REQUEST);

		this.client.send(new InetSocketAddress("localhost", 8888), request);
		return "proxy";
	}

}

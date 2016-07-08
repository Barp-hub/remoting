package io.github.riwcwt.proxy.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.github.riwcwt.entity.ServiceRequest;
import io.github.riwcwt.proxy.annotation.RemotingService;

@Component
public class DynamicProxy implements ApplicationContextAware, InitializingBean {
	private ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	private Map<String, Object> beans = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		beans = this.context.getBeansWithAnnotation(RemotingService.class);
	}

	private Object findService(String service, String version) {
		for (String key : beans.keySet()) {
			RemotingService annotation = beans.get(key).getClass().getAnnotation(RemotingService.class);
			if (annotation.value().getCanonicalName().equals(service) && annotation.version().equals(version)) {
				return beans.get(key);
			}
		}
		return null;
	}

	public Object invoke(ServiceRequest request) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object target = this.findService(request.getService(), request.getVersion());
		if (target != null) {
			Method method = target.getClass().getMethod(request.getMethod(), request.getParameterTypes());
			return method.invoke(target, request.getParameters());
		}
		return null;
	}

}

package io.github.riwcwt.proxy;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.github.riwcwt.annotation.RemotingService;

@Component
public class DynamicProxy implements ApplicationContextAware, InitializingBean {
	private ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> beans = this.context.getBeansWithAnnotation(RemotingService.class);

	}
}

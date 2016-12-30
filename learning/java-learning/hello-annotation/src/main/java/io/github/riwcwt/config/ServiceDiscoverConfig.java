package io.github.riwcwt.config;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.github.riwcwt.Main;
import io.github.riwcwt.annotation.Service;

public class ServiceDiscoverConfig implements ApplicationContextAware, InitializingBean, Closeable {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> services = context.getBeansWithAnnotation(Service.class);
		if (services != null) {
			for (String key : services.keySet()) {
				logger.info(key + " - " + services.get(key).getClass().getCanonicalName());
			}
		}
	}

	@Override
	public void close() throws IOException {
		logger.info("closing...");
	}

}

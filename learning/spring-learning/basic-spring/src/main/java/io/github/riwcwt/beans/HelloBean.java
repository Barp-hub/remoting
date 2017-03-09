package io.github.riwcwt.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class HelloBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(HelloBean.class);

	@Override
	public void setBeanName(String name) {
		logger.info("set bean name : " + name);
	}

	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		logger.info("set bean factory...");
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		logger.info("set application context...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("after property set...");
	}

	@PostConstruct
	public void init() {
		logger.info("init...");
	}

	@Override
	public void destroy() throws Exception {
		logger.info("destroying...");
	}

	@PreDestroy
	public void close() {
		logger.info("closing...");
	}
}

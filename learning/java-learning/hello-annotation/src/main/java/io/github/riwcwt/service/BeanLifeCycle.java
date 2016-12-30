package io.github.riwcwt.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

@Component
public class BeanLifeCycle implements InitializingBean, DisposableBean, ApplicationContextAware, MessageSourceAware,
		ApplicationEventPublisherAware {

	private static final Logger logger = LoggerFactory.getLogger(BeanLifeCycle.class);

	@Override
	public void destroy() throws Exception {
		logger.info("bean life cycle : DisposableBean-destroy");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("bean life cycle : InitializingBean-afterPropertiesSet");
	}

	@PostConstruct
	public void init() {
		logger.info("bean life cycle : @PostConstruct");
	}

	@PreDestroy
	public void close() {
		logger.info("bean life cycle : @PreDestroy");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		logger.info("bean life cycle : ApplicationContextAware-setApplicationContext");
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		logger.info("bean life cycle : MessageSourceAware-setMessageSource");
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		logger.info("bean life cycle : ApplicationEventPublisherAware-setApplicationEventPublisher");
	}
}

package io.github.riwcwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

	private ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		logger.info("setting context...");
		logger.info(context.getBeansWithAnnotation(Controller.class).toString());
	}

}

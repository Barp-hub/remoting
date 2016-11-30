package io.github.riwcwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonService implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

	@Value("${tomcat.name}")
	private String tomcat = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("tomcat : " + tomcat);
	}

}

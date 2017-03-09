package io.github.riwcwt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.github.riwcwt.config.ApplicationConfig;

public class ApplicationTest {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

	@Test
	public void config() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		context.start();
		logger.info("context start...");
		context.stop();
		context.close();
	}

}

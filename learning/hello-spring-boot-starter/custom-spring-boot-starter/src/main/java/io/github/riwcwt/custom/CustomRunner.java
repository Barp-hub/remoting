package io.github.riwcwt.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.AbstractApplicationContext;

public class CustomRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(CustomRunner.class);

	@Autowired
	private AbstractApplicationContext applicationContext;

	public void run(String... args) throws Exception {
		logger.info(applicationContext.getApplicationName());
	}

}

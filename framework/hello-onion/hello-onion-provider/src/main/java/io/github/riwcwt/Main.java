package io.github.riwcwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.github.riwcwt.config.ApplicationConfig;
import io.github.riwcwt.hello.HelloService;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = null;
		try {
			context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
			context.registerShutdownHook();

			HelloService helloService = context.getBean(HelloService.class);
			logger.info(helloService.hello("world"));
			System.in.read();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (context != null) {
				context.close();
			}
		}

	}

}

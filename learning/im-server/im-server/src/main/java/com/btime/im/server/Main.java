package com.btime.im.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.btime.im.server.config.ApplicationConfig;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	private static volatile boolean running = true;

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = null;

		try {
			logger.info(" starting im-server ...");
			context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

			logger.info("已启动！");

			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					synchronized (Main.class) {
						logger.info("closing...");
						running = false;
						Main.class.notify();
					}
				}
			});

			synchronized (Main.class) {
				while (running) {
					try {
						logger.info("running...");
						Main.class.wait();
					} catch (Throwable e) {
					}
				}
			}

		} catch (Exception e) {
			logger.error("start im-server error!", e);
		} finally {
			if (context != null) {
				context.close();
			}
		}

	}

}

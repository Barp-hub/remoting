package io.github.riwcwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.github.riwcwt.config.ApplicationConfig;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private static volatile boolean running = true;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = null;

		try {
			logger.info("启动中...");
			context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
			context.registerShutdownHook();

			logger.info("已启动！");

			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					synchronized (Main.class) {
						logger.info("关闭中...");
						running = false;
						Main.class.notify();
					}
				}
			});

			synchronized (Main.class) {
				while (running) {
					try {
						logger.info("运行中...");
						Main.class.wait();
					} catch (Throwable e) {
					}
				}
			}
			logger.info("程序关闭！");

		} catch (Exception e) {
			logger.error("程序启动报错！", e);
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}
}

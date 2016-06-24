package io.github.riwcwt.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		NettyServer server = new NettyServer();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				synchronized (Main.class) {
					logger.info("关闭中...");
					server.close();
				}
			}
		});

		server.start();
	}

}

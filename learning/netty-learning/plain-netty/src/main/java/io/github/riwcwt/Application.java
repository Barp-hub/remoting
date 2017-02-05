package io.github.riwcwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.netty.NettyServer;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;

/**
 * Hello world!
 *
 */
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws InterruptedException {
		InternalLoggerFactory.setDefaultFactory(Log4JLoggerFactory.INSTANCE);

		NettyServer server = new NettyServer();
		logger.info("starting server...");
		server.start();
	}
}

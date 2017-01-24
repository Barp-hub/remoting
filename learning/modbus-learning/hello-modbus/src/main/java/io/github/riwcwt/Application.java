package io.github.riwcwt;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		Slave slave = new Slave();
		slave.start();

		logger.info("start...");

		System.in.read();

		logger.info("stop...");
		slave.stop();
	}
}

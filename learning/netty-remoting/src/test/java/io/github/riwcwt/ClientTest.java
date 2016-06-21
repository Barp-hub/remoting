package io.github.riwcwt;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.client.NettyClient;

public class ClientTest {
	private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

	@Test
	public void client() throws IOException {
		NettyClient client = new NettyClient();
		client.start();

		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						client.connect("localhost", 8888);
					} catch (InterruptedException e) {
						logger.info("can not connect to : localhost:8888");
					}
				}
			});
			thread.start();
		}
		System.in.read();
		client.destroy();
	}

}

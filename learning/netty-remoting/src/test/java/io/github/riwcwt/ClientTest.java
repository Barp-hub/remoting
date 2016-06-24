package io.github.riwcwt;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.client.NettyClient;

public class ClientTest {
	private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

	@Test
	public void thread() throws IOException {
		NettyClient client = new NettyClient();
		client.start();

		for (int i = 0; i < 10000; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						client.connect("121.41.101.137", 8888);
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

	@Test
	public void client() {
		NettyClient client = new NettyClient();
		client.start();
		try {
			for (int i = 0; i < 10000; i++) {
				client.connect("121.41.101.137", 8888);
			}
			System.in.read();
			client.destroy();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

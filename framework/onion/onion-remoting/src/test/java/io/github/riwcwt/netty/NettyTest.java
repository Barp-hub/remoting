package io.github.riwcwt.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.riwcwt.constant.MessageType;
import io.github.riwcwt.entity.Request;
import io.github.riwcwt.entity.Response;
import io.github.riwcwt.netty.client.NettyClient;
import io.github.riwcwt.netty.config.NettyConfig;
import io.github.riwcwt.netty.server.NettyServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NettyConfig.class)
public class NettyTest {

	private static final Logger logger = LoggerFactory.getLogger(NettyTest.class);

	@Autowired
	private NettyServer server = null;

	@Autowired
	private NettyClient client = null;

	@Test
	public void server() throws IOException, InterruptedException {
		this.server.start(8888);

		logger.info("running...");
		System.in.read();
	}

	@Test
	public void client() throws InterruptedException, IOException {
		InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8888);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			Request request = new Request();
			request.setType(MessageType.HEART_BEAT);
			request.setRequestId(UUID.randomUUID().toString());
			logger.info("PING:" + request.getRequestId());
			Response response = this.client.send(socketAddress, request);
			if (response != null) {
				logger.info("PONG:" + response.getRequestId());
			}
		}
		long end = System.currentTimeMillis();
		logger.info("发送:" + 10000 + "  耗时：" + (end - start));
		System.in.read();
	}

}

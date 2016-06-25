package io.github.riwcwt.netty;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.riwcwt.constant.MessageType;
import io.github.riwcwt.entity.Request;
import io.github.riwcwt.netty.client.NettyClient;
import io.github.riwcwt.netty.config.NettyConfig;
import io.github.riwcwt.netty.server.NettyServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

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
		Channel channel = this.client.connect(new InetSocketAddress("localhost", 8888));
		for (int i = 0; i < 10; i++) {
			Request request = new Request();
			request.setType(MessageType.HEART_BEAT);
			logger.info("PING");
			ChannelFuture future = channel.writeAndFlush(request);
		}
		System.in.read();
	}

}

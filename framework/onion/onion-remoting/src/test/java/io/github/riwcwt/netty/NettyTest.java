package io.github.riwcwt.netty;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.riwcwt.netty.config.NettyConfig;
import io.github.riwcwt.netty.server.NettyServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NettyConfig.class)
public class NettyTest {

	private static final Logger logger = LoggerFactory.getLogger(NettyTest.class);

	@Autowired
	private NettyServer server = null;

	@Test
	public void server() throws IOException {
		try {
			this.server.start(8888);
		} catch (InterruptedException e) {
			logger.error("netty server start error!", e);
		}

		System.in.read();
	}

}

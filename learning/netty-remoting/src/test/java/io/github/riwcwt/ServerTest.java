package io.github.riwcwt;

import org.junit.Test;

import io.github.riwcwt.server.NettyServer;

public class ServerTest {

	@Test
	public void server() {
		NettyServer server = new NettyServer();
		server.start();
	}

}

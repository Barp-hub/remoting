package io.github.riwcwt.grpc.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "grpc")
public class GrpcServerProperties {

	/**
	 * gRPC server port
	 */
	private int port = 6565;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}

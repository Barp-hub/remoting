package io.github.riwcwt.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.server.interceptor.ServerHeaderInterceptor;
import io.github.riwcwt.server.service.GreeterImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException, InterruptedException {
		server();
	}

	public static void server() throws IOException, InterruptedException {
		int port = 9999;
		Server server = ServerBuilder.forPort(port)
				.addService(ServerInterceptors.intercept(new GreeterImpl(), new ServerHeaderInterceptor())).build()
				.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
				logger.info("*** shutting down gRPC server since JVM is shutting down");
				if (server != null) {
					server.shutdown();
				}
				logger.info("*** server shut down");
			}
		});

		server.awaitTermination();
	}

}

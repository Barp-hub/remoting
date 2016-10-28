package io.github.riwcwt.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.api.GreeterGrpc;
import io.github.riwcwt.api.HelloReply;
import io.github.riwcwt.api.HelloRequest;
import io.github.riwcwt.client.interceptor.ClientHeaderInterceptor;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws InterruptedException {
		client();
	}

	public static void client() throws InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9999).usePlaintext(true).build();
		ClientInterceptor interceptor = new ClientHeaderInterceptor();
		Channel interceptorChannel = ClientInterceptors.intercept(channel, interceptor);
		GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(interceptorChannel);
		GreeterGrpc.GreeterStub asyncStub = GreeterGrpc.newStub(interceptorChannel);

		HelloReply reply = blockingStub.sayHello(HelloRequest.newBuilder().setName("world").build());

		logger.info(reply.getMessage());

		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

}

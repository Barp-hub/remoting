package io.github.riwcwt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.ChatMessage;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class HelloWorldClient {
	private static final Logger						logger	= LoggerFactory.getLogger(HelloWorldClient.class.getName());

	private final ManagedChannel					channel;
	private final GreeterGrpc.GreeterBlockingStub	blockingStub;

	private final GreeterGrpc.GreeterStub			asyncStub;

	/**
	 * Construct client connecting to HelloWorld server at {@code host:port}.
	 */
	public HelloWorldClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port)
				// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
				// needing certificates.
				.usePlaintext(true).build();
		blockingStub = GreeterGrpc.newBlockingStub(channel);
		asyncStub = GreeterGrpc.newStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void chat() throws InterruptedException {
		final CountDownLatch finishLatch = new CountDownLatch(1);

		long start = System.currentTimeMillis();

		StreamObserver<ChatMessage> requstObserver = asyncStub.chat(new StreamObserver<ChatMessage>() {

			@Override
			public void onNext(ChatMessage value) {
				logger.info("client received:" + value.getMessage());

			}

			@Override
			public void onError(Throwable t) {
				Status status = Status.fromThrowable(t);
				logger.info(status.getDescription());
			}

			@Override
			public void onCompleted() {
				logger.info("client completed!");
				finishLatch.countDown();
				long end = System.currentTimeMillis();

				logger.info("一共耗时：" + (end - start));
			}
		});

		for (int i = 0; i < 10000; i++) {
			requstObserver.onNext(ChatMessage.newBuilder().setMessage(String.valueOf(i)).build());
		}

		requstObserver.onCompleted();

		finishLatch.await();
	}

	/** Say hello to server. */
	public void greet(String name) {
		logger.info("Will try to greet " + name + " ...");
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		HelloReply response;
		try {
			response = blockingStub.sayHello(request);
		} catch (StatusRuntimeException e) {
			logger.error("RPC failed: {0}", e.getStatus());
			return;
		}
		logger.info("Greeting: " + response.getMessage());
	}

	/**
	 * Greet server. If provided, the first element of {@code args} is the name to use in the greeting.
	 */
	public static void main(String[] args) throws Exception {
		HelloWorldClient client = new HelloWorldClient("localhost", 50051);
		try {
			/* Access a service running on the local machine on port 50051 */
			String user = "world";
			if (args.length > 0) {
				user = args[0]; /*
								 * Use the arg as the name to greet if provided
								 */
			}

			long start = System.currentTimeMillis();

			for (int i = 0; i < 10; i++) {
				client.greet(user);
			}

			long end = System.currentTimeMillis();

			logger.info("一共耗时：" + (end - start));

			client.chat();
		} finally {
			client.shutdown();
		}
	}
}

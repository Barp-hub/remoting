package io.github.riwcwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.interceptor.ServerHeaderInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.grpc.examples.helloworld.ChatMessage;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class HelloWorldServer {
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldServer.class.getName());

	/* The port on which the server should run */
	private int port = 50052;
	private Server server;

	private void start() throws IOException {
		server = ServerBuilder.forPort(port)
				.addService(ServerInterceptors.intercept(new GreeterImpl(), new ServerHeaderInterceptor())).build()
				.start();
		logger.info("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				HelloWorldServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon
	 * threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	/**
	 * Main launches the server from the command line.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		final HelloWorldServer server = new HelloWorldServer();
		server.start();
		server.blockUntilShutdown();
	}

	private class GreeterImpl extends GreeterGrpc.GreeterImplBase {

		@Override
		public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessage> responseObserver) {
			return new StreamObserver<ChatMessage>() {

				@Override
				public void onNext(ChatMessage value) {
					logger.info("server received:" + value.getMessage());
					ChatMessage message = ChatMessage.newBuilder().setMessage("收到!").build();
					responseObserver.onNext(message);
				}

				@Override
				public void onError(Throwable t) {
					logger.error(t.getMessage(), t);
				}

				@Override
				public void onCompleted() {
					responseObserver.onCompleted();
				}
			};
		}

		@Override
		public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
			HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}
}
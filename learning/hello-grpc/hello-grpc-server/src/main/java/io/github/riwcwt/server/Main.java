package io.github.riwcwt.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.api.ChatMessage;
import io.github.riwcwt.api.GreeterGrpc;
import io.github.riwcwt.api.HelloReply;
import io.github.riwcwt.api.HelloRequest;
import io.github.riwcwt.server.interceptor.ServerHeaderInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;

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

	public static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

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

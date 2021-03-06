package io.github.riwcwt.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.api.ChatMessage;
import io.github.riwcwt.api.GreeterGrpc;
import io.github.riwcwt.api.HelloReply;
import io.github.riwcwt.api.HelloRequest;
import io.grpc.stub.StreamObserver;

public class GreeterImpl extends GreeterGrpc.GreeterImplBase {
	private static final Logger logger = LoggerFactory.getLogger(GreeterImpl.class);

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

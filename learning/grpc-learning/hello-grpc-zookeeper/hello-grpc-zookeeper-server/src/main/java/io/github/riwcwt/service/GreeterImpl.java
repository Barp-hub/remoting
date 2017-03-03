package io.github.riwcwt.service;

import io.github.riwcwt.grpc.annotation.GrpcService;
import io.github.riwcwt.grpc.zookeeper.api.ChatMessage;
import io.github.riwcwt.grpc.zookeeper.api.GreeterGrpc;
import io.github.riwcwt.grpc.zookeeper.api.HelloReply;
import io.github.riwcwt.grpc.zookeeper.api.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by michael on 2017-03-02.
 */
@GrpcService
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {
    private static final Logger logger = LoggerFactory.getLogger(GreeterImpl.class);

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("two : Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ChatMessage> chat(final StreamObserver<ChatMessage> responseObserver) {
        return new StreamObserver<ChatMessage>() {

            @Override
            public void onNext(ChatMessage chatMessage) {
                logger.info("server received:" + chatMessage.getMessage());
                ChatMessage message = ChatMessage.newBuilder().setMessage("收到!").build();
                responseObserver.onNext(message);
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error(throwable.getMessage(), throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
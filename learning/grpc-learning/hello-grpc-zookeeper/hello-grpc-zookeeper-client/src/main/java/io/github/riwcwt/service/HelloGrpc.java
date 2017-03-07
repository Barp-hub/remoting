package io.github.riwcwt.service;

import io.github.riwcwt.grpc.annotation.GrpcClient;
import io.github.riwcwt.grpc.zookeeper.api.GreeterGrpc;
import io.github.riwcwt.grpc.zookeeper.api.HelloReply;
import io.github.riwcwt.grpc.zookeeper.api.HelloRequest;
import io.grpc.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by michael on 2017-03-02.
 */
@Component
public class HelloGrpc implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(HelloGrpc.class);

    @GrpcClient(value = "hello-grpc")
    private Channel channel = null;

    @Override
    public void afterPropertiesSet() throws Exception {

        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);

        for (int i = 0; i < 10; i++) {

            HelloReply reply = blockingStub.sayHello(HelloRequest.newBuilder().setName("world").build());

            logger.info("客户端收到：" + reply.getMessage());

            Thread.sleep(10000);
        }

    }

    @Override
    public void destroy() throws Exception {
    }
}

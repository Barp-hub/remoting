package io.github.riwcwt;

import io.github.riwcwt.api.GreeterGrpc;
import io.github.riwcwt.api.HelloReply;
import io.github.riwcwt.api.HelloRequest;
import io.github.riwcwt.client.interceptor.ClientHeaderInterceptor;
import io.github.riwcwt.client.nameResolver.ServerNameResolverProvider;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void thread() throws InterruptedException {

        ManagedChannel channel = NettyChannelBuilder.forTarget("server://localhost:11111")
                .nameResolverFactory(new ServerNameResolverProvider())
                .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance()).usePlaintext(true).build();
        ClientInterceptor interceptor = new ClientHeaderInterceptor();
        Channel interceptorChannel = ClientInterceptors.intercept(channel, interceptor);
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(interceptorChannel);


        int count = 2000;
        CountDownLatch latch = new CountDownLatch(count);

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {

                for (int j = 0; j < 10; j++) {
                    HelloReply reply = blockingStub.sayHello(HelloRequest.newBuilder().setName("world").build());

                    logger.info(reply.getMessage());
                }

                latch.countDown();
            });
            thread.start();
        }

        latch.await();

        long end = System.currentTimeMillis();

        System.out.println("耗时：" + (end - start));

        channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);

    }

}

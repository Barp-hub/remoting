package io.github.riwcwt.service;

import io.github.riwcwt.grpc.nameresolver.ZookeeperNameResolverProvider;
import io.github.riwcwt.grpc.registry.zookeeper.ZookeeperRegistry;
import io.github.riwcwt.grpc.zookeeper.api.GreeterGrpc;
import io.github.riwcwt.grpc.zookeeper.api.HelloReply;
import io.github.riwcwt.grpc.zookeeper.api.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 2017-03-02.
 */
@Component
public class HelloGrpc implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(HelloGrpc.class);

    private static final String BASE_PATH = "GRPC";

    @Autowired
    private Environment environment;

    private ZookeeperRegistry registry;

    private CuratorFramework client;

    @Override
    public void afterPropertiesSet() throws Exception {

        logger.info("initing register center...");
        client = CuratorFrameworkFactory.newClient(environment.getProperty("grpc.registerCenter"), new ExponentialBackoffRetry(1000, 3));
        client.start();

        registry = new ZookeeperRegistry();
        registry.init(client, BASE_PATH);


        ManagedChannel channel = NettyChannelBuilder.forTarget("zookeeper://" + environment.getProperty("grpc.serviceName"))
                .nameResolverFactory(new ZookeeperNameResolverProvider(registry))
                .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance()).usePlaintext(true).build();


        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);

        for (int i = 0; i < 10; i++) {

            HelloReply reply = blockingStub.sayHello(HelloRequest.newBuilder().setName("world").build());

            logger.info("客户端收到：" + reply.getMessage());

            Thread.sleep(10000);
        }

        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    @Override
    public void destroy() throws Exception {
        Optional.ofNullable(registry).ifPresent(zookeeperRegistry -> {
            try {
                zookeeperRegistry.close();
            } catch (IOException e) {
                logger.error("close register center error!", e);
            }
        });

        Optional.ofNullable(client).ifPresent(curatorFramework -> {
            curatorFramework.close();
        });
    }
}

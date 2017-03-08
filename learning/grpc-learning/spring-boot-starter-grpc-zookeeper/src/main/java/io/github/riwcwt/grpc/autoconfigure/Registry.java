package io.github.riwcwt.grpc.autoconfigure;

import io.github.riwcwt.grpc.nameresolver.ZookeeperNameResolverProvider;
import io.github.riwcwt.grpc.registry.zookeeper.ZookeeperRegistry;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
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
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 2017-03-06.
 */
public class Registry implements InitializingBean, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(Registry.class);

    private static final String BASE_PATH = "GRPC";

    @Autowired
    private GrpcProperties grpcProperties;

    private ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<>();

    private ZookeeperRegistry registry;

    private CuratorFramework client;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("initing register center...");
        client = CuratorFrameworkFactory.newClient(grpcProperties.getRegisterCenter(), new ExponentialBackoffRetry(1000, 3));
        client.start();

        registry = new ZookeeperRegistry();
        registry.init(client, BASE_PATH);
    }

    public ZookeeperRegistry registry() {
        return registry;
    }

    public Channel channel(String service, List<ClientInterceptor> list) {
        return Optional.ofNullable(channels.get(service)).orElseGet(() -> {
            Channel channel = NettyChannelBuilder.forTarget("zookeeper://" + service)
                    .nameResolverFactory(new ZookeeperNameResolverProvider(registry))
                    .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance()).usePlaintext(true).build();
            if (!CollectionUtils.isEmpty(list)) {
                channel = ClientInterceptors.intercept(channel, list);
            }
            channels.putIfAbsent(service, channel);
            return channel;
        });
    }

    @Override
    public void destroy() throws Exception {
        logger.info("unregister grpc service ...");
        registry.close();

        logger.info("disconnect to zookeeper ...");
        client.close();

        logger.info("close grpc client ...");
        channels.forEach((service, channel) -> {
            if (channel instanceof ManagedChannel) {
                try {
                    ManagedChannel.class.cast(channel).shutdown().awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }


}

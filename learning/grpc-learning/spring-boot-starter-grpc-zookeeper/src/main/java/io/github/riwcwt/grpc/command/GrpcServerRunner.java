package io.github.riwcwt.grpc.command;

import io.github.riwcwt.grpc.annotation.GrpcGlobalInterceptor;
import io.github.riwcwt.grpc.annotation.GrpcService;
import io.github.riwcwt.grpc.autoconfigure.GrpcServerProperties;
import io.github.riwcwt.grpc.registry.zookeeper.Instance;
import io.github.riwcwt.grpc.registry.zookeeper.ZookeeperRegistry;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.grpc.ServerServiceDefinition;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.type.StandardMethodMetadata;

import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by michael on 2017-03-02.
 */
public class GrpcServerRunner implements CommandLineRunner, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServerRunner.class);

    private static final String BASE_PATH = "GRPC";
    @Autowired
    private AbstractApplicationContext applicationContext;

    @Autowired
    private GrpcServerProperties grpcServerProperties;

    private Server server;

    private ZookeeperRegistry registry;

    private CuratorFramework client;

    @Override
    public void destroy() throws Exception {
        logger.info("unregister grpc service ...");
        registry.close();

        logger.info("disconnect to zookeeper ...");
        client.close();

        logger.info("Shutting down gRPC server ...");
        Optional.ofNullable(server).ifPresent(Server::shutdown);
        logger.info("gRPC server stopped.");
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("initing register center...");
        client = CuratorFrameworkFactory.newClient(grpcServerProperties.getRegisterCenter(), new ExponentialBackoffRetry(1000, 3));
        client.start();

        registry = new ZookeeperRegistry();
        registry.init(client, BASE_PATH);

        logger.info("Starting gRPC Server...");

        Collection<ServerInterceptor> globalInterceptors = this.getBeanNamesByTypeWithAnnotation(GrpcGlobalInterceptor.class, ServerInterceptor.class)
                .map(name -> applicationContext.getBeanFactory().getBean(name, ServerInterceptor.class)).collect(Collectors.toList());

        final ServerBuilder<?> serverBuilder = ServerBuilder.forPort(this.grpcServerProperties.getPort());

        // find and register all GRpcService-enabled beans
        getBeanNamesByTypeWithAnnotation(GrpcService.class, BindableService.class).forEach(name -> {
            BindableService srv = applicationContext.getBeanFactory().getBean(name, BindableService.class);
            ServerServiceDefinition serviceDefinition = srv.bindService();
            GrpcService gRpcServiceAnn = applicationContext.findAnnotationOnBean(name, GrpcService.class);
            serviceDefinition = bindInterceptors(serviceDefinition, gRpcServiceAnn, globalInterceptors);
            serverBuilder.addService(serviceDefinition);
            logger.info("'{}' service has been registered.", srv.getClass().getName());
        });

        server = serverBuilder.build().start();
        logger.info("gRPC Server started, listening on port {}.", this.grpcServerProperties.getPort());

        registry.registerService(ServiceInstance.<Instance>builder()
                .id(UUID.randomUUID().toString())
                .name(grpcServerProperties.getServiceName())
                .port(grpcServerProperties.getPort())
                .address(InetAddress.getLocalHost().getHostAddress())
                .payload(null)
                .uriSpec(new UriSpec("{scheme}://{address}:{port}")).build());

        startDaemonAwaitThread();
    }

    private void startDaemonAwaitThread() {
        Thread awaitThread = new Thread(() -> {
            try {
                io.github.riwcwt.grpc.command.GrpcServerRunner.this.server.awaitTermination();
            } catch (InterruptedException e) {
                logger.error("gRPC server stopped.", e);
            }
        });
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    private ServerServiceDefinition bindInterceptors(ServerServiceDefinition serviceDefinition, GrpcService gRpcService, Collection<ServerInterceptor> globalInterceptors) {

        Stream<? extends ServerInterceptor> privateInterceptors = Stream.of(gRpcService.interceptors()).map(interceptorClass -> {
            try {
                return 0 < applicationContext.getBeanNamesForType(interceptorClass).length ? applicationContext.getBean(interceptorClass) : interceptorClass.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("Failed to create interceptor instance.", e);
            }
        });

        List<ServerInterceptor> interceptors = Stream.concat(gRpcService.applyGlobalInterceptors() ? globalInterceptors.stream() : Stream.empty(), privateInterceptors).distinct()
                .collect(Collectors.toList());
        return ServerInterceptors.intercept(serviceDefinition, interceptors);
    }

    private <T> Stream<String> getBeanNamesByTypeWithAnnotation(Class<? extends Annotation> annotationType, Class<T> beanType) throws Exception {
        return Stream.of(applicationContext.getBeanNamesForType(beanType)).filter(name -> {
            BeanDefinition beanDefinition = applicationContext.getBeanFactory().getBeanDefinition(name);
            if (beanDefinition.getSource() instanceof StandardMethodMetadata) {
                StandardMethodMetadata metadata = (StandardMethodMetadata) beanDefinition.getSource();
                return metadata.isAnnotated(annotationType.getName());
            }
            return null != applicationContext.getBeanFactory().findAnnotationOnBean(name, annotationType);
        });
    }

}

package io.github.riwcwt.grpc.command;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.type.StandardMethodMetadata;

import io.github.riwcwt.grpc.annotation.GrpcGlobalInterceptor;
import io.github.riwcwt.grpc.annotation.GrpcService;
import io.github.riwcwt.grpc.autoconfigure.GrpcServerProperties;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.grpc.ServerServiceDefinition;

public class GrpcServerRunner implements CommandLineRunner, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(GrpcServerRunner.class);

	@Autowired
	private AbstractApplicationContext applicationContext;

	@Autowired
	private GrpcServerProperties grpcServerProperties;

	private Server server;

	@Override
	public void run(String... args) throws Exception {
		logger.info("Starting gRPC Server ...");

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
		startDaemonAwaitThread();

	}

	private void startDaemonAwaitThread() {
		Thread awaitThread = new Thread() {
			@Override
			public void run() {
				try {
					GrpcServerRunner.this.server.awaitTermination();
				} catch (InterruptedException e) {
					logger.error("gRPC server stopped.", e);
				}
			}

		};
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

	@Override
	public void destroy() throws Exception {
		logger.info("Shutting down gRPC server ...");
		Optional.ofNullable(server).ifPresent(Server::shutdown);
		logger.info("gRPC server stopped.");
	}

}

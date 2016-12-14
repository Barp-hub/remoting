package io.github.riwcwt.grpc.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.riwcwt.grpc.annotation.GrpcService;
import io.github.riwcwt.grpc.command.GrpcServerRunner;

@Configuration
public class GrpcAutoConfiguration {

	@Bean
	@ConditionalOnBean(annotation = GrpcService.class)
	public GrpcServerRunner grpcServerRunner() {
		return new GrpcServerRunner();
	}

}

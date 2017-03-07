package io.github.riwcwt.grpc.autoconfigure;

import io.github.riwcwt.grpc.annotation.GrpcClient;
import io.github.riwcwt.grpc.annotation.GrpcService;
import io.github.riwcwt.grpc.client.GrpcClientBeanPostProcessor;
import io.github.riwcwt.grpc.command.GrpcServerRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by michael on 2017-03-02.
 */
@Configuration
@EnableConfigurationProperties(GrpcServerProperties.class)
public class GrpcAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "grpc.registerCenter")
    public Registry registry() {
        return new Registry();
    }

    @Bean
    @ConditionalOnBean(annotation = GrpcService.class)
    public GrpcServerRunner grpcServerRunner() {
        return new GrpcServerRunner();
    }

    @Bean
    @ConditionalOnClass(value = GrpcClient.class)
    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor() {
        return new GrpcClientBeanPostProcessor();
    }
}

package io.github.riwcwt.grpc.autoconfigure;

import io.github.riwcwt.grpc.annotation.GrpcService;
import io.github.riwcwt.grpc.command.GrpcServerRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
    @ConditionalOnBean(annotation = GrpcService.class)
    public GrpcServerRunner grpcServerRunner() {
        return new GrpcServerRunner();
    }

}

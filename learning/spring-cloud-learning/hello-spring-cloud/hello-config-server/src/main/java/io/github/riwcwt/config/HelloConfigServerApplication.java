package io.github.riwcwt.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class HelloConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloConfigServerApplication.class, args);
    }
}

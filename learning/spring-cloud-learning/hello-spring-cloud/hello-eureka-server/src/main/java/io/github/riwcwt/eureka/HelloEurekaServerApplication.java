package io.github.riwcwt.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HelloEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloEurekaServerApplication.class, args);
    }
}

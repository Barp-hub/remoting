package io.github.riwcwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HelloSpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringCloudApplication.class, args);
    }
}

package io.github.riwcwt.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HelloProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloProviderApplication.class, args);
    }
}

package io.github.riwcwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@EnableZipkinStreamServer
@SpringBootApplication
public class HelloZipkinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloZipkinServerApplication.class, args);
    }
}

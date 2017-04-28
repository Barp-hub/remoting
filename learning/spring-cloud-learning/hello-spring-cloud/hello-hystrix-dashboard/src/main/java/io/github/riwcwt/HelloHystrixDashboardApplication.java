package io.github.riwcwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
public class HelloHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloHystrixDashboardApplication.class, args);
    }
}

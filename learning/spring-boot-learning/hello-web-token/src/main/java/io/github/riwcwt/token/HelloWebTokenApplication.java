package io.github.riwcwt.token;

import com.btime.monitor.agent.Monitor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWebTokenApplication {

    public static void main(String[] args) {
        Monitor.builder().appName("test-application").apiPath("http://192.168.1.229").build().start();
        SpringApplication.run(HelloWebTokenApplication.class, args);
    }
}

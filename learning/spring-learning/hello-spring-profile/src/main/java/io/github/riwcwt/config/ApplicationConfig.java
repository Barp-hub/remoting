package io.github.riwcwt.config;

import io.github.riwcwt.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by michael on 2017-02-09.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    @Profile(value = "develop")
    public HelloService develop() {
        HelloService service = new HelloService();
        service.setEvnironment("develop");
        return service;
    }


    @Bean
    @Profile(value = "product")
    public HelloService product() {
        HelloService service = new HelloService();
        service.setEvnironment("product");
        return service;
    }
}

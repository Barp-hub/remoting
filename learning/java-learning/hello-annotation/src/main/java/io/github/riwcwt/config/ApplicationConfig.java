package io.github.riwcwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.github.riwcwt.annotation.EnableRemoting;

@EnableRemoting(name = "hello")
@Configuration
@ComponentScan(basePackages = { "io.github.riwcwt.service" })
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true)
public class ApplicationConfig {

	@Bean
	public ServiceDiscoverConfig service() {
		return new ServiceDiscoverConfig();
	}

}

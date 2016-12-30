package com.riwcwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.riwcwt.registry.ServiceRegistry;

@Configuration
public class ApplicationConfig {

	@Bean
	public ServiceRegistry registry() {
		ServiceRegistry registry = new ServiceRegistry();
		registry.setNamespace("service");
		registry.setThreadCount(5);
		registry.setZookeeperConnect("localhost:2181");
		return registry;
	}

}

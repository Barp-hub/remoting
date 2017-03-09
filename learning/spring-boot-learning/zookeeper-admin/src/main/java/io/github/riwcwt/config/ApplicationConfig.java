package io.github.riwcwt.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import io.github.riwcwt.service.ServiceRegistry;

@Configuration
@Import(ZookeeperConfig.class)
@ComponentScan(basePackages = { "io.github.riwcwt.service" })
public class ApplicationConfig {

	@Autowired
	private Environment environment = null;

	@Bean
	public CuratorFramework zookeeperClient() {
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(environment.getProperty("zookeeper.connect")).retryPolicy(new RetryNTimes(5, 1000)).namespace("service").build();
		client.start();
		return client;
	}

	@Bean
	public Executor pool() {
		return Executors.newFixedThreadPool(2);
	}

	@Bean
	public ServiceRegistry registry() {
		ServiceRegistry registry = new ServiceRegistry();
		registry.setNamespace("service");
		registry.setThreadCount(5);
		registry.setZookeeperConnect("localhost:2181");
		return registry;
	}
}

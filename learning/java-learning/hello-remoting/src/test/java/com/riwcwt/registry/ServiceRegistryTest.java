package com.riwcwt.registry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.riwcwt.config.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class ServiceRegistryTest {

	private String service = "io.github.riwcwt.service.ApplicationService";
	private String host = "localhost";
	private String port = "20880";

	@Autowired
	private ServiceRegistry serviceRegistry = null;

	@Test
	public void register() throws Exception {
		this.serviceRegistry.register(service, host, port);
	}

	@Test
	public void unregister() throws Exception {
		this.serviceRegistry.unregister(service, host, port);
	}

	@Test
	public void subscribe() throws Exception {
		this.serviceRegistry.subscribe(service);
		System.in.read();
	}
}

package io.github.riwcwt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.github.riwcwt.Main;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
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

}

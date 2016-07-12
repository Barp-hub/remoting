package io.github.riwcwt.proxy.factory;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.riwcwt.proxy.config.ProxyConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProxyConfig.class)
public class ProxyFactoryTest {

	@Autowired
	private ProxyFactory factory = null;

	@Test
	public void proxy() {
		HelloService helloService = (HelloService) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(), new Class<?>[] { HelloService.class }, factory);
		System.out.println(helloService.hello("world"));
	}

}

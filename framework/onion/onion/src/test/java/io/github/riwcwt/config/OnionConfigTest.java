package io.github.riwcwt.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.riwcwt.proxy.factory.DynamicProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OnionConfig.class)
public class OnionConfigTest {
	private static final Logger logger = LoggerFactory.getLogger(OnionConfigTest.class);

	@Autowired
	private DynamicProxy proxy = null;

	@Test
	public void context() {
		logger.info(proxy.toString());
	}

}

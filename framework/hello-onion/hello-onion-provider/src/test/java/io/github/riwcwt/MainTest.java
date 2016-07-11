package io.github.riwcwt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.riwcwt.config.ApplicationConfig;
import io.github.riwcwt.hello.HelloService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class MainTest {

	private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

	@Autowired
	private HelloService helloService = null;

	@Test
	public void main() {
		logger.info(helloService.hello("world"));
	}

}

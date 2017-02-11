package io.github.riwcwt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.riwcwt.entity.AuthUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Autowired
	private UserService userService = null;

	@Test
	public void addUser() {
		AuthUser user = new AuthUser();
		user.setUsername("zhujun");
		this.userService.addUser(user);

		logger.info("ID : " + user.getId());

	}

}

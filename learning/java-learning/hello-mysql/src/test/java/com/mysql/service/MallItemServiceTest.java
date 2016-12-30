package com.mysql.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.application.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class MallItemServiceTest {
	private static final Logger	logger			= LoggerFactory.getLogger(MallItemServiceTest.class);

	@Autowired
	private MallItemService		mallItemService	= null;

	@Test
	public void copy() {
		Long[] ids = new Long[] { 162L, 111L, 202L, 139L, 114L, 112L, 110L, 153L, 256L, 255L };
		for (Long id : ids) {
			mallItemService.copy(id, 50);
		}
		logger.info("拷贝完成!");
	}

}

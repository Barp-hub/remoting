package io.github.riwcwt.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.github.riwcwt.Main;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class ZookeeperServiceTest {
	private static final Logger	logger	= LoggerFactory.getLogger(ZookeeperServiceTest.class);

	@Autowired
	private ZookeeperService	zookeeper;

	@Autowired
	private CuratorFramework	client	= null;

	@Test
	public void node() throws Exception {
		logger.info("node cache...");
		zookeeper.node("/hello");
		System.in.read();
	}

	@Test
	public void children() throws Exception {
		zookeeper.children("/hello");
		System.in.read();
	}

	@Test
	public void change() throws Exception {
		client.setData().forPath("/hello", String.valueOf(new Date().getTime()).getBytes());
	}

	@Test
	public void changePath() throws Exception {
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < 5; i++) {
			String path = String.valueOf(new Date().getTime());
			list.add(path);
			client.create().creatingParentsIfNeeded().forPath("/hello/" + path);
			Thread.sleep(3000);
		}
		for (String line : list) {
			client.delete().deletingChildrenIfNeeded().forPath("/hello/" + line);
			Thread.sleep(3000);
		}
	}
}

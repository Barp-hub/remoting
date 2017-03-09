package io.github.riwcwt.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {
	@Autowired
	private CuratorFramework client = null;
	
	
}

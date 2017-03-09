package io.github.riwcwt.service;

import java.util.concurrent.Executor;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperService {
	private static final Logger	logger		= LoggerFactory.getLogger(ZookeeperService.class);

	@Autowired
	private CuratorFramework	client		= null;

	@Autowired
	private Executor			executor	= null;

	public NodeCache node(String path) throws Exception {
		if (client.checkExists().forPath(path) == null) {
			client.create().creatingParentsIfNeeded().forPath(path);
		}
		NodeCache node = new NodeCache(client, path, false);
		node.start(true);
		node.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				logger.info(node.getCurrentData().getPath() + " - " + new String(node.getCurrentData().getData()));
			}
		}, executor);
		return node;
	}

	public PathChildrenCache children(String path) throws Exception {
		if (client.checkExists().forPath(path) == null) {
			client.create().creatingParentsIfNeeded().forPath(path);
		}

		PathChildrenCache children = new PathChildrenCache(client, path, true);
		children.start(StartMode.POST_INITIALIZED_EVENT);
		children.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				switch (event.getType()) {
					case CHILD_ADDED:
						logger.info("CHILD_ADDED: " + event.getData().getPath());
						break;
					case CHILD_REMOVED:
						logger.info("CHILD_REMOVED: " + event.getData().getPath());
						break;
					case CHILD_UPDATED:
						logger.info("CHILD_UPDATED: " + event.getData().getPath());
						break;
					default:
						break;
				}
			}
		}, executor);

		return children;
	}
}

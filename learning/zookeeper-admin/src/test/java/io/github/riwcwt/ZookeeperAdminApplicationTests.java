package io.github.riwcwt;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.github.riwcwt.watcher.DataWatcher;
import io.github.riwcwt.watcher.SessionWatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class ZookeeperAdminApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(ZookeeperAdminApplicationTests.class);

	@Autowired
	private CuratorFramework client = null;

	@Autowired
	private ApplicationContext context = null;

	@Test
	public void add() throws Exception {
		String path = "/test";
		if (client.checkExists().forPath(path) == null) {
			client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
					.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path, "测试".getBytes());
		} else {
			client.setData().forPath(path, String.valueOf(new Date().getTime()).getBytes());
		}

		client.getData().usingWatcher(context.getBean(DataWatcher.class)).forPath(path);
		logger.info(context.getApplicationName());

		client.setData().forPath(path, String.valueOf(new Date().getTime()).getBytes());
	}

	@Test
	public void sessionListener() throws InterruptedException {
		client.getConnectionStateListenable().addListener(context.getBean(SessionWatcher.class));
		Thread.sleep(60 * 1000);
	}

	@SuppressWarnings("resource")
	@Test
	public void node() throws Exception {

		/**
		 * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
		 */
		ExecutorService pool = Executors.newFixedThreadPool(2);

		String path = "/cnode";
		if (client.checkExists().forPath(path) == null) {
			client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
					.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
					.forPath(path, String.valueOf(new Date().getTime()).getBytes());
		} else {
			client.setData().forPath(path, String.valueOf(new Date().getTime()).getBytes());
		}
		/**
		 * 监听数据节点的变化情况
		 */
		final NodeCache nodeCache = new NodeCache(client, path, false);
		nodeCache.start(true);
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				logger.info("Node data is changed, new data: " + new String(nodeCache.getCurrentData().getData()));
			}
		}, pool);

		/**
		 * 监听子节点的变化情况
		 */
		final PathChildrenCache childrenCache = new PathChildrenCache(client, path, true);
		childrenCache.start(StartMode.POST_INITIALIZED_EVENT);
		childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
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
		}, pool);

		Thread.sleep(5 * 60 * 1000);
		pool.shutdown();
	}

	@Test
	public void changeNode() throws Exception {
		String path = "/cnode" + "/child";
		if (client.checkExists().forPath(path) == null) {
			client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
		}
	}

	@Test
	public void changeNodeData() throws Exception {
		String path = "/cnode";
		if (client.checkExists().forPath(path) == null) {

		} else {
			client.setData().forPath(path, String.valueOf(new Date().getTime()).getBytes());
		}
	}
}

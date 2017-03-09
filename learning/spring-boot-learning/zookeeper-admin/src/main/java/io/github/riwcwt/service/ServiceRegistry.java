package io.github.riwcwt.service;

import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ServiceRegistry implements ApplicationContextAware, InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);

	public static final String PROVIDER = "/provider";
	public static final String CONSUMER = "/consumer";

	private ApplicationContext context = null;

	private String namespace = "service";
	private String zookeeperConnect = "localhost:2181";
	private int threadCount = 2;

	private ConcurrentSkipListSet<PathChildrenCache> children = null;
	/**
	 * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
	 */
	private ThreadPoolTaskExecutor threadPool = null;

	private CuratorFramework client = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		logger.info("application id : " + context.getId());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		threadPool = new ThreadPoolTaskExecutor();
		threadPool.setCorePoolSize(threadCount);
		threadPool.setWaitForTasksToCompleteOnShutdown(true);
		threadPool.initialize();

		client = CuratorFrameworkFactory.builder().connectString(this.zookeeperConnect)
				.retryPolicy(new RetryNTimes(5, 1000)).namespace(this.namespace).build();
		client.start();

		children = new ConcurrentSkipListSet<PathChildrenCache>();
	}

	public void register(String service, String host, String port) throws Exception {
		String address = "/" + host + ":" + port;
		if (client.checkExists().forPath(service + PROVIDER + address) == null) {
			client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
					.forPath(service + PROVIDER + address);
		}
	}

	public void unregister(String service, String host, String port) throws Exception {
		String address = "/" + host + ":" + port;
		if (client.checkExists().forPath(service + PROVIDER + address) != null) {
			client.delete().forPath(service + PROVIDER + address);
		}
	}

	public void subscribe(String service) throws Exception {
		PathChildrenCache childrenCache = new PathChildrenCache(client, service, true);
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
		}, threadPool);
		this.children.add(childrenCache);

	}

	public void unsubscribe(String service) {
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getZookeeperConnect() {
		return zookeeperConnect;
	}

	public void setZookeeperConnect(String zookeeperConnect) {
		this.zookeeperConnect = zookeeperConnect;
	}

	@Override
	public void destroy() throws Exception {
		for (PathChildrenCache child : children) {
			child.close();
		}
		this.threadPool.shutdown();
	}

}

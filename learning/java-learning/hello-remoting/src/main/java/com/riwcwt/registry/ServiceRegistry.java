package com.riwcwt.registry;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

	private List<PathChildrenCache> children = null;
	/**
	 * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
	 */
	private ThreadPoolTaskExecutor threadPool = null;

	private CuratorFramework client = null;

	/**
	 * 缓存的服务列表地址
	 */
	private List<ServiceNode> services = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		logger.info("application id : " + context.getId());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("初始化注册中...");
		threadPool = new ThreadPoolTaskExecutor();
		threadPool.setCorePoolSize(threadCount);
		threadPool.setWaitForTasksToCompleteOnShutdown(true);
		threadPool.initialize();

		client = CuratorFrameworkFactory.builder().connectString(this.zookeeperConnect)
				.retryPolicy(new RetryNTimes(5, 1000)).namespace(this.namespace).build();
		client.start();

		children = new LinkedList<PathChildrenCache>();

		services = new LinkedList<ServiceNode>();
	}

	public void register(String service, String host, String port) throws Exception {
		String address = "/" + host + ":" + port;
		if (client.checkExists().forPath("/" + service + PROVIDER + address) == null) {
			client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
					.forPath("/" + service + PROVIDER + address);
		}
	}

	public void unregister(String service, String host, String port) throws Exception {
		String address = "/" + host + ":" + port;
		if (client.checkExists().forPath("/" + service + PROVIDER + address) != null) {
			client.delete().forPath("/" + service + PROVIDER + address);
		}
	}

	public void subscribe(String service) throws Exception {
		PathChildrenCache childrenCache = new PathChildrenCache(client, "/" + service + PROVIDER, true);
		childrenCache.start(StartMode.POST_INITIALIZED_EVENT);
		childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				switch (event.getType()) {
				case CHILD_ADDED:
					logger.info("CHILD_ADDED: " + event.getData().getPath());
					List<String> children = client.getChildren().forPath("/" + service + PROVIDER);
					for (String child : children) {
						logger.info("CHILD : " + child);
					}
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

	public void unsubscribe(String service) throws Exception {
		PathChildrenCache childrenCache = new PathChildrenCache(client, "/" + service + PROVIDER, true);
		childrenCache.start(StartMode.POST_INITIALIZED_EVENT);

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
		logger.info("关闭注册中心...");
		for (PathChildrenCache child : children) {
			child.close();
		}
		this.threadPool.shutdown();
	}

}

class ServiceNode {
	private String service;
	private Set<String> providers;
	private Set<String> consumers;

	public ServiceNode(String service) {
		this.service = service;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Set<String> getProviders() {
		return providers;
	}

	public void setProviders(Set<String> providers) {
		this.providers = providers;
	}

	public Set<String> getConsumers() {
		return consumers;
	}

	public void setConsumers(Set<String> consumers) {
		this.consumers = consumers;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof ServiceNode) {
			ServiceNode node = ServiceNode.class.cast(object);
			if (node.getService().equalsIgnoreCase(this.service)) {
				return true;
			}
		}
		return false;
	}

}
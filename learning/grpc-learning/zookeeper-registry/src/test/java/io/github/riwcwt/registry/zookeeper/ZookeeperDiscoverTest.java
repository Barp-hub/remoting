package io.github.riwcwt.registry.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by michael on 2017-02-22.
 */
public class ZookeeperDiscoverTest {

    private CuratorFramework client = null;

    private ZookeeperRegistry registry = null;

    @Before
    public void init() throws Exception {
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();

        registry = new ZookeeperRegistry();
        registry.init(client, "service");
    }

    @Test
    public void watch() throws Exception {
        registry.watchService("contract", new ServiceListener() {
            public void update() {
                List<ServiceInstance<Instance>> instances = ZookeeperDiscoverTest.this.registry.getServiceInstances("contract");
                if (instances != null) {
                    for (ServiceInstance<Instance> instance : instances) {
                        System.out.println(instance.getPayload().toString());
                    }
                }
            }
        });

        Thread.sleep(90 * 1000);
    }

    @After
    public void close() throws IOException {
        registry.close();
        CloseableUtils.closeQuietly(client);
    }

}

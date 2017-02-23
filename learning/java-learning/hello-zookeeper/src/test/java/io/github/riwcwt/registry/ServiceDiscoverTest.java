package io.github.riwcwt.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by michael on 2017-02-22.
 */
public class ServiceDiscoverTest {

    private ServiceDiscover serviceDiscover = null;
    private CuratorFramework client = null;

    @Before
    public void init() throws Exception {
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();
        serviceDiscover = new ServiceDiscover();
        serviceDiscover.init(client, "services");
    }

    @Test
    public void discover() throws Exception {

        ServiceInstance<Instance> instance = serviceDiscover.getServiceInstance("service1");

        System.out.println(instance.getPayload().toString());
    }

    @Test
    public void instances() throws Exception {
        List<ServiceInstance<Instance>> instances = this.serviceDiscover.getInstances("service1");
        if (instances != null) {
            for (ServiceInstance<Instance> instance : instances) {
                System.out.println(instance.getPayload().toString());
            }
        }

        Thread.sleep(90 * 1000);
    }

    @After
    public void close() {
        serviceDiscover.close();
        CloseableUtils.closeQuietly(client);
    }
}

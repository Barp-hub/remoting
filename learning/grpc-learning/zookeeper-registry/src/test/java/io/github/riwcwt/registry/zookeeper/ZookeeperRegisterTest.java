package io.github.riwcwt.registry.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.apache.curator.x.discovery.UriSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by michael on 2017-02-23.
 */
public class ZookeeperRegisterTest {
    private CuratorFramework client = null;


    private ServiceInstanceBuilder<Instance> builder = null;

    private ZookeeperRegistry registry = null;

    @Before
    public void init() throws Exception {
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();

        Instance detail = new Instance();
        detail.setDescription("合约");
        detail.setVersion("1.0");

        builder = ServiceInstance.<Instance>builder()
                .name("contract")
                .port(12345)
                .address(InetAddress.getLocalHost().getHostAddress())
                .payload(detail)
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"));


        registry = new ZookeeperRegistry();
        registry.init(client, "service");
    }

    @Test
    public void register() throws Exception {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(10000);
            registry.registerService(builder.id(String.valueOf(i)).port(10000 + i).build());
        }
    }


    @After
    public void close() throws IOException {
        registry.close();
        CloseableUtils.closeQuietly(client);
    }

}

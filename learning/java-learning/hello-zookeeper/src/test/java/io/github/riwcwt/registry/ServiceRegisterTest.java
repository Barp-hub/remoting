package io.github.riwcwt.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by michael on 2017-02-22.
 */
public class ServiceRegisterTest {
    private ServiceRegister serviceRegister = null;
    private CuratorFramework client = null;

    @Before
    public void init() throws Exception {
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();

        serviceRegister = new ServiceRegister();
        serviceRegister.init(client, "services");
    }


    @Test
    public void register() throws Exception {

        ServiceInstance<Instance> instance = ServiceInstance.<Instance>builder()
                .name("service1")
                .port(12345)
                .address("192.168.1.100")   //address不写的话，会取本地ip
                .payload(new Instance("192.168.1.100", 12345, "Test.Service1"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();

        serviceRegister.registerService(instance);

        Thread.sleep(60 * 1000);

        serviceRegister.unregisterService(instance);
    }


    @After
    public void close() throws IOException {
        serviceRegister.close();
        CloseableUtils.closeQuietly(client);
    }
}

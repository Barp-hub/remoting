package io.github.riwcwt.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;

/**
 * Created by michael on 2017-02-22.
 */
public class ServiceRegister {

    private ServiceDiscovery<Instance> serviceDiscovery;
    private CuratorFramework client;

    public void init(CuratorFramework client, String basePath) throws Exception {
        this.client = client;
        JsonInstanceSerializer<Instance> serializer = new JsonInstanceSerializer<Instance>(Instance.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(Instance.class)
                .client(client)
                .serializer(serializer)
                .basePath(basePath)
                .build();
        serviceDiscovery.start();
    }

    public void registerService(ServiceInstance<Instance> serviceInstance) throws Exception {
        serviceDiscovery.registerService(serviceInstance);
    }

    public void unregisterService(ServiceInstance<Instance> serviceInstance) throws Exception {
        serviceDiscovery.unregisterService(serviceInstance);

    }

    public void updateService(ServiceInstance<Instance> serviceInstance) throws Exception {
        serviceDiscovery.updateService(serviceInstance);

    }

    public void close() throws IOException {
        serviceDiscovery.close();
    }

}

package io.github.riwcwt.grpc.registry.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.details.ServiceCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michael on 2017-03-02.
 */
public class ZookeeperRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);

    private JsonInstanceSerializer<Instance> serializer = null;
    private ServiceDiscovery<Instance> serviceDiscovery = null;

    private ConcurrentHashMap<String, ServiceCache<Instance>> serviceCaches = null;

    public void init(CuratorFramework client, String basePath) throws Exception {
        logger.debug("初始化注册中心，base path：" + basePath);
        //序列化工具
        this.serializer = new JsonInstanceSerializer<>(Instance.class);

        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(Instance.class).client(client).serializer(serializer).basePath(basePath).build();

        this.serviceDiscovery.start();

        this.serviceCaches = new ConcurrentHashMap<>();
    }

    public List<ServiceInstance<Instance>> getServiceInstances(String service) {
        ServiceCache<Instance> serviceCache = this.serviceCaches.get(service);
        if (serviceCache == null) {
            return null;
        }
        return serviceCache.getInstances();
    }


    public void watchService(String service, final ServiceListener listener) throws Exception {
        if (this.serviceCaches.get(service) == null) {
            ServiceCache<Instance> serviceCache = this.serviceDiscovery.serviceCacheBuilder().name(service).build();
            serviceCache.addListener(new ServiceCacheListener() {
                public void cacheChanged() {
                    listener.update();
                }

                public void stateChanged(CuratorFramework client, ConnectionState newState) {
                }
            });
            serviceCache.start();
            this.serviceCaches.put(service, serviceCache);
        }
    }

    public void unwatchService(String service) throws IOException {
        ServiceCache<Instance> serviceCache = this.serviceCaches.get(service);
        if (serviceCache == null) {
            return;
        }
        serviceCache.close();
        this.serviceCaches.remove(service);
    }

    public void registerService(ServiceInstance<Instance> serviceInstance) throws Exception {
        this.serviceDiscovery.registerService(serviceInstance);
    }

    public void unregisterService(ServiceInstance<Instance> serviceInstance) throws Exception {
        this.serviceDiscovery.unregisterService(serviceInstance);

    }

    public void updateService(ServiceInstance<Instance> serviceInstance) throws Exception {
        this.serviceDiscovery.updateService(serviceInstance);

    }

    public void close() throws IOException {
        Optional.ofNullable(serviceCaches).ifPresent(service -> {
            service.forEach((key, value) -> {
                try {
                    unwatchService(key);
                    Optional.ofNullable(value.getInstances()).ifPresent(instances -> {
                        instances.forEach(instance -> {
                            try {
                                unregisterService(instance);
                            } catch (Exception e) {
                                logger.warn("unregister service error!", e);
                            }
                        });
                    });
                } catch (IOException e) {
                    logger.warn("unwatch service error!", e);
                }
            });
        });
        this.serviceDiscovery.close();
    }

}

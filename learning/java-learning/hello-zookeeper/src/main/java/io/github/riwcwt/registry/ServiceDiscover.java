package io.github.riwcwt.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.details.ServiceCacheListener;
import org.apache.curator.x.discovery.strategies.RandomStrategy;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

/**
 * Created by michael on 2017-02-22.
 */
public class ServiceDiscover {

    private ServiceDiscovery<Instance> serviceDiscovery;
    private Map<String, ServiceProvider<Instance>> providers = Maps.newHashMap();
    private List<Closeable> closeableList = Lists.newArrayList();
    private Object lock = new Object();

    public void init(CuratorFramework client, String basePath) throws Exception {
        JsonInstanceSerializer<Instance> serializer = new JsonInstanceSerializer<Instance>(Instance.class);
        serviceDiscovery = ServiceDiscoveryBuilder
                .builder(Instance.class)
                .client(client)
                .basePath(basePath)
                .serializer(serializer)
                .build();

        serviceDiscovery.start();
    }

    public ServiceInstance<Instance> getServiceInstance(String serviceName) throws Exception {
        ServiceProvider<Instance> provider = providers.get(serviceName);
        if (provider == null) {
            synchronized (lock) {
                provider = providers.get(serviceName);
                if (provider == null) {
                    provider = serviceDiscovery.serviceProviderBuilder().
                            serviceName(serviceName).
                            providerStrategy(new RandomStrategy<Instance>())
                            .build();
                    provider.start();
                    closeableList.add(provider);
                    providers.put(serviceName, provider);
                }
            }
        }


        return provider.getInstance();
    }

    private ServiceCache<Instance> serviceCache = null;

    public List<ServiceInstance<Instance>> getInstances(String serviceName) throws Exception {
        serviceCache = serviceDiscovery.serviceCacheBuilder().name(serviceName).build();
        serviceCache.addListener(new ServiceCacheListener() {
            public void cacheChanged() {
                System.out.println("cache changed");

                for (ServiceInstance<Instance> instance : serviceCache.getInstances()) {
                    System.out.println(instance.getPayload().toString() + ":\t" + instance.buildUriSpec());
                }

            }

            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                System.out.println("state changed");

            }
        });

        //Thread.sleep(90 * 1000);
        serviceCache.start();


        return serviceCache.getInstances();
    }


    public synchronized void close() {
        for (Closeable closeable : closeableList) {
            CloseableUtils.closeQuietly(closeable);
        }
    }

}

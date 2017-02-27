package io.github.riwcwt.grpc.nameresolver;

import io.github.riwcwt.registry.zookeeper.Instance;
import io.github.riwcwt.registry.zookeeper.ServiceListener;
import io.github.riwcwt.registry.zookeeper.ZookeeperRegistry;
import io.grpc.Attributes;
import io.grpc.NameResolver;
import io.grpc.ResolvedServerInfo;
import io.grpc.ResolvedServerInfoGroup;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by michael on 2017-02-23.
 */
public class ZookeeperNameResolver extends NameResolver {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperNameResolver.class);

    private static final String BASE_PATH = "grpc";

    private Listener listener;

    private URI uri;

    private ZookeeperRegistry registry = null;

    public ZookeeperNameResolver(URI uri, ZookeeperRegistry registry/*CuratorFramework client*/) throws Exception {
        this.uri = uri;
        this.registry = registry;
    }

    @Override
    public String getServiceAuthority() {
        return this.uri.getAuthority();
    }

    @Override
    public void start(final Listener listener) {
        this.listener = listener;
        this.refresh();
        try {
            this.registry.watchService(uri.getAuthority(), new ServiceListener() {
                public void update() {
                    ZookeeperNameResolver.this.refresh();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("listening to service change error!!!", e);
        }
    }

    @Override
    public void refresh() {
        List<ServiceInstance<Instance>> instances = registry.getServiceInstances(uri.getAuthority());
        if (instances != null) {
            List<ResolvedServerInfoGroup> servers = new LinkedList<ResolvedServerInfoGroup>();
            ResolvedServerInfoGroup.Builder builder = ResolvedServerInfoGroup.builder();

            for (ServiceInstance<Instance> instance : instances) {
                ResolvedServerInfo server = new ResolvedServerInfo(new InetSocketAddress(instance.getAddress(), instance.getPort()));
                builder.add(server);
            }

            servers.add(builder.build());

            this.listener.onUpdate(servers, Attributes.EMPTY);
        } else {
            throw new RuntimeException("there is no provider for this service : " + uri.toString());
        }
    }

    @Override
    public void shutdown() {
        try {
            registry.close();
        } catch (IOException e) {
            logger.warn("close registry error!", e);
        }
    }
}

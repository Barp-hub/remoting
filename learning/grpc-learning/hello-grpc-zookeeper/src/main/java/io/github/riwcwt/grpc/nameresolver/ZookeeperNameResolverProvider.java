package io.github.riwcwt.grpc.nameresolver;

import com.google.common.base.Preconditions;
import io.github.riwcwt.registry.zookeeper.ZookeeperRegistry;
import io.grpc.Attributes;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import javax.annotation.Nullable;
import java.net.URI;

/**
 * Created by michael on 2017-02-23.
 */
public class ZookeeperNameResolverProvider extends NameResolverProvider {

    private static final String SCHEME = "zookeeper";

    private ZookeeperRegistry registry = null;

    public ZookeeperNameResolverProvider(ZookeeperRegistry registry) {
        //this.client = client;
        this.registry = registry;
    }

    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 5;
    }

    @Override
    @Nullable
    public NameResolver newNameResolver(URI uri, Attributes attributes) {
        if (uri.getScheme().equals(SCHEME)) {
            Preconditions.checkNotNull(uri.getAuthority(), "authority is not null");
            try {
                return new ZookeeperNameResolver(uri, registry);
            } catch (Exception e) {
                throw new RuntimeException("can not create zookeeper name resolver");
            }
        } else {
            throw new RuntimeException("the targetUri scheme must be zookeeper");
        }
    }

    @Override
    public String getDefaultScheme() {
        return SCHEME;
    }
}

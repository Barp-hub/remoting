package io.github.riwcwt.grpc.nameresolver;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import org.apache.curator.framework.CuratorFramework;

import javax.annotation.Nullable;
import java.net.URI;

/**
 * Created by michael on 2017-02-23.
 */
public class ZookeeperNameResolverProvider extends NameResolverProvider {

    private static final String SCHEME = "zookeeper";

    private CuratorFramework client;

    public ZookeeperNameResolverProvider(CuratorFramework client) {
        this.client = client;
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
                return new ZookeeperNameResolver(uri, client);
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

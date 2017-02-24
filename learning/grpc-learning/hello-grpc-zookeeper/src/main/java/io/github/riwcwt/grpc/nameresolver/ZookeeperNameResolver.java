package io.github.riwcwt.grpc.nameresolver;

import io.grpc.NameResolver;

import javax.annotation.concurrent.GuardedBy;

/**
 * Created by michael on 2017-02-23.
 */
public class ZookeeperNameResolver extends NameResolver {


    @GuardedBy("this")
    private boolean shutdown;
    @GuardedBy("this")
    private boolean resolving;
    @GuardedBy("this")
    private Listener listener;


    @Override
    public String getServiceAuthority() {
        return null;
    }

    @Override
    public void start(Listener listener) {

    }

    @Override
    public void shutdown() {

    }
}

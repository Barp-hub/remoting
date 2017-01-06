package io.github.riwcwt;

import com.coreos.jetcd.api.KVGrpc;
import com.coreos.jetcd.api.PutRequest;
import com.coreos.jetcd.api.PutResponse;
import com.coreos.jetcd.api.WatchCreateRequest;
import com.coreos.jetcd.api.WatchRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ApplicationTests {

    private ManagedChannel channel = null;

    @Before
    public void setup() {
        //ManagedChannel channel = NettyChannelBuilder.forTarget("server://localhost:11111")
        //        .nameResolverFactory(new ServerNameResolverProvider())
        //        .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance()).usePlaintext(true).build();

        channel = ManagedChannelBuilder.forAddress("localhost", 2379).usePlaintext(true).build();
    }

    @Test
    public void getKV() throws ExecutionException, InterruptedException {


        KVGrpc.KVFutureStub kvGrpc = KVGrpc.newFutureStub(channel);

        PutRequest request = PutRequest.newBuilder().setKey(ByteString.copyFrom("hello", Charset.forName("UTF-8")))
                .setValue(ByteString.copyFrom("world", Charset.forName("UTF-8"))).setLease(0).setPrevKv(false).build();

        ListenableFuture<PutResponse> response = kvGrpc.put(request);

        System.out.println(response.get().toString());

    }

    @Test
    public void watch() {
        WatchRequest.newBuilder().setCreateRequest(WatchCreateRequest.newBuilder().setKey(ByteString.copyFrom("hello", Charset.forName("UTF-8")))).build();
    }

    @After
    public void destroy() throws InterruptedException {
        channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
    }

}

package io.github.riwcwt.client.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

public class ClientHeaderInterceptor implements ClientInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ClientHeaderInterceptor.class);

    private static Metadata.Key<String> customHeadKey = Metadata.Key.of("custom_client_header_key",
            Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {
        return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                /* put custom header */
                headers.put(customHeadKey, "customRequestValue");
                super.start(new SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        /**
                         * if you don't need receive header from server, you can
                         * use {@link io.grpc.stub.MetadataUtils attachHeaders}
                         * directly to send header
                         */
                        logger.info("header received from server:" + headers);
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }

}

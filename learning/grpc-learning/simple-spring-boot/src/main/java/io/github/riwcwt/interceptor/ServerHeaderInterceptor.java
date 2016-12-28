package io.github.riwcwt.interceptor;

import io.github.riwcwt.grpc.annotation.GrpcGlobalInterceptor;
import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by michael on 2016-12-28.
 */
@GrpcGlobalInterceptor
public class ServerHeaderInterceptor implements ServerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ServerHeaderInterceptor.class);

    private static Metadata.Key<String> customHeadKey = Metadata.Key.of("custom_server_header_key", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata requestHeaders, ServerCallHandler<ReqT, RespT> next) {
        logger.info("header received from client:" + requestHeaders);
        return next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendHeaders(Metadata responseHeaders) {
                responseHeaders.put(customHeadKey, "customRespondValue");
                super.sendHeaders(responseHeaders);
            }
        }, requestHeaders);
    }

}

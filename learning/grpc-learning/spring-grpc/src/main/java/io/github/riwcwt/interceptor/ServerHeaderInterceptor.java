package io.github.riwcwt.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.grpc.annotation.GrpcGlobalInterceptor;
import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

@GrpcGlobalInterceptor
public class ServerHeaderInterceptor implements ServerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ServerHeaderInterceptor.class);

	private static Metadata.Key<String> customHeadKey = Metadata.Key.of("custom_server_header_key", Metadata.ASCII_STRING_MARSHALLER);

	@Override
	public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata requestHeaders, ServerCallHandler<ReqT, RespT> next) {
		logger.info("header received from client:" + requestHeaders);
		return next.startCall(new SimpleForwardingServerCall<ReqT, RespT>(call) {
			@Override
			public void sendHeaders(Metadata responseHeaders) {
				responseHeaders.put(customHeadKey, "customRespondValue");
				super.sendHeaders(responseHeaders);
			}
		}, requestHeaders);
	}

}

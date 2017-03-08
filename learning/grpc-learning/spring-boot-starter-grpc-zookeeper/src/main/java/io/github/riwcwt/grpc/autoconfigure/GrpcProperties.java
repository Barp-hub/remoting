package io.github.riwcwt.grpc.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by michael on 2017-03-01.
 */
@ConfigurationProperties(prefix = "grpc")
public class GrpcProperties {
    /**
     * gRPC server port
     */
    private int port = 6565;

    /**
     * 注册中心地址
     */
    private String registerCenter;

    /**
     * 服务的应用名称
     */
    private String serviceName;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRegisterCenter() {
        return registerCenter;
    }

    public void setRegisterCenter(String registerCenter) {
        this.registerCenter = registerCenter;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

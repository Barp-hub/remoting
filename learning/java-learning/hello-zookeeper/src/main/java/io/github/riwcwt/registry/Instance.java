package io.github.riwcwt.registry;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Created by michael on 2017-02-22.
 */
@JsonRootName("details")
public class Instance {
    private String host;
    private Integer port;
    private String service;

    public Instance() {
    }

    public Instance(String host, Integer port, String service) {
        this.host = host;
        this.port = port;
        this.service = service;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", service='" + service + '\'' +
                '}';
    }
}

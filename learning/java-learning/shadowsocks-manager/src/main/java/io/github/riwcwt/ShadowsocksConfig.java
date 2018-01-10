package io.github.riwcwt;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

public class ShadowsocksConfig {
    private String server;
    private Integer timeout;
    private String method;
    @JSONField(name = "port_password")
    private Map<String, String> port_password;
    @JSONField(name = "_comment")
    private Map<String, String> _comment;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getPort_password() {
        return port_password;
    }

    public void setPort_password(Map<String, String> port_password) {
        this.port_password = port_password;
    }

    public Map<String, String> get_comment() {
        return _comment;
    }

    public void set_comment(Map<String, String> _comment) {
        this._comment = _comment;
    }
}

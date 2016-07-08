package io.github.riwcwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.github.riwcwt.netty.config.NettyConfig;
import io.github.riwcwt.proxy.config.ProxyConfig;

@Configuration
@Import(value = { NettyConfig.class, ProxyConfig.class })
public class OnionConfig {

}

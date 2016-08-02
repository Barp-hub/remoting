package io.github.riwcwt.proxy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.github.riwcwt.proxy.factory" })
public class ProxyConfig {

}

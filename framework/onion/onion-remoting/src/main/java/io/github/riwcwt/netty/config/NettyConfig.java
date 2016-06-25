package io.github.riwcwt.netty.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.github.riwcwt.netty.client.codec", "io.github.riwcwt.netty.server.codec",
		"io.github.riwcwt.netty.serialize", "io.github.riwcwt.netty.server", "io.github.riwcwt.netty.client",
		"io.github.riwcwt.netty.listener" })
public class NettyConfig {

}

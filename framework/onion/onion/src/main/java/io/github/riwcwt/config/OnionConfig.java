package io.github.riwcwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.github.riwcwt.netty.config.NettyConfig;

@Configuration
@Import(value = { NettyConfig.class })
public class OnionConfig {

}

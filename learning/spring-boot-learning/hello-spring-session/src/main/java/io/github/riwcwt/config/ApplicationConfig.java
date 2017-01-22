package io.github.riwcwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by michael on 2017-01-22.
 */
@Configuration
@EnableRedisHttpSession
public class ApplicationConfig {
}

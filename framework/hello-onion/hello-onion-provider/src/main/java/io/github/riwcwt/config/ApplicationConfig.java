package io.github.riwcwt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { OnionConfig.class })
@ComponentScan(basePackages = { "io.github.riwcwt.hello" })
public class ApplicationConfig {

}

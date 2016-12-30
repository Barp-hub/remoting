package com.mysql.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(value = { DatabaseConfig.class })
@ComponentScan(basePackages = { "com.mysql.service", "com.mysql.util" })
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true)
public class ApplicationConfig {

}

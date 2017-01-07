package io.github.riwcwt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.github.riwcwt.beans.HelloBean;

@Configuration
@ComponentScan(basePackageClasses = { HelloBean.class })
public class ApplicationConfig {

}

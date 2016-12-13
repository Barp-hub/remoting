package io.github.riwcwt.config;

//import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
//import org.springframework.context.annotation.Bean;

import io.github.riwcwt.annotations.EnableRPC;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.io.UrlResource;
//
//import java.net.MalformedURLException;

/**
 * Created by michael on 2016-12-08.
 */
@Configuration
//@PropertySource(value = "${config.file}", ignoreResourceNotFound = true, encoding = "UTF-8")
@EnableRPC
public class ApplicationConfig {

    //@Bean
    //public static PropertyPlaceholderConfigurer properties() throws MalformedURLException {
    //    PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
    //    configurer.setLocation(new UrlResource("http://localhost/application.properties"));
    //    configurer.setIgnoreResourceNotFound(true);
    //    return configurer;
    //}

}

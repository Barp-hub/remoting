package io.github.riwcwt.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.github.riwcwt.web.interceptor.AccessInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private AccessInterceptor accessInterceptor = null;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessInterceptor);
		super.addInterceptors(registry);
	}

}

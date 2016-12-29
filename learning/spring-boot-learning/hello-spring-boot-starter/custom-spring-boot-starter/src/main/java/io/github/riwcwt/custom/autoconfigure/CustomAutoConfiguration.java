package io.github.riwcwt.custom.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.riwcwt.custom.CustomRunner;

@Configuration
public class CustomAutoConfiguration {

	@Bean
	public CustomRunner runner() {
		return new CustomRunner();
	}

}

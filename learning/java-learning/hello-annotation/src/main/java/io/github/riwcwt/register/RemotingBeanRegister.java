package io.github.riwcwt.register;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import io.github.riwcwt.Main;
import io.github.riwcwt.annotation.EnableRemoting;

@Configuration
public class RemotingBeanRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private ResourceLoader resourceLoader;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableRemoting.class.getName());
		for (String key : attributes.keySet()) {
			logger.info(key + " - " + attributes.get(key).toString());
		}

		String[] beans = registry.getBeanDefinitionNames();
		for (String bean : beans) {
			logger.info("bean : " + bean);
		}

		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Bean
	public Executor executor() {
		return Executors.newFixedThreadPool(2);
	}
}

package com.btime.im.server.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;

@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true)
@ComponentScan(basePackages = { "com.btime.server.netty", "com.btime.server.netty.codec",
		"com.btime.im.server.ons.listener" })
public class ApplicationConfig {

	@Autowired
	private Environment environment = null;

	@Autowired
	private MessageListener listener = null;

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public ConsumerBean consumer() {
		ConsumerBean consumer = new ConsumerBean();

		Properties properties = new Properties();
		properties.put("ConsumerId", environment.getProperty("ons.consumer.id"));
		properties.put("AccessKey", environment.getProperty("ons.access.key"));
		properties.put("SecretKey", environment.getProperty("ons.secret.key"));
		properties.put("ConsumeThreadNums", environment.getProperty("ons.consume.thread.number", Integer.class));
		consumer.setProperties(properties);

		Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();

		Subscription subscription = new Subscription();
		subscription.setTopic(environment.getProperty("ons.chat.message.topic"));
		subscription.setExpression(environment.getProperty("ons.chat.message.expression"));

		subscriptionTable.put(subscription, listener);

		consumer.setSubscriptionTable(subscriptionTable);

		return consumer;
	}

}

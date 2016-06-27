package com.btime.im.server.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.btime.im.server.ons.listener.ChatMessageListener;

@Configuration
@ComponentScan(basePackages = { "com.btime.server.netty", "com.btime.server.netty.codec" })
public class ApplicationConfig {

	@Autowired
	private Environment environment = null;

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public ConsumerBean consumer() {
		ConsumerBean consumer = new ConsumerBean();

		Properties properties = new Properties();
		properties.put("ConsumerId", "XXX");
		properties.put("AccessKey", "XXX");
		properties.put("SecretKey", "XXX");
		properties.put("ConsumeThreadNums", 50);
		consumer.setProperties(properties);

		Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();

		Subscription subscription = new Subscription();
		subscription.setTopic("Topic");
		subscription.setExpression("*");
		MessageListener listener = new ChatMessageListener();

		subscriptionTable.put(subscription, listener);

		consumer.setSubscriptionTable(subscriptionTable);

		return consumer;
	}

}

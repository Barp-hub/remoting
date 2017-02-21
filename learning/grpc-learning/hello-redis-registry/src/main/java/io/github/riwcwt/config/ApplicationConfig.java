package io.github.riwcwt.config;

import io.github.riwcwt.redis.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * Created by michael on 2017-02-21.
 */
@Configuration
public class ApplicationConfig {

    @Autowired
    private Environment environment = null;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory = null;

    @Autowired
    private RedisMessageSubscriber redisMessageSubscriber = null;

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(this.environment.getProperty("topic.channel"));
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(this.redisMessageSubscriber);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter messageListenerAdapter, ChannelTopic topic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(this.redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, topic);
        return container;
    }
}

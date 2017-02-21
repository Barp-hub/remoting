package io.github.riwcwt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

/**
 * Created by michael on 2017-02-21.
 */
@Component
public class RedisMessagePublisher {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ChannelTopic topic;


    public void publish(String message) {
        this.stringRedisTemplate.convertAndSend(topic.getTopic(), message);
    }

}

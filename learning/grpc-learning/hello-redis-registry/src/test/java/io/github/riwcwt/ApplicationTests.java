package io.github.riwcwt;

import io.github.riwcwt.redis.RedisMessagePublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

    @Test
    public void set() {
        this.stringRedisTemplate.opsForValue().set("name", "michael");
    }

    @Test
    public void publish() {
        this.redisMessagePublisher.publish("hello");
    }
}

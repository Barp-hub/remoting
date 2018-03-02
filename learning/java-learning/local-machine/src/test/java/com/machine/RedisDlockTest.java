package com.machine;

import com.machine.dlock.RedisDlock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class RedisDlockTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisDlockTest.class);

    private static JedisPool jedisPool;

    @BeforeClass
    public static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1024);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        String ip = "192.168.243.138";
        int port = 6379;
        int timeOut = 2000;
        jedisPool = new JedisPool(config, ip, port, timeOut);
    }

    @Test
    public void lock() throws IOException {
        String key = "key";

        ExecutorService executor = Executors.newFixedThreadPool(50);

        IntStream.range(1, 5).forEach(i -> executor.submit(() -> {
            String id = String.valueOf(Math.random());
            try {
                if (RedisDlock.tryGetDistributedLock(this.jedisPool.getResource(), key, id, 100000)) {
                    logger.info("task-" + i + ",获取锁成功");
                } else {
                    logger.info("task-" + i + ",获取锁失败");
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.info("task-" + i + ",获取锁异常", e);
            } finally {
                if (RedisDlock.releaseDistributedLock(this.jedisPool.getResource(), key, id)) {
                    logger.info("task-" + i + ",释放锁成功");
                } else {
                    logger.info("task-" + i + ",释放锁失败");
                }
            }
        }));

        System.in.read();

        executor.shutdown();
    }

}

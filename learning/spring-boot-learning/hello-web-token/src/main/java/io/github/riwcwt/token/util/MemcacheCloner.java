package io.github.riwcwt.token.util;

import com.sun.corba.se.spi.ior.ObjectKey;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by michael on 2017-07-10.
 */
public class MemcacheCloner {

    private static final Logger logger = LoggerFactory.getLogger(MemcacheCloner.class);

    private static ThreadPoolTaskExecutor taskExecutor = null;
    private static MemcachedClient client = null;

    static {
        try {
            //            client = new MemcachedClient(AddrUtil.getAddresses("m-bp1568937d5b65e4.memcache.rds.aliyuncs.com:11211"));
            AuthDescriptor authDescriptor = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler("ocs", "fWmjYYe9hPWENgddvoEYyIVn2thfv5"));

            client = new MemcachedClient(new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY) // uses Binary protocol
                    .setOpTimeout(1000)// set the time out 1000ms
                    .setAuthDescriptor(authDescriptor).build(), AddrUtil.getAddresses("m-bp16d0238e459c44.memcache.rds.aliyuncs.com:11211")); // server address

            taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(2);
            taskExecutor.setMaxPoolSize(10);
            taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

            taskExecutor.initialize();

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    taskExecutor.shutdown();
                }
            }, "memcache-cloner-shutdown-hook"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void set(final String key, final int exp, final Object o) {
        //        taskExecutor.execute(new Runnable() {
        //            @Override
        //            public void run() {
        //                if (client != null) {
        //                    if (logger.isDebugEnabled()) {
        //                        logger.debug("设置key：" + key);
        //                    }
        client.set(key, exp, o);
        //                }
        //            }
        //        });
    }

    public static Object get(final String key) {
        return client.get(key);
    }

    public static void delete(final String key) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (client != null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("删除key：" + key);
                    }
                    client.delete(key);
                }
            }
        });
    }
}

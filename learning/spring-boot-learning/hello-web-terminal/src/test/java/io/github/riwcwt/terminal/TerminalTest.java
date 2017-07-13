package io.github.riwcwt.terminal;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 2017-07-06.
 */
public class TerminalTest {

    @Test
    public void connect() {
    }

    @Test
    public void cache() throws InterruptedException, ExecutionException {
        Cache<String, String> cache = CacheBuilder
                .newBuilder().maximumSize(100).expireAfterWrite(5, TimeUnit.SECONDS)
                .build();

        cache.put("1", "hello");

        Thread.sleep(4000);

        System.out.println(cache.get("1", () -> "null"));
    }
}

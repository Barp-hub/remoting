package io.github.riwcwt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class HttpClientTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

    private ServiceRun run = new ServiceRun();

    @Test
    public void http() {

        ExecutorService executor = Executors.newFixedThreadPool(500);

        List<Future<Boolean>> list = new LinkedList<>();
        IntStream.range(1, 500).forEach(i -> list.add(executor.submit(() -> {

            IntStream.range(1, 30).forEach(value -> logger.info(run.runService("https://www.baidu.com/", null, true)));

            return true;
        })));

        list.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        });

        executor.shutdown();

    }

    @Test
    public void request() {
        logger.info(run.runService("https://www.baidu.com/", null, true));
    }

}

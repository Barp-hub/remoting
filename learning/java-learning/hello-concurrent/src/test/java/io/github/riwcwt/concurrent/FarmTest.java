package io.github.riwcwt.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 2017-03-13.
 */
public class FarmTest {

    private static final Logger logger = LoggerFactory.getLogger(FarmTest.class);

    private FarmManager manager = new FarmManager();

    @Test
    public void farm() throws IOException, InterruptedException {
        manager.init();

        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 10000; i++) {
            executor.execute(() -> {
                Farm farm = manager.getFarm((int) (Math.random() * 10));
                if (farm == null) {
                    logger.error("error farm!!!");
                }
            });
        }

        TimeUnit.SECONDS.sleep(30);

        executor.shutdownNow();
    }

}

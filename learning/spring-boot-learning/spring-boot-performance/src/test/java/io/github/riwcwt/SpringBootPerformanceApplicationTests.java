package io.github.riwcwt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

public class SpringBootPerformanceApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootPerformanceApplicationTests.class);

    @Test
    public void performance() throws InterruptedException {
        RestTemplate template = new RestTemplate();


        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1000);
        executor.setMaxPoolSize(2000);
        executor.setWaitForTasksToCompleteOnShutdown(true);

        executor.initialize();

        int count = 600;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    logger.info(template.getForObject("http://localhost:8080/performance", String.class));
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();
    }

}

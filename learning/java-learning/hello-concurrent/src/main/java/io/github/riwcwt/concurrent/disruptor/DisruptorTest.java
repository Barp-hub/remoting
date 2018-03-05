package io.github.riwcwt.concurrent.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class DisruptorTest {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorTest.class);

    public static void main(String[] args) throws IOException {

        DisruptorService disruptor = new DisruptorService();
        disruptor.init();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<Boolean>> list = new LinkedList<>();

        IntStream.range(1, 20).forEach(i -> list.add(executor.submit(() -> {
            disruptor.publish("测试-" + i);
            return true;
        })));

        list.forEach(future -> {
            try {
                logger.info("发布结果：" + future.get().toString());
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(), e);
            }
        });

//        System.in.read();

        executor.shutdown();
        disruptor.close();
    }

}

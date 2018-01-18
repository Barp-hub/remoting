package io.github.riwcwt.concurrent.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class BlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);

        Thread producer = new Thread(() -> IntStream.range(1, 20).forEach(n -> {
            try {
                queue.put(String.valueOf(n));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        Thread consumer = new Thread(() -> IntStream.range(1, 20).forEach(value -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        consumer.start();
        producer.start();

    }

}

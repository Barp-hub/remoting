package com.machine.tricky;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ThreadLocalMain {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(50);

        List<Future<Boolean>> list = new LinkedList<>();
        IntStream.range(0, 100).forEach(i -> list.add(executor.submit(() -> {
            Content content = new Content();
            ContentContext.set(content);

            Thread.sleep(60000);

            return true;
        })));

        list.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }

    public static class ContentContext {
        private static ThreadLocal<Content> threadLocal = new ThreadLocal<>();

        public static void set(Content content) {
            threadLocal.set(content);
        }

        public static Content get() {
            return threadLocal.get();
        }
    }

    public static class Content {
        //10m
        byte data[] = new byte[1024 * 1024 * 10];

        @Override
        protected void finalize() throws Throwable {
            System.out.println(" one content is released!");
        }
    }

}

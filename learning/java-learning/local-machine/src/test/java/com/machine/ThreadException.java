package com.machine;

import org.junit.Test;

import java.util.UUID;

public class ThreadException {

    @Test
    public void exception() throws InterruptedException {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("runtime error!");

        });
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getName());
            System.out.println(e.getMessage());
        });
        thread.start();

        Thread boy = new Thread(() -> {
            throw new RuntimeException("bad boy!");
        });
        boy.setUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getName());
            System.out.println(e.getMessage());
        });
        boy.start();

        thread.join();
        boy.join();
    }

    @Test
    public void random() {
        System.out.println(UUID.randomUUID().toString());
    }
}

package com.machine.thread;

import org.junit.Test;

public class ThreadTest {

    private int a = 0;
    private int b = 0;

    private int x = 5;
    private int y = 5;

    @Test
    public void thread() throws InterruptedException {
        Thread one = new Thread(() -> {
            a = 1;
            x = b;
            print(x, y);
        }, "one");
        Thread two = new Thread(() -> {
            b = 2;
            y = a;
            print(x, y);
        }, "two");

        one.start();
        two.start();

        one.join();
        two.join();

        print(x, y);
    }

    private void print(int i, int j) {
        System.out.println(Thread.currentThread().getName() + "-" + i + "-" + j);
    }
}

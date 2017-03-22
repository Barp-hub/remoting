package io.github.riwcwt.concurrent;

/**
 * Created by michael on 2017-03-22.
 */
public class JoinCountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(() -> {
        });
        Thread parser2 = new Thread(() -> System.out.println("parser2 finish"));
        parser1.start();
        parser2.start();
        parser1.join();
        parser2.join();
        System.out.println("all parser finish");
    }
}

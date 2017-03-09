package io.github.riwcwt;

import java.util.function.Supplier;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) {
        Supplier<Runnable> supplier = () -> () -> {
            System.out.println("hello, lambda!");
        };

        Thread thread=new Thread(supplier.get());
        thread.start();
    }
}

package io.github.riwcwt.hello;

/**
 * Created by michael on 2017-05-27.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("dubbo.registery.center = " + System.getenv("dubbo.registery.center"));

        System.setProperty("dubbo.registery.center", System.getenv("dubbo.registery.center"));

        com.alibaba.dubbo.container.Main.main(null);
    }

}

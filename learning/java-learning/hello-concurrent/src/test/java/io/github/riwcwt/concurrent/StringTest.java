package io.github.riwcwt.concurrent;

import org.junit.Test;

/**
 * Created by michael on 2017-06-13.
 */
public class StringTest {

    @Test
    public void equal() {
        String i = "hello";
        String j = "hello";

        System.out.println("i = j : " + (i == j));

        j = new String("hello");
        System.out.println("i = j : " + (i == j));
    }

    @Test
    public void intern() {
        String hello = "Hello", lo = "lo";
        System.out.print((hello == "Hello") + " ");
        System.out.print((Other.hello == hello) + " ");
        System.out.print((hello == ("Hel" + "lo")) + " ");
        System.out.print((hello == ("Hel" + lo)) + " ");
        System.out.println(hello == ("Hel" + lo).intern());
    }
}

class Other {
    static String hello = "Hello";
}

package io.github.riwcwt.concurrent;

import org.junit.Test;

/**
 * Created by michael on 2017-06-12.
 */
public class IntegerTest {

    @Test
    public void swap() {
        Integer i = 1;
        Integer j = 2;

        swap(i, j);

        System.out.println("i = " + i);
        System.out.println("j = " + j);
    }

    public void swap(Integer i, Integer j) {
        Integer temp = i;
        i = j;
        j = i;
    }

    @Test
    public void equal() {
        Integer i = 10;
        Integer j = 10;

        System.out.println("i = j : " + (i == j));

        j = Integer.valueOf(10);
        System.out.println("i = j : " + (i == j));

        i = 128;
        j = 128;
        System.out.println("i = j : " + (i == j));
    }
}

package io.github.riwcwt.concurrent;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by michael on 2017-06-12.
 */
public class IntegerTest {

    @Test
    public void swap() throws NoSuchFieldException, IllegalAccessException {
        Integer i = 1;
        Integer j = 2;

        swap(i, j);

        System.out.println("i = " + i);
        System.out.println("j = " + j);

        Integer k = 1;
        System.out.println("k = " + k);
    }

    public void swap(Integer i, Integer j) throws NoSuchFieldException, IllegalAccessException {

        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);

        int k = i.intValue();

        field.setInt(i, j.intValue());

        field.setInt(j, k);
        //        Integer temp = i;
        //        i = j;
        //        j = i;
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

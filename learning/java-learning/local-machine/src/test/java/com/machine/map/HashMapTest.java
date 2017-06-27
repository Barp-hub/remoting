package com.machine.map;

import org.junit.Test;

/**
 * Created by michael on 2017-06-16.
 */
public class HashMapTest {

    @Test
    public void map() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < 1024; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }

        map.print();
    }

}

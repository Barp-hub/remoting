package com.machine.map;

/**
 * Created by michael on 2017-06-15.
 */
public interface Map<K, V> {

    V put(K key, V value);

    V get(K key);

    int size();

    interface Entry<K, V> {

        K getKey();

        V getValue();
    }

}

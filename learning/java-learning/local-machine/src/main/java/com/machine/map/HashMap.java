package com.machine.map;

/**
 * Created by michael on 2017-06-15.
 */
public class HashMap<K, V> implements Map<K, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size = 0;

    private Node<K, V>[] array;

    @Override
    public V put(K key, V value) {
        int index = index(key);
        Node<K, V> entry = array[index];
        if (entry == null) {
            array[index] = new Node<K, V>(index, key, value, null);
            size++;
        } else {
            while (true) {
                if (entry.getKey() == key) {
                    entry.value = value;
                    break;
                }
                if (entry.next == null) {
                    entry.next = new Node<K, V>(index, key, value, null);
                    size++;
                    break;
                }
                entry = entry.next;
            }
        }
        return value;
    }

    @Override
    public V get(K key) {
        int index = index(key);
        Node<K, V> entry = array[index];
        if (entry == null) {
            return null;
        } else {
            while (true) {
                if (entry.getKey() == key) {
                    return entry.value;
                }
                if (entry.next == null) {
                    return null;
                }
                entry = entry.next;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int index(Object key) {
        return hash(key) % DEFAULT_INITIAL_CAPACITY;
    }

    class Node<K, V> implements Map.Entry<K, V> {

        final int hash;
        final K key;
        V value;

        Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }
}

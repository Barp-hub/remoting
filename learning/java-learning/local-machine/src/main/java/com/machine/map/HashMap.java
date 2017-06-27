package com.machine.map;

/**
 * Created by michael on 2017-06-15.
 */
public class HashMap<K, V> implements Map<K, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size = 0;

    private Node<K, V>[] array;

    public HashMap() {
        array = new Node[DEFAULT_INITIAL_CAPACITY];
    }

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

        if (size > array.length * DEFAULT_LOAD_FACTOR) {
            resize();
        }
        return value;
    }

    private void resize() {
        if (this.array == null) {
            this.array = new Node[DEFAULT_INITIAL_CAPACITY];
        } else {
            Node<K, V>[] old = array;
            this.array = new Node[this.array.length * 2];
            for (Node node : old) {
                Node current = node;
                while (current != null) {
                    Node temp = current;
                    int index = index(temp.getKey());
                    temp.next = this.array[index];
                    this.array[index] = temp;
                    current = current.next;
                }
            }
        }
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

    public void print() {
        if (this.array != null) {
            for (Node node : this.array) {
                Node current = node;
                while (current != null) {
                    System.out.print(current.getKey() + " ");
                    current = current.next;
                }
            }
            System.out.println();
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
        return hash(key) % this.array.length;
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

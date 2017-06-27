package io.github.riwcwt.tree;

import org.junit.Test;

/**
 * Created by michael on 2017-06-20.
 */
public class BTreeTest {

    @Test
    public void add() {
        BTree<Integer> tree = new BTree<>();
        tree.add(50);
        tree.add(20);
        tree.add(55);
        tree.add(53);
        tree.print();
    }

}

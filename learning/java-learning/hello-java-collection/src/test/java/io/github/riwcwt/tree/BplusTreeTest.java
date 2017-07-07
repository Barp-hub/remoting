package io.github.riwcwt.tree;

import org.junit.Test;

/**
 * Created by michael on 2017-07-04.
 */
public class BplusTreeTest {

    @Test
    public void tree() {

        BplusTree tree = new BplusTree(3);

        for (int i = 0; i < 6; i++) {
            tree.insertOrUpdate(i, i);
        }
    }

}

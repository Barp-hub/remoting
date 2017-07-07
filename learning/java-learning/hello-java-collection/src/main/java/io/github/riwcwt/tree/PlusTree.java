package io.github.riwcwt.tree;

/**
 * Created by michael on 2017-07-04.
 */
public interface PlusTree {
    Object get(Comparable key);   //查询

    void remove(Comparable key);    //移除

    void insertOrUpdate(Comparable key, Object obj); //插入或者更新，如果已经存在，就更新，否则插入
}

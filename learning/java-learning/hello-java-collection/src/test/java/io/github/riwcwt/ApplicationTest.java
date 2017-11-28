package io.github.riwcwt;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ApplicationTest {

    @Test
    public void arrayList() {
        //List基本操作
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Tom");
        arrayList.add("Jerry");
        arrayList.add("Micky");
        // 使用Iterator遍历元素
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String str = it.next();
            System.out.println(str);
        }
        // 在指定位置插入元素
        arrayList.add(2, "Kate");
        // 通过索引直接访问元素
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
        List<String> subList = new ArrayList<String>();
        subList.add("Mike");
        // addAll(Collection<? extends String> c)添加所给集合中的所有元素
        arrayList.addAll(subList);
        // 判断是否包含某个元素
        if (arrayList.contains("Mike")) {
            System.out.println("Mike is include in the list");
        }

        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.addAll(arrayList);
        // 获取指定元素
        System.out.println(linkedList.get(4));
        // 获取第一个元素
        System.out.println(linkedList.getFirst());
        // 获取最后一个元素
        System.out.println(linkedList.getLast());
        // 获取并删除第一个元素
        System.out.println(linkedList.pollFirst());

        // 获取，但不移除第一个元素
        System.out.println(linkedList.peekFirst());
    }

    @Test
    public void array() {
        int[] data = IntStream.range(0, 10).toArray();
        IntStream.of(data).forEach(System.out::println);

        System.out.println("---------------");
        System.arraycopy(data, 0, data, 5, 3);
        IntStream.of(data).forEach(System.out::println);
    }

}

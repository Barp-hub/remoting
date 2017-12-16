package io.github.riwcwt.jvm.gc;

import java.lang.ref.WeakReference;

public class ReferenceTest {

    public static void main(String[] args) {
        String content = new String("这是一段文本！");

        WeakReference<String> reference = new WeakReference<>(content);

        content = null;

        System.out.println(reference.get());

        System.gc();

        System.out.println(reference.get());
    }

}

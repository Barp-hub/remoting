package io.github.riwcwt.concurrent;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 2017-06-13.
 */
public class ReferencesTest {

    private WeakReference<Map<Integer, String>> myMap;

    public static void main(String[] args) {
        new ReferencesTest().doFunction();
    }

    private void doFunction() {

        Map<Integer, String> map = new HashMap<Integer, String>();
        myMap = new WeakReference<Map<Integer, String>>(map);

        map = null;
        int i = 0;
        while (true) {
            if (myMap != null && myMap.get() != null) {
                myMap.get().put(i++, "test" + i);

                System.out.println("im still working!!!!");
            } else {

                System.out
                        .println("*******im free*******");

            }

        }
    }

}

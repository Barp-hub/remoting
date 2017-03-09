package io.github.riwcwt.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 2017-03-09.
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}

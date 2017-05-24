package io.github.riwcwt.concurrent.disruptor;

/**
 * Created by michael on 2017-04-17.
 */
public class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}

package io.github.riwcwt.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by michael on 2017-04-17.
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}

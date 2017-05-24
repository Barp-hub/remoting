package io.github.riwcwt.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by michael on 2017-04-17.
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event : " + event.getValue() + "   sequence : " + sequence + "    endOfBatch : " + endOfBatch);
    }
}

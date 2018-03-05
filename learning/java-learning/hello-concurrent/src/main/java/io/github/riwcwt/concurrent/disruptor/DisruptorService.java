package io.github.riwcwt.concurrent.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class DisruptorService {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorService.class);

    private RingBuffer<List<String>> ringBuffer = null;

    private Disruptor<List<String>> disruptor = null;

    private EventTranslatorOneArg<List<String>, String> translator = (event, sequence, data) -> event.add(data);

    public void init() {

        disruptor = new Disruptor<>(LinkedList<String>::new, 8
                , DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());

        WorkHandler<List<String>>[] handlers = new WorkHandler[2];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = (List<String> list) -> list.forEach(event -> {
                logger.info("收到事件：" + event);
                throw new RuntimeException("抛出异常：" + event);
            });
        }

        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        disruptor.handleEventsWithWorkerPool(handlers);

        ringBuffer = disruptor.start();
    }

    public void publish(String information) {
        this.ringBuffer.publishEvent(translator, information);
    }

    public void close() {
        this.disruptor.shutdown();
    }

}

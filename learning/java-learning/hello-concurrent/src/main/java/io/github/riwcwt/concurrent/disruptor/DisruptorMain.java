package io.github.riwcwt.concurrent.disruptor;

/**
 * @description disruptor代码样例。每10ms向disruptor中插入一个元素，消费者读取数据，并打印到终端
 */

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

public class DisruptorMain {
    public static void main(String[] args) throws Exception {
        // 队列中的元素
        class Element {

            private int value;

            public int get() {
                return value;
            }

            public void set(int value) {
                this.value = value;
            }

        }

        // 生产者的线程工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "simpleThread");
            }
        };

        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<Element> factory = new EventFactory<Element>() {
            @Override
            public Element newInstance() {
                return new Element();
            }
        };

        // 处理Event的handler
        EventHandler<Element> handler = new EventHandler<Element>() {
            @Override
            public void onEvent(Element element, long sequence, boolean endOfBatch) {
                System.out.println(Thread.currentThread().getName() + Thread.currentThread().getId() + " Element: " + element.get());
            }
        };

        // 阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        // 指定RingBuffer的大小
        int bufferSize = 16;

        // 创建disruptor，采用单生产者模式
        Disruptor<Element> disruptor = new Disruptor(factory, bufferSize, threadFactory, ProducerType.MULTI, strategy);

        // 设置EventHandler
        disruptor.handleEventsWith(handler);

        // 启动disruptor的线程
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        //        for (int l = 0; l < 1000; l++) {
        //            // 获取下一个可用位置的下标
        //            long sequence = ringBuffer.next();
        //            try {
        //                // 返回可用位置的元素
        //                Element event = ringBuffer.get(sequence);
        //                // 设置该位置元素的值
        //                event.set(l);
        //            } finally {
        //                ringBuffer.publish(sequence);
        //            }
        //            Thread.sleep(10);
        //        }

        List<Thread> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int l = 0; l < 1000; l++) {
                    // 获取下一个可用位置的下标
                    long sequence = ringBuffer.next();
                    try {
                        // 返回可用位置的元素
                        Element event = ringBuffer.get(sequence);
                        // 设置该位置元素的值
                        event.set(l);
                    } finally {
                        ringBuffer.publish(sequence);
                    }

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            list.add(thread);
        }
        for (Thread t : list) {
            t.join();
        }
        disruptor.shutdown();
    }

}

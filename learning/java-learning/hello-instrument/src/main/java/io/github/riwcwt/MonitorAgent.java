package io.github.riwcwt;

import java.lang.instrument.Instrumentation;

/**
 * Hello world!
 */
public class MonitorAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Hi, I'm agent!");
        instrumentation.addTransformer(new MonitorTransformer());
    }
}

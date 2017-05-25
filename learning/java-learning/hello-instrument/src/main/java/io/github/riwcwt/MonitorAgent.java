package io.github.riwcwt;

import java.lang.instrument.Instrumentation;

/**
 * Hello world!
 */
public class MonitorAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Hi, I'm agent premain!");
        instrumentation.addTransformer(new MonitorTransformer());
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        System.out.println("Hi, I'm agent main!");
        instrumentation.addTransformer(new MonitorTransformer());
    }
}

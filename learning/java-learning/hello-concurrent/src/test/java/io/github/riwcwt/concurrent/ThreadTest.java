package io.github.riwcwt.concurrent;

import com.sun.tools.attach.VirtualMachine;
import org.junit.Test;

import java.util.stream.Stream;

public class ThreadTest {

    @Test
    public void trace() {
        Thread.getAllStackTraces().forEach((thread, stackTraceElements) -> {
            System.out.printf("%-20s \t %s \t %d \t %s\n", thread.getName(), thread.getState(), thread.getPriority(), thread.isDaemon() ? "Daemon" : "Normal");
            Stream.of(stackTraceElements).forEach(stack -> System.out.println(new StringBuilder().append("\tat ").append(stack.getClassName()).append(".").append(stack.getMethodName()).append("(").append(stack.getFileName()).append(":").append(stack.getLineNumber()).append(")").toString()));
        });
    }

    @Test
    public void machine() {
        VirtualMachine.list().forEach(descriptor -> {
            System.out.println(descriptor.provider().name());
            System.out.println(descriptor.provider().type());
        });

        System.out.println(HeapHisto.getHisto());
    }

}

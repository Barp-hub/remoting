package com.machine.design.pattern.structural.proxy;

/**
 * Created by michael on 2017-06-26.
 */
public class ProxyPatternTest {

    public static void main(String[] args) {
        CommandExecutor executor = new CommandExecutorProxy("Pankaj", "wrong_pwd");
        try {
            executor.runCommand("ls -ltr");
            executor.runCommand(" rm -rf abc.pdf");
        } catch (Exception e) {
            System.out.println("Exception Message::" + e.getMessage());
        }
    }

}

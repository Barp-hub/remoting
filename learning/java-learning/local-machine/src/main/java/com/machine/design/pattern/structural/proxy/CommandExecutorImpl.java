package com.machine.design.pattern.structural.proxy;

/**
 * Created by michael on 2017-06-26.
 */
public class CommandExecutorImpl implements CommandExecutor {
    @Override
    public void runCommand(String cmd) throws Exception {
        //some heavy implementation
        //        Runtime.getRuntime().exec(cmd);
        System.out.println("'" + cmd + "' command executed.");
    }
}

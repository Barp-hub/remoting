package com.machine.design.pattern.behavioral.command;

/**
 * Created by michael on 2017-06-29.
 */
public class FileInvoker {
    public Command command;

    public FileInvoker(Command c) {
        this.command = c;
    }

    public void execute() {
        this.command.execute();
    }
}
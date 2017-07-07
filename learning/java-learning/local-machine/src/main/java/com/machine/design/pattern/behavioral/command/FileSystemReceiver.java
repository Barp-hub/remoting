package com.machine.design.pattern.behavioral.command;

/**
 * Created by michael on 2017-06-29.
 */
public interface FileSystemReceiver {

    void openFile();

    void writeFile();

    void closeFile();
}

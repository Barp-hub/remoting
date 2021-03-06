package com.machine.design.pattern.behavioral.command;

/**
 * Created by michael on 2017-06-29.
 */
public class UnixFileSystemReceiver implements FileSystemReceiver {
    @Override
    public void openFile() {
        System.out.println("Opening file in unix OS");
    }

    @Override
    public void writeFile() {
        System.out.println("Writing file in unix OS");
    }

    @Override
    public void closeFile() {
        System.out.println("Closing file in unix OS");
    }
}
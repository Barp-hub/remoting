package com.machine.design.pattern.behavioral.memento;

/**
 * Created by michael on 2017-07-03.
 */
public class FileWriterCaretaker {
    private Object obj;

    public void save(FileWriterUtil fileWriter) {
        this.obj = fileWriter.save();
    }

    public void undo(FileWriterUtil fileWriter) {
        fileWriter.undoToLastSave(obj);
    }
}
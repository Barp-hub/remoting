package com.machine.design.pattern.behavioral.observer;

/**
 * Created by michael on 2017-06-27.
 */
public interface Subject {
    //methods to register and unregister observers
    public void register(Observer obj);

    public void unregister(Observer obj);

    //method to notify observers of change
    public void notifyObservers();

    //method to get updates from subject
    public Object getUpdate(Observer obj);
}
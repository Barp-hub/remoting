package com.machine.design.pattern.behavioral.observer;

/**
 * Created by michael on 2017-06-27.
 */
public interface Observer {
    //method to update the observer, used by subject
    public void update();
    //attach with subject to observe
    public void setSubject(Subject sub);
}
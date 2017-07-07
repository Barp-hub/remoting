package com.machine.design.pattern.behavioral.mediator;

/**
 * Created by michael on 2017-06-27.
 */
public interface ChatMediator {

    public void sendMessage(String msg, User user);

    void addUser(User user);

}

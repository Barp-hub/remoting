package com.machine.design.pattern.behavioral.mediator;

/**
 * Created by michael on 2017-06-27.
 */
public class UserImpl extends User {
    public UserImpl(ChatMediator med, String name) {
        super(med, name);
    }

    @Override
    public void send(String msg) {
        System.out.println(this.name + ": Sending Message=" + msg);
        mediator.sendMessage(msg, this);
    }

    @Override
    public void receive(String msg) {
        System.out.println(this.name + ": Received Message:" + msg);
    }
}
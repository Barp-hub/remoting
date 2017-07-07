package com.machine.design.pattern.behavioral.mediator;

/**
 * Created by michael on 2017-06-27.
 */
public abstract class User {
    protected ChatMediator mediator;
    protected String name;

    public User(ChatMediator med, String name) {
        this.mediator = med;
        this.name = name;
    }

    public abstract void send(String msg);

    public abstract void receive(String msg);
}
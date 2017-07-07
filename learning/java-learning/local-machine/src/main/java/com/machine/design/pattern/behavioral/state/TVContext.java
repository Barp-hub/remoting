package com.machine.design.pattern.behavioral.state;

/**
 * Created by michael on 2017-07-03.
 */
public class TVContext implements State {
    private State tvState;
    public void setState(State state) {
        this.tvState=state;
    }
    public State getState() {
        return this.tvState;
    }
    @Override
    public void doAction() {
        this.tvState.doAction();
    }
}
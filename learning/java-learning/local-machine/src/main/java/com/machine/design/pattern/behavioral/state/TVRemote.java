package com.machine.design.pattern.behavioral.state;

/**
 * Created by michael on 2017-07-03.
 */
public class TVRemote {
    public static void main(String[] args) {
        TVContext context = new TVContext();
        State tvStartState = new TVStartState();
        State tvStopState = new TVStopState();
        context.setState(tvStartState);
        context.doAction();
        context.setState(tvStopState);
        context.doAction();
    }
}

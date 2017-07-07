package com.machine.design.pattern.behavioral.state;

/**
 * Created by michael on 2017-07-03.
 */
public class TVStopState implements State {
    @Override
    public void doAction() {
        System.out.println("TV is turned OFF");
    }
}
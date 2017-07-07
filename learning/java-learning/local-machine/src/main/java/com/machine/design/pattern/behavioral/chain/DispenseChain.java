package com.machine.design.pattern.behavioral.chain;

/**
 * Created by michael on 2017-06-27.
 */
public interface DispenseChain {
    void setNextChain(DispenseChain nextChain);

    void dispense(Currency cur);
}

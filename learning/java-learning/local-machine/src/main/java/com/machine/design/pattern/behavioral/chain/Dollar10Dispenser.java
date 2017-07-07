package com.machine.design.pattern.behavioral.chain;

/**
 * Created by michael on 2017-06-27.
 */
public class Dollar10Dispenser implements DispenseChain {
    private DispenseChain chain;

    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void dispense(Currency cur) {
        if (cur.getAmount() >= 10) {
            int num = cur.getAmount() / 10;
            int remainder = cur.getAmount() % 10;
            System.out.println("Dispensing " + num + " 10$ note");
            if (remainder != 0) this.chain.dispense(new
                    Currency(remainder));
        } else {
            this.chain.dispense(cur);
        }
    }
}
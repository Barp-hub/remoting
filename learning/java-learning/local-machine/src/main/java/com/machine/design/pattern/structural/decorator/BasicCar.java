package com.machine.design.pattern.structural.decorator;

/**
 * Created by michael on 2017-06-27.
 */
public class BasicCar implements Car {
    @Override
    public void assemble() {
        System.out.print("Basic Car.");
    }
}
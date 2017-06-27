package com.machine.design.pattern.structural.decorator;

/**
 * Created by michael on 2017-06-27.
 */
public class LuxuryCar extends CarDecorator {
    public LuxuryCar(Car c) {
        super(c);
    }
    @Override
    public void assemble(){
        car.assemble();
        System.out.print(" Adding features of Luxury Car.");
    }
}
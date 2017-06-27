package com.machine.design.pattern.structural.decorator;

/**
 * Created by michael on 2017-06-27.
 */
public class SportsCar extends CarDecorator {
    public SportsCar(Car c) {
        super(c);
    }
    @Override
    public void assemble(){
        car.assemble();
        System.out.print(" Adding features of Sports Car.");
    }
}
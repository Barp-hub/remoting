package com.machine.design.pattern.structural.decorator;

/**
 * Created by michael on 2017-06-27.
 */
public class CarDecorator implements Car {
    protected Car car;
    public CarDecorator(Car c){
        this.car=c;
    }
    @Override
    public void assemble() {
        this.car.assemble();
    }
}
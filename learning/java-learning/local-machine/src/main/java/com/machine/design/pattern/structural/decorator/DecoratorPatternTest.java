package com.machine.design.pattern.structural.decorator;

/**
 * Created by michael on 2017-06-27.
 */
public class DecoratorPatternTest {

    public static void main(String[] args) {
        Car sportsCar = new SportsCar(new BasicCar());
        sportsCar.assemble();
        System.out.println("\n*****");
        Car sportsLuxuryCar = new SportsCar(new LuxuryCar(new
                BasicCar()));
        sportsLuxuryCar.assemble();
    }

}

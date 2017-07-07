package com.machine.design.pattern.behavioral.template;

/**
 * Created by michael on 2017-06-27.
 */
public class WoodenHouse extends HouseTemplate {
    @Override
    public void buildWalls() {
        System.out.println("Building Wooden Walls");
    }

    @Override
    public void buildPillars() {
        System.out.println("Building Pillars with Wood coating");
    }
}
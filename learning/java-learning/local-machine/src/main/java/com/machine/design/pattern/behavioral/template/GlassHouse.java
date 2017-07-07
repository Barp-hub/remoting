package com.machine.design.pattern.behavioral.template;

/**
 * Created by michael on 2017-06-27.
 */
public class GlassHouse extends HouseTemplate {
    @Override
    public void buildWalls() {
        System.out.println("Building Glass Walls");
    }
    @Override
    public void buildPillars() {
        System.out.println("Building Pillars with glass coating");
    }
}
package com.machine.design.pattern.structural.bridge;

/**
 * Created by michael on 2017-06-27.
 */
public abstract class Shape {
    //Composition - implementor
    protected Color color;

    //constructor with implementor as input argument
    public Shape(Color c) {
        this.color = c;
    }

    public abstract void applyColor();
}
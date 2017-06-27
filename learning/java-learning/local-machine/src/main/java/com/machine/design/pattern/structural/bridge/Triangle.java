package com.machine.design.pattern.structural.bridge;

/**
 * Created by michael on 2017-06-27.
 */
public class Triangle extends Shape {
    public Triangle(Color c) {
        super(c);
    }

    @Override
    public void applyColor() {
        System.out.print("Triangle filled with color ");
        color.applyColor();
    }
}
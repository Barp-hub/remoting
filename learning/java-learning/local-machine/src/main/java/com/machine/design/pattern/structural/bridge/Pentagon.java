package com.machine.design.pattern.structural.bridge;

/**
 * Created by michael on 2017-06-27.
 */
public class Pentagon extends Shape {
    public Pentagon(Color c) {
        super(c);
    }

    @Override
    public void applyColor() {
        System.out.print("Pentagon filled with color ");
        color.applyColor();
    }
}
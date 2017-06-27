package com.machine.design.pattern.structural.composite;

/**
 * Created by michael on 2017-06-26.
 */
public class Circle implements Shape {
    @Override
    public void draw(String fillColor) {
        System.out.println("Drawing Circle with color " + fillColor);
    }
}
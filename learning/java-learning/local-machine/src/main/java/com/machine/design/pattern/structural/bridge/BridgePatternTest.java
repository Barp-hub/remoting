package com.machine.design.pattern.structural.bridge;

/**
 * Created by michael on 2017-06-27.
 */
public class BridgePatternTest {

    public static void main(String[] args) {
        Shape tri = new Triangle(new RedColor());
        tri.applyColor();
        Shape pent = new Pentagon(new GreenColor());
        pent.applyColor();
    }

}

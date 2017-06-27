package com.machine.design.pattern.structural.flyweight;

import java.awt.*;

/**
 * Created by michael on 2017-06-26.
 */
public interface Shape {

    public void draw(Graphics g, int x, int y, int width, int height,
                     Color color);

}

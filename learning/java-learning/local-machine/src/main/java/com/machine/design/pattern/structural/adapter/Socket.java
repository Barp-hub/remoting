package com.machine.design.pattern.structural.adapter;

/**
 * Created by michael on 2017-06-26.
 */
public class Socket {

    public Volt getVolt() {
        return new Volt(120);
    }

}

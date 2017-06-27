package com.machine.design.pattern.structural.adapter;

/**
 * Created by michael on 2017-06-26.
 */
public interface SocketAdapter {

    Volt get120Volt();

    Volt get12Volt();

    Volt get3Volt();
}

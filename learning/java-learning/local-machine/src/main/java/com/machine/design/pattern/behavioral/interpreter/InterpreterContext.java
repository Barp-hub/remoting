package com.machine.design.pattern.behavioral.interpreter;

/**
 * Created by michael on 2017-07-03.
 */
public class InterpreterContext {

    public String getBinaryFormat(int i) {
        return Integer.toBinaryString(i);
    }

    public String getHexadecimalFormat(int i) {
        return Integer.toHexString(i);
    }

}

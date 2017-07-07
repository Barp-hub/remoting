package com.machine.design.pattern.behavioral.interpreter;

/**
 * Created by michael on 2017-07-03.
 */
public class IntToHexExpression implements Expression {
    private int i;

    public IntToHexExpression(int c) {
        this.i = c;
    }

    @Override
    public String interpret(InterpreterContext ic) {
        return ic.getHexadecimalFormat(i);
    }
}
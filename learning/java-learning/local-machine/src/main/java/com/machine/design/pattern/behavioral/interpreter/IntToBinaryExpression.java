package com.machine.design.pattern.behavioral.interpreter;

/**
 * Created by michael on 2017-07-03.
 */
public class IntToBinaryExpression implements Expression {
    private int i;
    public IntToBinaryExpression(int c){
        this.i=c;
    }
    @Override
    public String interpret(InterpreterContext ic) {
        return ic.getBinaryFormat(this.i);
    }
}

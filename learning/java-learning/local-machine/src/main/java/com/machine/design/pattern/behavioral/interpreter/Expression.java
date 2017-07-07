package com.machine.design.pattern.behavioral.interpreter;

/**
 * Created by michael on 2017-07-03.
 */
public interface Expression {
    String interpret(InterpreterContext ic);
}

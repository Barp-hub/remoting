package com.machine.design.pattern.behavioral.visitor;

/**
 * Created by michael on 2017-07-03.
 */
public interface ItemElement {
    int accept(ShoppingCartVisitor visitor);
}

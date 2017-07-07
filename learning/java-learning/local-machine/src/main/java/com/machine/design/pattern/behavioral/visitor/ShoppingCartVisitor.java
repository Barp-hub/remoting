package com.machine.design.pattern.behavioral.visitor;

/**
 * Created by michael on 2017-07-03.
 */
public interface ShoppingCartVisitor {
    int visit(Book book);

    int visit(Fruit fruit);
}

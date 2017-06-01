package io.github.riwcwt.hello.model;

import java.io.Serializable;

/**
 * Created by michael on 2017-05-27.
 */
public class HelloDTO implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

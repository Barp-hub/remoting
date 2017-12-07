package io.github.riwcwt.reflect;

import java.util.stream.Stream;

public class HelloReflect {

    public static void main(String[] args) {
        System.out.println(HelloReflect.class.getPackage().getName());
        Stream.of(Class.class.getMethods()).forEach(method -> {
            //            System.out.println(method.getName());
            System.out.println(method.toGenericString());
        });
    }

}

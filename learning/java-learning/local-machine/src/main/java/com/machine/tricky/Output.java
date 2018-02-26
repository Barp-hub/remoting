package com.machine.tricky;

public class Output {

    public static void main(String[] args) {
        method(null);
    }

    public static void method(Object var) {
        System.out.println("object");
    }

    public static void method(String var) {
        System.out.println("string");
    }

    //    public static void method(StringBuffer var) {
    //        System.out.println("string buffer");
    //    }
}

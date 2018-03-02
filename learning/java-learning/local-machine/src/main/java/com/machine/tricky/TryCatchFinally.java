package com.machine.tricky;

public class TryCatchFinally {

    public static void main(String[] args) {
        System.out.println(test());
    }

    public static String test() {
        String t = "";
        try {
            t = "try";
            Integer.parseInt(null);
            return t;
        } catch (Exception e) {
            t = "catch";
            Integer.parseInt(null);
            return t;
        } finally {
            t = "finally";
            return t;
        }
    }
}

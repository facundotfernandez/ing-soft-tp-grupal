package com.tpIngSoft1.restApi.rules.operator;

public class Equal implements Comparable {
    public Equal() {}

    public String toString() {
        return "=";
    }

    public boolean compare (String val1, String val2) {
        return val1.equals(val2);
    }
}

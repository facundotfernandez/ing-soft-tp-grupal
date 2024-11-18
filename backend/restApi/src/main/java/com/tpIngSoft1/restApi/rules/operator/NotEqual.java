package com.tpIngSoft1.restApi.rules.operator;

public class NotEqual implements Comparable{
    public NotEqual() {}

    public String toString() {
        return "!=";
    }

    public boolean compare (String val1, String val2) {
        return !val1.equals(val2);
    }
}

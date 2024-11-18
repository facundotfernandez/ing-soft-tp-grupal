package com.tpIngSoft1.restApi.rules.operator;

public class GreaterOrEqualThan implements Comparable{
    public GreaterOrEqualThan() {}

    public String toString() {
        return ">=";
    }

    public boolean compare (String val1, String val2) {
        try {
            int num1 = Integer.parseInt(val1);
            int num2 = Integer.parseInt(val2);
            return num1 >= num2;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

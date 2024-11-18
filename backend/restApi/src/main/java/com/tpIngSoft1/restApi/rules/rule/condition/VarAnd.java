package com.tpIngSoft1.restApi.rules.rule.condition;

import com.tpIngSoft1.restApi.domain.Variant;

import java.util.Map;

public class VarAnd implements VariantRule{
    private VariantRule rule1;
    private VariantRule rule2;

    public VarAnd() {}

    public VarAnd(VariantRule rule1, VariantRule rule2) {
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    public String toString() {
        return "("+rule1.toString()+")" + " && " + "("+rule2.toString()+")";
    }

    public VariantRule getRule1() {
        return rule1;
    }

    public void setRule1(VariantRule rule1) {
        this.rule1 = rule1;
    }

    public VariantRule getRule2() {
        return rule2;
    }

    public void setRule2(VariantRule rule2) {
        this.rule2 = rule2;
    }

    public boolean compare(Variant variant) {
        return rule1.compare(variant) && rule2.compare(variant);
    }
}

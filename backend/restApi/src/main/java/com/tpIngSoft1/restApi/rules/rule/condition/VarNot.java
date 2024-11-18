package com.tpIngSoft1.restApi.rules.rule.condition;

import com.tpIngSoft1.restApi.domain.Variant;

public class VarNot implements VariantRule{

    private String description;
    private VariantRule rule;

    public VarNot() {}

    public VarNot(VariantRule rule) {
        this.rule = rule;
    }

    public String toString() {
        return "!"+"("+rule.toString()+")";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VariantRule getRule() {
        this.description = "! " + "("+ rule.toString()+ ")";
        return rule;
    }

    public void setRule(VariantRule rule) {
        this.rule = rule;
    }


    public boolean compare(Variant variant) {
        return !rule.compare(variant);
    }
}

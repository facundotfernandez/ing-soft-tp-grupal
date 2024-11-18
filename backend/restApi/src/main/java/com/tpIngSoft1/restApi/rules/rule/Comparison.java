package com.tpIngSoft1.restApi.rules.rule;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.rule.condition.VariantRule;
import com.tpIngSoft1.restApi.rules.ruleType.Evaluable;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;

import java.util.ArrayList;
import java.util.List;

public class Comparison implements Rule{
    private List<String> error;
    private Evaluable comparisonType;
    private VariantRule condition;

    public Comparison() {
        this.error = new ArrayList<>();
    }

    public Comparison(Evaluable comparisonType, VariantRule condition) {
        this.error = new ArrayList<>();
        this.comparisonType = comparisonType;
        this.condition = condition;
    }

    public String toString() {
        return comparisonType.toString() + condition.toString();
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public void addError(String error) {
        this.error.add(error);
    }

    public Evaluable getComparisonType() {
        return comparisonType;
    }

    public void setComparisonType(Evaluable comparisonType) {
        this.comparisonType = comparisonType;
    }

    public VariantRule getCondition() {
        return condition;
    }

    public void setCondition(VariantRule condition) {
        this.condition = condition;
    }

    public boolean evaluate(List<Variant> variantes) {
        boolean r = comparisonType.evaluate(variantes, condition);
        if (!r) {
            this.addError(this.toString());
        }
        return r;
    }
}

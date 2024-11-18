package com.tpIngSoft1.restApi.rules.rule;

import com.tpIngSoft1.restApi.domain.Variant;

import java.util.ArrayList;
import java.util.List;

public class Not implements Rule{
    private List<String> error;
    private Rule rule;

    public Not() {
        this.error = new ArrayList<>();
    }

    public Not(Rule rule) {
        this.error = new ArrayList<>();
        this.rule = rule;

    }

    public String toString() {
        return "! " + "["+rule.toString()+"]";
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

    public Rule getRule() {
        return rule;
    }

    public void serRule(Rule rule) {
        this.rule = rule;
    }

    public boolean evaluate(List<Variant> variants) {
        boolean r = rule.evaluate(variants);
        if (r) {
            this.addError(rule.toString());
        }
        return !r;
    }
}

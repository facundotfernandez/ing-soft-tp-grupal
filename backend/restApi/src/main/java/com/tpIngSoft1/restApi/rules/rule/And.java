package com.tpIngSoft1.restApi.rules.rule;

import com.tpIngSoft1.restApi.domain.Variant;

import java.util.ArrayList;
import java.util.List;

public class And implements Rule{

    private List<String> error;
    private Rule rule1;
    private Rule rule2;

    public And() {
        this.error = new ArrayList<>();
    }

    public And(Rule rule1, Rule rule2) {
        this.error = new ArrayList<>();
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    public String toString() {
        return "["+rule1.toString()+"]" + " && " + "["+rule2.toString()+"]";
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

    public Rule getRule1() {
        return rule1;
    }

    public void serRule1(Rule rule1) {
        this.rule1 = rule1;
    }

    public Rule getRule2() {
        return rule2;
    }

    public void serRule2(Rule rule2) {
        this.rule2 = rule2;
    }

    public boolean evaluate(List<Variant> variants) {
        boolean r1 = rule1.evaluate(variants);
        if (!r1) {
            this.addError(rule1.toString());
            for (String e : rule1.getError()) {
                if (!this.error.contains(e)) {
                    this.addError(e);
                }
            }
        }

        boolean r2 = rule2.evaluate(variants);
        if (!r2) {
            this.addError(rule2.toString());
            for (String e : rule2.getError()) {
                if (!this.error.contains(e)) {
                    this.addError(e);
                }
            }
        }

        return  r1 && r2;
    }
}

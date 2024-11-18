package com.tpIngSoft1.restApi.rules.rule.condition;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.Comparable;

import java.util.Map;

public class VarCondition implements VariantRule{

    private String key;
    private String value;
    private Comparable operator;

    public VarCondition() { }

    public VarCondition(String key, Comparable operator, String value) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Comparable getOperator() {
        return operator;
    }

    public void setOperator(Comparable operator) {
        this.operator = operator;
    }

    public String toString() {
        return key+operator.toString()+value;
    }

    public boolean compare(Variant variant) {
        Map<String, String> specs = variant.getSpecs();
        if (!specs.containsKey(key)){
            return false;
        }
        String specValue = specs.get(key);
        return operator.compare(specValue, value);
    }
/*
    public boolean compare(String variantValue) {
        return operator.compare(variantValue, this.value);
    }
*/
}

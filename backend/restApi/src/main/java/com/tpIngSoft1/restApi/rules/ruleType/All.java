package com.tpIngSoft1.restApi.rules.ruleType;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.rule.condition.VariantRule;

import java.util.List;
import java.util.Map;

public class All implements Evaluable{
    public All() {}

    public String toString() {
        return "All have to: ";
    }

    @Override
    public boolean evaluate (List<Variant> variants, VariantRule condition) {
        for (Variant variant : variants) {
            Map<String, String> specs = variant.getSpecs();
            if (!condition.compare(variant)) {
                return false;
            }
        }
        return true;
    }
}

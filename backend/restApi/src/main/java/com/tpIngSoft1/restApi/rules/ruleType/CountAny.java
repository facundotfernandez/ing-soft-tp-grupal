package com.tpIngSoft1.restApi.rules.ruleType;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.rule.condition.VariantRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountAny implements Evaluable{
    public CountAny() {}

    public String toString() {
        return "Count Any has to: ";
    }

    @Override
    public boolean evaluate (List<Variant> variants, VariantRule condition) {
        Map<String, Integer> stockPerSpecType = new HashMap<>();

        String key = null;
        if (condition instanceof VarCondition) {
            key = ((VarCondition) condition).getKey();
        }


        for (Variant variant : variants) {
            Map<String, String> specs = variant.getSpecs();
            if (specs.containsKey(key)) {
                String keyType = specs.get(key);
                stockPerSpecType.put(keyType, stockPerSpecType.getOrDefault(keyType, 0) + variant.getStock());
            }
        }

        for (Map.Entry<String, Integer> entry : stockPerSpecType.entrySet()) {
            Variant  syntheticVariant = new Variant(Map.of(key, entry.getValue().toString()), 1);
            if (!condition.compare(syntheticVariant)) {
                return false;
            }
        }
        return true;
    }
}

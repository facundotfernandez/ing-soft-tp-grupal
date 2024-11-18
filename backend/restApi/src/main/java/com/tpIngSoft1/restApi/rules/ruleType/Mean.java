package com.tpIngSoft1.restApi.rules.ruleType;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.rule.condition.VariantRule;

import java.util.List;
import java.util.Map;

public class Mean implements Evaluable{
    private VariantRule filterCondition;

    public Mean() {
        this.filterCondition = new VariantRule() {
            @Override
            public boolean compare(Variant variants) {
                return true;
            }
        };
    }

    public Mean(VariantRule condition) {
        this.filterCondition = condition;
    }

    public VariantRule getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(VariantRule filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String toString() {
        return "Mean have to: ";
    }

    @Override
    public boolean evaluate (List<Variant> variants, VariantRule condition) {
        if (condition instanceof VarCondition) {
            String key = null;
            key = ((VarCondition) condition).getKey();
            int total = 0;
            int counter = 0;
            for (Variant variant : variants) {
                Map<String, String> specs = variant.getSpecs();

                if (filterCondition.compare(variant)){
                    if (specs.containsKey(key)) {
                        String specValue = specs.get(key);
                        try {
                            int value = Integer.parseInt(specValue);
                            total += value * variant.getStock();;
                            counter += variant.getStock();
                        } catch (NumberFormatException e) {
                            total += 0;
                        }
                    }
                }
            }
            float mean = counter == 0 ? 0 : (float) total/counter;
            Variant syntheticVariant = new Variant(Map.of(key, Float.toString(mean)), 1);
            return condition.compare(syntheticVariant);
        }
        return false;
    }
}

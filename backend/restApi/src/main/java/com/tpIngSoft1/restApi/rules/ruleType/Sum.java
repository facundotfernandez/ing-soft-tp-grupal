package com.tpIngSoft1.restApi.rules.ruleType;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.rule.condition.VariantRule;

import java.util.List;
import java.util.Map;

public class Sum implements Evaluable{
    private VarCondition filterCondition;

    public Sum() {
        this.filterCondition = new VarCondition() {
            @Override
            public boolean compare(Variant variants) {
                return true;
            }
        };
    }

    public Sum(VarCondition filterCondition) {
        this.filterCondition = filterCondition;
    }

    public VarCondition getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(VarCondition filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String toString() {
        return "Sum has to: ";
    }

    @Override
    public boolean evaluate (List<Variant> variants, VariantRule condition) {
        if (condition instanceof VarCondition) {
            String key = null;
            key = ((VarCondition) condition).getKey();
            int total = 0;
            for (Variant variant : variants) {
                Map<String, String> specs = variant.getSpecs();

                if (filterCondition.compare(variant)){
                    if (specs.containsKey(key)) {
                        String specValue = specs.get(key);
                        try {
                            int value = Integer.parseInt(specValue);
                            total += value * variant.getStock();
                        } catch (NumberFormatException e) {
                            total += 0;
                        }
                    }
                }
            }
            String sumString = Integer.toString(total);
            return condition.compare(new Variant(Map.of(key, sumString), 1));
        }
        return false;
    }
}

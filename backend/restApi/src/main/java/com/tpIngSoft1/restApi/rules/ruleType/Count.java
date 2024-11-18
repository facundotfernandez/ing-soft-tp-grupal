package com.tpIngSoft1.restApi.rules.ruleType;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.rule.condition.VariantRule;

import java.util.List;
import java.util.Map;

public class Count implements Evaluable {
    private VariantRule filterCondition;

    public Count() {
        this.filterCondition = new VariantRule() {
            @Override
            public boolean compare(Variant variants) {
                return true;
            }
        };
    }

    public Count(VariantRule condition) {
        this.filterCondition = condition;
    }

    public VariantRule getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(VariantRule filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String toString() {
        return "Count has to: ";
    }

    @Override
    public boolean evaluate (List<Variant> variants, VariantRule condition) {
        int total = 0;
        for (Variant variant : variants) {
            if (filterCondition.compare(variant)) {
                total += variant.getStock();
            }
        }
        String sumString = Integer.toString(total);
        return condition.compare(new Variant(Map.of("Total", sumString), 1));
    }
}

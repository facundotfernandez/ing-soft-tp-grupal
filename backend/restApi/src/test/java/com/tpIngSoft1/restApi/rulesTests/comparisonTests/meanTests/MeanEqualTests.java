package com.tpIngSoft1.restApi.rulesTests.comparisonTests.meanTests;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.Equal;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.ruleType.Mean;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeanEqualTests {
    public static List<Variant> sampleVariant() {
        List<Variant> variants = new ArrayList<>();
        Variant variant1 = new Variant();
        variant1.setStock(1);
        Map<String, String> specs1 = new HashMap<>();
        specs1.put("Peso", "30");
        specs1.put("Talle", "XL");
        variant1.setSpecs(specs1);
        variants.add(variant1);
        Variant variant2 = new Variant();
        variant2.setStock(1);
        Map<String, String> specs2 = new HashMap<>();
        specs2.put("Peso", "20");
        specs2.put("Talle", "XL");
        variant2.setSpecs(specs2);
        variants.add(variant2);
        return variants;
    }

    @Test
    public void meanEqualTrueTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition = new VarCondition("Peso", new Equal(), "25.0");
        Comparison comparison = new Comparison(new Mean(), condition);
        assertTrue(comparison.evaluate(variants));
    }
    @Test
    public void meanEqualFalseTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition = new VarCondition("Peso", new Equal(), "20");
        Comparison comparison = new Comparison(new Mean(), condition);
        assertFalse(comparison.evaluate(variants));
    }
}

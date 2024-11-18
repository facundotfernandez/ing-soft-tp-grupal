package com.tpIngSoft1.restApi.rulesTests.comparisonTests.individualTests;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.Equal;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.ruleType.Individual;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndividualEqualTests {
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
        specs2.put("Talle", "XS");
        variant2.setSpecs(specs2);
        variants.add(variant2);
        return variants;
    }

    @Test
    public void individualEqualTrueTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition = new VarCondition("Talle", new Equal(), "XS");
        Comparison comparison = new Comparison(new Individual(), condition);
        assertTrue(comparison.evaluate(variants));
    }
    @Test
    public void individualEqualFalseTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition = new VarCondition("Talle", new Equal(), "M");
        Comparison comparison = new Comparison(new Individual(), condition);
        assertFalse(comparison.evaluate(variants));
    }

}

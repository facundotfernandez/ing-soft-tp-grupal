package com.tpIngSoft1.restApi.rulesTests.boolTests;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.Equal;
import com.tpIngSoft1.restApi.rules.rule.And;
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

public class AndTests {
    public static List<Variant> sampleVariant() {
        List<Variant> variants = new ArrayList<>();
        Variant variant1 = new Variant();
        variant1.setStock(1);
        Map<String, String> specs1 = new HashMap<>();
        specs1.put("Peso", "30");
        specs1.put("Talle", "XS");
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
    public void allEqualTrueTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition1 = new VarCondition("Peso", new Equal(), "30");
        Comparison comparison1 = new Comparison(new Individual(), condition1);
        VarCondition condition2 = new VarCondition("Talle", new Equal(), "XL");
        Comparison comparison2 = new Comparison(new Individual(), condition2);
        And and = new And(comparison1, comparison2);
        System.out.println(and);
        assertTrue(and.evaluate(variants));
    }
    @Test
    public void allEqualFalseTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition1 = new VarCondition("Peso", new Equal(), "10");
        Comparison comparison1 = new Comparison(new Individual(), condition1);
        VarCondition condition2 = new VarCondition("Talle", new Equal(), "M");
        Comparison comparison2 = new Comparison(new Individual(), condition2);
        And and = new And(comparison1, comparison2);
        boolean r = and.evaluate(variants);
        System.out.println(and.getError());
        assertFalse(and.evaluate(variants));
    }
}

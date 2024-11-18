package com.tpIngSoft1.restApi.rulesTests.boolTests;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.Equal;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.condition.VarAnd;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.ruleType.Individual;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VarConditionTests {
    public static List<Variant> sampleVariant2() {
        List<Variant> variants = new ArrayList<>();

        Variant variant1 = new Variant();
        variant1.setStock(1);
        Map<String, String> specs1 = new HashMap<>();
        specs1.put("Peso", "3");
        specs1.put("Tipo", "Liquido");
        variant1.setSpecs(specs1);
        variants.add(variant1);

        Variant variant2 = new Variant();
        variant2.setStock(4);
        Map<String, String> specs2 = new HashMap<>();
        specs2.put("Peso", "2");
        specs2.put("Tipo", "Gaseoso");
        variant2.setSpecs(specs2);
        variants.add(variant2);
        return variants;
    }

    @Test
    public void VarConditionAndTrueTest() {
        List<Variant> variants = sampleVariant2();
        VarCondition condition1 = new VarCondition("Peso", new Equal(), "3");
        VarCondition condition2 = new VarCondition("Tipo", new Equal(), "Liquido");
        VarAnd varAnd = new VarAnd(condition1, condition2);
        Comparison comparison = new Comparison(new Individual(), varAnd);
        assertTrue(comparison.evaluate(variants));
    }

    @Test
    public void VarConditionAndFalseTest() {
        List<Variant> variants = sampleVariant2();
        VarCondition condition1 = new VarCondition("Tipo", new Equal(), "Gaseoso");
        VarCondition condition2 = new VarCondition("Tipo", new Equal(), "Liquido");
        VarAnd varAnd = new VarAnd(condition1, condition2);
        Comparison comparison = new Comparison(new Individual(), varAnd);
        System.out.println(comparison);
        assertFalse(comparison.evaluate(variants));
    }
}

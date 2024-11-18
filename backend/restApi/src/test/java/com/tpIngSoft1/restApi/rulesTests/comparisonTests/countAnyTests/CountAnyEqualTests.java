package com.tpIngSoft1.restApi.rulesTests.comparisonTests.countAnyTests;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.GreaterThan;
import com.tpIngSoft1.restApi.rules.operator.LessThan;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.ruleType.CountAny;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CountAnyEqualTests {
    public static List<Variant> sampleVariant() {
        List<Variant> variants = new ArrayList<>();

        Variant variant1 = new Variant();
        variant1.setStock(2);
        Map<String, String> specs1 = new HashMap<>();
        specs1.put("Peso", "30");
        specs1.put("Talle", "XL");
        specs1.put("Tipo", "Remera");
        variant1.setSpecs(specs1);
        variants.add(variant1);

        Variant variant2 = new Variant();
        variant2.setStock(2);
        Map<String, String> specs2 = new HashMap<>();
        specs2.put("Peso", "20");
        specs2.put("Talle", "XL");
        specs2.put("Tipo", "Pelota");
        variant2.setSpecs(specs2);
        variants.add(variant2);

        Variant variant3 = new Variant();
        variant3.setStock(3);
        Map<String, String> specs3 = new HashMap<>();
        specs3.put("Peso", "20");
        specs3.put("Talle", "XL");
        specs3.put("Tipo", "Pelota");
        variant3.setSpecs(specs3);
        variants.add(variant3);
        return variants;
    }

    @Test
    public void countAnyEqualTrueTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition = new VarCondition("Tipo", new LessThan(), "6");
        Comparison comparison = new Comparison(new CountAny(), condition);
        boolean r = comparison.evaluate(variants);
        assertTrue(r);
    }
    @Test
    public void countAnyEqualFalseTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition = new VarCondition("Tipo", new GreaterThan(), "4");
        Comparison comparison = new Comparison(new CountAny(), condition);
        assertFalse(comparison.evaluate(variants));
    }
}

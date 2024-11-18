package com.tpIngSoft1.restApi.rulesTests.posibleTests;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.Equal;
import com.tpIngSoft1.restApi.rules.operator.GreaterThan;
import com.tpIngSoft1.restApi.rules.operator.LessThan;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.ruleType.Count;
import com.tpIngSoft1.restApi.rules.ruleType.Individual;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityPerCategory {
    public static List<Variant> sampleVariant() {
        List<Variant> variants = new ArrayList<>();

        Variant variant1 = new Variant();
        variant1.setStock(1);
        Map<String, String> specs1 = new HashMap<>();
        specs1.put("Tipo", "Solido");
        specs1.put("Categoria", "Electronica");
        variant1.setSpecs(specs1);
        variants.add(variant1);

        Variant variant2 = new Variant();
        variant2.setStock(2);
        Map<String, String> specs2 = new HashMap<>();
        specs2.put("Tipo", "Liquido");
        specs2.put("Categoria", "Electronica");
        variant2.setSpecs(specs2);
        variants.add(variant2);

        Variant variant3 = new Variant();
        variant3.setStock(3);
        Map<String, String> specs3 = new HashMap<>();
        specs3.put("Tipo", "Solido");
        specs3.put("Categoria", "Comida");
        variant3.setSpecs(specs3);
        variants.add(variant3);

        Variant variant4 = new Variant();
        variant4.setStock(4);
        Map<String, String> specs4 = new HashMap<>();
        specs4.put("Tipo", "Liquido");
        specs4.put("Categoria", "Comida");
        variant4.setSpecs(specs4);
        variants.add(variant4);
        return variants;
    }

    @Test
    public void LessThan5ElectronicaTest() {
        List<Variant> variants = sampleVariant();
        VarCondition filterCondition = new VarCondition("Categoria", new Equal(), "Electronica");
        VarCondition condition = new VarCondition("Total", new LessThan(), "5");
        Comparison comparison = new Comparison(new Count(filterCondition), condition);
        assertTrue(comparison.evaluate(variants));
    }

    @Test
    public void LessThan5ComidaTest() {
        List<Variant> variants = sampleVariant();
        VarCondition filterCondition = new VarCondition("Categoria", new Equal(), "Comida");
        VarCondition condition = new VarCondition("Total", new LessThan(), "5");
        Comparison comparison = new Comparison(new Count(filterCondition), condition);
        assertFalse(comparison.evaluate(variants));
    }
    @Test
    public void Equal6LiquidoTest() {
        List<Variant> variants = sampleVariant();
        VarCondition filterCondition = new VarCondition("Tipo", new Equal(), "Liquido");
        VarCondition condition = new VarCondition("Total", new Equal(), "6");
        Comparison comparison = new Comparison(new Count(filterCondition), condition);
        assertTrue(comparison.evaluate(variants));
    }
    @Test
    public void GreaterThan3SolidoTest() {
        List<Variant> variants = sampleVariant();
        VarCondition filterCondition = new VarCondition("Tipo", new Equal(), "Solido");
        VarCondition condition = new VarCondition("Total", new GreaterThan(), "3");
        Comparison comparison = new Comparison(new Count(filterCondition), condition);
        assertTrue(comparison.evaluate(variants));
    }
}

package com.tpIngSoft1.restApi.rulesTests.boolTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.GreaterThan;
import com.tpIngSoft1.restApi.rules.operator.Equal;
import com.tpIngSoft1.restApi.rules.rule.And;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.Rule;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.rule.Not;
import com.tpIngSoft1.restApi.rules.ruleType.CountAny;
import com.tpIngSoft1.restApi.rules.ruleType.Individual;
import com.tpIngSoft1.restApi.rules.ruleType.Sum;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskTests {
    public static List<Variant> sampleVariant() {
        List<Variant> variants = new ArrayList<>();

        Variant variant1 = new Variant();
        variant1.setStock(1);
        Map<String, String> specs1 = new HashMap<>();
        specs1.put("Peso", "3");
        specs1.put("Talle", "XL");
        variant1.setSpecs(specs1);
        variants.add(variant1);

        Variant variant2 = new Variant();
        variant2.setStock(3);
        Map<String, String> specs2 = new HashMap<>();
        specs2.put("Peso", "2");
        specs2.put("Talle", "XS");
        variant2.setSpecs(specs2);
        variants.add(variant2);

        Variant variant3 = new Variant();
        variant3.setStock(2);
        Map<String, String> specs3 = new HashMap<>();
        specs3.put("Peso", "1");
        specs3.put("Talle", "XS");
        variant3.setSpecs(specs3);
        variants.add(variant3);
        return variants;
    }
    @Test
    public void weightNotMoreThanTest() {
        List<Variant> variants = sampleVariant();
        VarCondition filterCondition = new VarCondition("Peso", new GreaterThan(), "0");
        VarCondition condition = new VarCondition("Peso", new GreaterThan(), "10");
        Comparison comparison = new Comparison(new Sum(filterCondition), condition);
        Not not = new Not(comparison);
        assertFalse(not.evaluate(variants));
    }

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
    public void NotGasAndLiquidTest() {
        List<Variant> variants = sampleVariant2();
        VarCondition condition1 = new VarCondition("Tipo", new Equal(), "Gaseoso");
        Comparison comparison1 = new Comparison(new Individual(), condition1);
        VarCondition condition2 = new VarCondition("Tipo", new Equal(), "Liquido");
        Comparison comparison2 = new Comparison(new Individual(), condition2);
        And and = new And(comparison1, comparison2);
        Not not = new Not(and);
        boolean r = not.evaluate(variants);
        assertFalse(r);
    }

    @Test
    public void NotGasAndLiquidjsonTest() throws IOException{
        List<Variant> variants = sampleVariant();
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/test/java/com/tpIngSoft1/restApi/rulesTests/boolTests/Rule.json");
        Rule rule = mapper.readValue(jsonFile, Rule.class);
        assertTrue(rule.evaluate(variants));
    }

    @Test
    public void NotMoreThan3Test() {
        List<Variant> variants = sampleVariant2();
        VarCondition condition = new VarCondition("Talle", new GreaterThan(), "3");
        Comparison comparison = new Comparison(new CountAny(), condition);
        Not not = new Not(comparison);
        assertFalse(not.evaluate(variants));
    }
}

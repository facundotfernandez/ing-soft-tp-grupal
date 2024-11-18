package com.tpIngSoft1.restApi.rulesTests.posibleTests;

import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.Equal;
import com.tpIngSoft1.restApi.rules.operator.GreaterThan;
import com.tpIngSoft1.restApi.rules.operator.LessOrEqualThan;
import com.tpIngSoft1.restApi.rules.operator.LessThan;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.ruleType.Count;
import com.tpIngSoft1.restApi.rules.ruleType.Sum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightPerCategory {
    public static List<Variant> sampleVariant() {
        List<Variant> variants = new ArrayList<>();

        Variant variant1 = new Variant();
        variant1.setStock(1);
        Map<String, String> specs1 = new HashMap<>();
        specs1.put("Peso", "4");
        specs1.put("Categoria", "Electronica");
        variant1.setSpecs(specs1);
        variants.add(variant1);

        Variant variant2 = new Variant();
        variant2.setStock(2);
        Map<String, String> specs2 = new HashMap<>();
        specs2.put("Peso", "3");
        specs2.put("Categoria", "Electronica");
        variant2.setSpecs(specs2);
        variants.add(variant2);

        Variant variant3 = new Variant();
        variant3.setStock(3);
        Map<String, String> specs3 = new HashMap<>();
        specs3.put("Peso", "1");
        specs3.put("Categoria", "Ropa");
        variant3.setSpecs(specs3);
        variants.add(variant3);

        Variant variant4 = new Variant();
        variant4.setStock(4);
        Map<String, String> specs4 = new HashMap<>();
        specs4.put("Peso", "2");
        specs4.put("Categoria", "Ropa");
        variant4.setSpecs(specs4);
        variants.add(variant4);
        return variants;
    }
    @Test
    public void RopaWeightLessOrEqualThan15() {
        List<Variant> variants = sampleVariant();
        VarCondition filterCondition = new VarCondition("Categoria", new Equal(), "Ropa");
        VarCondition condition = new VarCondition("Peso", new LessOrEqualThan(), "15");
        Comparison comparison = new Comparison(new Sum(filterCondition), condition);
        assertTrue(comparison.evaluate(variants));
    }
    @Test
    public void ElectronicaWeightGreaterThan5() {
        List<Variant> variants = sampleVariant();
        VarCondition filterCondition = new VarCondition("Categoria", new Equal(), "Electronica");
        VarCondition condition = new VarCondition("Peso", new GreaterThan(), "5");
        Comparison comparison = new Comparison(new Sum(filterCondition), condition);
        assertTrue(comparison.evaluate(variants));
    }

}

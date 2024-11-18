package com.tpIngSoft1.restApi.rulesTests.boolTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.operator.Equal;
import com.tpIngSoft1.restApi.rules.operator.LessThan;
import com.tpIngSoft1.restApi.rules.rule.And;
import com.tpIngSoft1.restApi.rules.rule.Comparison;
import com.tpIngSoft1.restApi.rules.rule.Or;
import com.tpIngSoft1.restApi.rules.rule.Rule;
import com.tpIngSoft1.restApi.rules.rule.condition.VarCondition;
import com.tpIngSoft1.restApi.rules.ruleType.Count;
import com.tpIngSoft1.restApi.rules.ruleType.CountAny;
import com.tpIngSoft1.restApi.rules.ruleType.Individual;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ComplecatedTests {
    public static List<Variant> sampleVariant() {
        List<Variant> variants = new ArrayList<>();

        Variant variant1 = new Variant();
        variant1.setStock(2);
        Map<String, String> specs1 = new HashMap<>();
        specs1.put("Peso", "20");
        specs1.put("Talle", "XS");
        specs1.put("Tipo", "Remera");
        variant1.setSpecs(specs1);
        variants.add(variant1);

        Variant variant2 = new Variant();
        variant2.setStock(2);
        Map<String, String> specs2 = new HashMap<>();
        specs2.put("Peso", "30");
        specs2.put("Talle", "XS");
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
    public void ComplecatedAndTest() {
        List<Variant> variants = sampleVariant();
        VarCondition condition1 = new VarCondition("Peso", new Equal(), "30");
        Comparison comparison1 = new Comparison(new Individual(), condition1);
        VarCondition condition2 = new VarCondition("Talle", new Equal(), "XL");
        Comparison comparison2 = new Comparison(new Individual(), condition2);
        VarCondition condition3 = new VarCondition("Tipo", new Equal(), "Pelota");
        Comparison comparison3 = new Comparison(new Individual(), condition3);
        VarCondition condition4 = new VarCondition("Tipo", new Equal(), "Camisa");
        Comparison comparison4 = new Comparison(new Individual(), condition4);
        And and1 = new And(comparison1, comparison2);
        And and2 = new And(comparison3, comparison4);
        And and = new And(and1, and2);

        System.out.println("La Relga es: " + and);
        boolean r = and.evaluate(variants);
        System.out.println("Error: " + and.getError());
        assertFalse(r);
    }
    @Test
    public void TrueTest() {
        List<Variant> variants = sampleVariant();
        // que halla una variante con peso = 30
        VarCondition condition1 = new VarCondition("Peso", new Equal(), "30");
        Comparison comparison1 = new Comparison(new Individual(), condition1);
        // que la cantidad de variante con talle = XS no sean mas que 5
        VarCondition filterCondition = new VarCondition("Talle", new Equal() , "XS");
        VarCondition condition = new VarCondition("Total", new LessThan(), "4");
        Comparison comparison2 = new Comparison(new Count(filterCondition), condition);
        // que no halla un tipo de producto que se repita mas de 6 veces
        VarCondition condition2 = new VarCondition("Tipo", new LessThan(), "4");
        Comparison comparison3 = new Comparison(new CountAny(), condition2);

        // que halla una variante con peso = 30
        // y
        //   que la cantidad de variante con talle = XS no sean mas que 5
        //   o
        //   que no halla un tipo de producto que se repita mas de 3 veces
        Or or = new Or(comparison3, comparison2);
        And and = new And(or, comparison1);
        System.out.println("La Relga es: " + and);
        boolean r = and.evaluate(variants);
        System.out.println("Error: " + and.getError());
        assertFalse(r);
    }

    @Test
    public void JsonTest() throws IOException {
        List<Variant> variants = sampleVariant();
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/test/java/com/tpIngSoft1/restApi/rulesTests/boolTests/Rules.json");
        Rule rule = mapper.readValue(jsonFile, Rule.class);
        assertTrue(rule.evaluate(variants));
    }
}

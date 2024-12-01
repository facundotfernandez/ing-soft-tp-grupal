package com.tpIngSoft1.restApi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.rules.rule.Rule;

public class RuleService {
 
    private ObjectMapper mapper;
    private InputStream inputStream;
    private Rule rule;

    public RuleService() {
        this.mapper =  new ObjectMapper();
        this.inputStream = getClass().getClassLoader().getResourceAsStream("Rules/Rule.json");
        try{
            this.rule = mapper.readValue(inputStream, Rule.class);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public boolean checkOrder(List<Variant> variant){
        return rule.evaluate(variant);
    }
}

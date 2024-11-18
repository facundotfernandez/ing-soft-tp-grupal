package com.tpIngSoft1.restApi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
@Data
@AllArgsConstructor
public class OrderItem {
    private String vid;
    private int quantity;
    private String name;
    private String pid;
    private Map<String, String> specs;
}

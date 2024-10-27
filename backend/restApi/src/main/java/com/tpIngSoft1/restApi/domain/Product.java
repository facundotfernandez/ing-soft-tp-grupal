package com.tpIngSoft1.restApi.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String name;
    private String description;
    private Float price;
    private String id;
    
}

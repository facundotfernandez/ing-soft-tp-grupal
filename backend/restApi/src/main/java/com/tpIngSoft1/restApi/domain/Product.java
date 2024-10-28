package com.tpIngSoft1.restApi.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

        private String id;

        private String name;

        private String description;

        private Double price;

        private String brand;

        public Product(String name, String description, String brand, Double price) {
                this.name = name;
                this.description = description;
                this.brand = brand;
                this.price = price;
        }
}


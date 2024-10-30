package com.tpIngSoft1.restApi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
        @Id
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

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }

        public void setDescription(String description) { this.description = description; }

        public Double getPrice() { return price; }

        public void setPrice(Double price) { this.price = price; }

        public String getBrand() { return brand; }

        public void setBrand(String brand) { this.brand = brand; }
}


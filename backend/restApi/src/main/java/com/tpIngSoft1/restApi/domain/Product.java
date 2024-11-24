package com.tpIngSoft1.restApi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "products")
@Data
@AllArgsConstructor
public class Product {
        @Id
        private String id;

        private String name;

        private List<Variant> variants = new ArrayList<>();

        public Product() {
                this.variants = new ArrayList<>();
        }

        public Product(String name, String description, String brand, Double price) {
                this.name = name;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getName() { return name; }


        public void setName(String name) { this.name = name; }

        public List<Variant> getVariants() { return variants; }

        public void setVariants(List<Variant> variants) { this.variants = variants; }
}

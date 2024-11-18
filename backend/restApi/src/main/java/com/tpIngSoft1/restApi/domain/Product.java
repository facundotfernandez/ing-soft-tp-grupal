package com.tpIngSoft1.restApi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

@Document(collection = "products")
@Data
@AllArgsConstructor
public class Product {
        @Id
        private String id;

        private String name;

        private List<Variant> variants;

        public Product() {};

        public Product(String id, String name) {
                this.id = id;
                this.name = name;
        }

        public String getId() { return id; };

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public List<Variant> getVariants() { return variants; }

        public void setVariants(List<Variant> variants) { this.variants = variants; }
}


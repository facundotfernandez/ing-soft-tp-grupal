package com.tpIngSoft1.restApi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.tpIngSoft1.restApi.domain.Variant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
        @Id
        private String id;

        private String name;

        private List<Variant> variants;

        public Product() {};

        public Product(String name, String description, String brand, Double price) {
                this.name = name;
        }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public List<Variant> getVariants() { return variants; }

        public void setVariants(List<Variant> variants) { this.variants = variants; }
}


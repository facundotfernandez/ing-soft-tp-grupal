package com.tpIngSoft1.restApi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
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

        public int getVariantStock(String vid) {return variants.stream()
                .filter(variant -> variant.getVid().equals(vid))
                .findFirst()
                .map(Variant::getStock)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el Variant con id: " + id));}

        public void setVariants(List<Variant> variants) { this.variants = variants; }

        public void updateVariant(Variant variantToUpdate) {
                for (Variant variant : this.variants) {
                        if (variant.getVid() == variantToUpdate.getVid()) {
                                variant.setStock(variantToUpdate.getStock());
                                break;
                        }
                }
        }
}

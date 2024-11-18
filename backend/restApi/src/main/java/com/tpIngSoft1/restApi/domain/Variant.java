package com.tpIngSoft1.restApi.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Variant {

        private String vid;
        private int stock;
        private Map<String, String> specs;

        public Variant() {
                this.vid = UUID.randomUUID().toString();
                this.stock = 0;
                this.specs = new HashMap<>();
        }

        public Variant(Map<String, String> specs, int stock) {
                this.vid = UUID.randomUUID().toString();
                this.specs = specs;
                this.stock = stock;
        }

        public Variant(Map<String, String> specs, int stock, String vid) {
                this.vid = vid;
                this.specs = specs;
                this.stock = stock;
        }

        public String getVid() {
                return vid;
        }

        public int getStock() {
                return stock;
        }

        public void setStock(int stock) {
                this.stock = stock;
        }

        public void addSspecs(String key, String value) {
                specs.put(key, value);
        }

        public Map<String, String> getSpecs() {
                return specs;
        }

        public void setSpecs(Map<String, String> specs) {
                this.specs = specs;
        }

        public String toString() {
                return specs.toString();
        }
}



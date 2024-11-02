package com.tpIngSoft1.restApi.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Variant {

        private String vid;
        private Map<String, String> characteristics;

        public Variant() {
                this.vid = UUID.randomUUID().toString();
                this.characteristics = new HashMap<>();
        }

        public String getVid() {
                return vid;
        }

        public void addCharacteristic(String key, String value) {
                characteristics.put(key, value);
        }

        public Map<String, String> getCharacteristics() {
                return characteristics;
        }

        public void setCharacteristics(Map<String, String> characteristics) {
                this.characteristics = characteristics;
        }

        public String toString() {
                return characteristics.toString();
        }
}

package com.tpIngSoft1.restApi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;

@Data
@Document(collection = "carts")
public class Cart {

    @Id
    private String id;

    private String userId;

    private List<CartItem> items;

    // Constructor vacío
    public Cart() {}

    // Constructor con parámetros
    public Cart(String userId, List<CartItem> items) {
        this.userId = userId;
        this.items = items;
    }

    // Clase interna que representa un item en el carrito
    // La cantidad de productos debería ser consultada a la base de datos.
    @Data
    public static class CartItem {
        private String productId;
        private int quantity;

        public CartItem(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
    }
}

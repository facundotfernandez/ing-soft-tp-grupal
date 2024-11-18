package com.tpIngSoft1.restApi.controller;

import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.Cart;
import com.tpIngSoft1.restApi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Product>> getCart(@PathVariable String userId) {
        List<Product> productos = cartService.getCart(userId);
        return ResponseEntity.ok(productos);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addProductsToCart(@PathVariable String userId, @RequestBody Product producto) {
        cartService.addProduct(userId, producto);
        return ResponseEntity.ok("Producto agregado al carrito");
    }
}
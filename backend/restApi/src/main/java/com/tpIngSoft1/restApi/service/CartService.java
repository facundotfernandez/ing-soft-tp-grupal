package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.Cart;
import com.tpIngSoft1.restApi.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;

    public List<Product> getCart(String userId) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        List<Product> products = new ArrayList<>();

        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            for (Cart.CartItem item : cart.getItems()) {
                // productService.findById para obtener el producto
                Optional<Product> productOpt = productService.findById(item.getProductId());
                if (productOpt.isPresent()) {
                    products.add(productOpt.get());
                }
            }
        }

        return products;
    }

    public void addProduct(String userId, Product producto) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(userId, new ArrayList<>()));

        cart.getItems().add(new Cart.CartItem(producto.getId(), 1));//TODO: Aca puede variar la cantidad
        cartRepository.save(cart);
    }
}
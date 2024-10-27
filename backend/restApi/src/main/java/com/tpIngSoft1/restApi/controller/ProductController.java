package com.tpIngSoft1.restApi.controller;

import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.repository.ProductRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor
class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

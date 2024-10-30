package com.tpIngSoft1.restApi.controller;


import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.repository.ProductRepository;
import com.tpIngSoft1.restApi.service.ProductService;
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
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
class ProductController {
    @Autowired
    private ProductRepository productRepository;
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        if (productService.findByNameAndBrand(product.getName(), product.getBrand()).isPresent()) {
            return new ResponseEntity<>("El producto ya existe", HttpStatus.CONFLICT);
        }
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>("Producto agregado exitosamente", HttpStatus.CREATED);
    }
}

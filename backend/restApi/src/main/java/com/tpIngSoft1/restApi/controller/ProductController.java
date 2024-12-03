package com.tpIngSoft1.restApi.controller;


import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.service.ProductService;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable("id") String id){
        return productService.getProductById(id);
    }

    @GetMapping("/{id}/{vid}")
    public ResponseEntity<Variant> getVariantByProductIdAndVid(
            @PathVariable("id") String pid,
            @PathVariable("vid") String vid) {
        return productService.findByPIDandVID(pid,vid);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addVariantToProduct(
            @PathVariable("id") String pid,
            @RequestBody Map<String, String> specs) {

        return productService.addVariantToProduct(pid, specs);
        
    }

    @PatchMapping("/{id}/{vid}")
    public ResponseEntity<String> updateVariantStock(
            @PathVariable("id") String pid,
            @PathVariable("vid") String vid,
            @RequestBody Integer stock) {
        return productService.updateVariantStock(pid,vid,stock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id){
        return productService.deleteProduct(id);
    }

    @DeleteMapping("/{id}/{vid}")
    public ResponseEntity<String> deleteVariant(@PathVariable("id") String id, @PathVariable("vid") String vid){
        return productService.deleteVariant(id,vid);
    }
}

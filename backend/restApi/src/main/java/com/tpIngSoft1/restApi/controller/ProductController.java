package com.tpIngSoft1.restApi.controller;


import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.repository.ProductRepository;
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
        if (productService.findByName(product.getName()).isPresent()) {
            return new ResponseEntity<>("El producto ya existe", HttpStatus.CONFLICT);
        }
        productService.saveProduct(product);
        return new ResponseEntity<>("Producto agregado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT); // Devuelve una lista vacía
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id){
        return productService.findById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/{vid}")
    public ResponseEntity<Variant> getVariantByProductIdAndVid(
            @PathVariable("id") String pid,
            @PathVariable("vid") String vid) {
        Optional<Product> product = productService.findById(pid);
        if (product.isPresent()) {
            Optional<Variant> variant = product.get()
                    .getVariants()
                    .stream()
                    .filter(v -> v.getVid().equals(vid))
                    .findFirst();
            if (variant.isPresent()) {
                return new ResponseEntity<>(variant.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> addVariantToProduct(
            @PathVariable("id") String pid,
            @RequestBody Map<String, String> specs) {
        Optional<Product> product = productService.findById(pid);
        if (product.isPresent()) {
            Product produ = product.get();
            Variant variant = new Variant();

            int stock = 0;
            if (specs.containsKey("stock")) {
                try {
                    stock = Integer.parseInt(specs.get("stock"));
                } catch (NumberFormatException e) {
                    return new ResponseEntity<>("El stock tiene que ser un numero", HttpStatus.BAD_REQUEST);
                }
                specs.remove("stock");
            }

            variant.setStock(stock);
            variant.setSpecs(specs);
            produ.getVariants().add(variant);
            productService.saveProduct(produ);
            return new ResponseEntity<>("Variante agregada exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/{vid}")
    public ResponseEntity<String> updateVariantStock(
            @PathVariable("id") String pid,
            @PathVariable("vid") String vid,
            @RequestBody Integer stock) {
        Optional<Product> product = productService.findById(pid);
        if (product.isPresent()) {
            Product produ = product.get();
            Variant variantToUpdate = produ.getVariants()
                    .stream()
                    .filter(v -> v.getVid().equals(vid))
                    .findFirst()
                    .orElse(null);
            if (variantToUpdate != null) {
                variantToUpdate.setStock(stock);
                productService.saveProduct(produ);
                return new ResponseEntity<>("Variante modificada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Variante no encontrada", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id){
        if(!productService.findById(id).isPresent()) {
            return new ResponseEntity<>("El producto no existe", HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);
        return new ResponseEntity<>("Producto eliminado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{vid}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id, @PathVariable("vid") String vid){
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            Product produ = product.get();
            boolean removed = produ.getVariants().removeIf(v -> v.getVid().equals(vid));
            if (removed) {
                productService.saveProduct(produ);
                return new ResponseEntity<>("Variante eliminada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Variante no encontrada", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}

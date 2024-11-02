package com.tpIngSoft1.restApi.controller;


import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.repository.ProductRepository;
import com.tpIngSoft1.restApi.service.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
class ProductController {
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

    //FALTA AGREGAR COMENTARIOS EJ:SI ESTA VACIO
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //FALTA AGREGAR COMENTARIOS EJ:SI NO SE ENCUENTRA
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id){
        return productService.findById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/variants/{vid}")
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

    @PatchMapping("/{id}/addVariant")
    public ResponseEntity<String> addVariantToProduct(
            @PathVariable("id") String pid,
            @RequestBody Map<String, String> characteristict) {
        Optional<Product> product = productService.findById(pid);
        if (product.isPresent()) {
            Product produ = product.get();
            Variant variant = new Variant();
            variant.setCharacteristics(characteristict);
            produ.getVariants().add(variant);
            productService.saveProduct(produ);
            return new ResponseEntity<>("Variante agregada exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/updateVariant/{vid}")
    public ResponseEntity<String> updateVariantChar(
            @PathVariable("id") String pid,
            @PathVariable("vid") String vid,
            @RequestBody Map<String, String> updatedChar) {
        Optional<Product> product = productService.findById(pid);
        if (product.isPresent()) {
            Product produ = product.get();
            Variant variantToUpdate = produ.getVariants()
                    .stream()
                    .filter(v -> v.getVid().equals(vid))
                    .findFirst()
                    .orElse(null);
            if (variantToUpdate != null) {
                updatedChar.forEach(variantToUpdate.getCharacteristics()::put);
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
}

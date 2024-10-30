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
import java.util.List;
@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        if (productService.findByNameAndBrand(product.getName(), product.getBrand()).isPresent()) {
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
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        return productService.findById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        if(!productService.findById(id).isPresent()) {
            return new ResponseEntity<>("El producto no existe", HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);
        return new ResponseEntity<>("Producto eliminado exitosamente", HttpStatus.OK);
    }

}

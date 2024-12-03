package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.repository.ProductRepository;
import com.tpIngSoft1.restApi.utils.ApiResponse;
import com.tpIngSoft1.restApi.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tpIngSoft1.restApi.domain.Variant;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = repository.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT); // Devuelve una lista vacía
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<Variant> findByPIDandVID(String pid, String vid) {
        Optional<Product> product = repository.findById(pid);
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

    public ResponseEntity<String> addVariantToProduct(String pid, Map<String, String> specs) {
        Optional<Product> product = repository.findById(pid);
        if (product.isPresent()) {
            Product produ = product.get();

            int stock = 0;
            if (specs.containsKey("stock")) {
                try {
                    stock = Integer.parseInt(specs.get("stock"));
                } catch (NumberFormatException e) {
                    return new ResponseEntity<>("El stock tiene que ser un numero", HttpStatus.BAD_REQUEST);
                }
                specs.remove("stock");
            }

            Variant variant = new Variant(specs,stock);
            produ.getVariants().add(variant);
            repository.save(produ);
            return new ResponseEntity<>("Variante agregada exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> saveProduct(Product product){
        
        if (repository.findByName(product.getName()).isPresent()) {
            return new ResponseEntity<>("El producto ya existe", HttpStatus.CONFLICT);
        }
        repository.save(product);
        return new ResponseEntity<>("Producto agregado exitosamente", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteProduct(String id) {
        if(!repository.findById(id).isPresent()) {
            return new ResponseEntity<>("El producto no existe", HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>("Producto eliminado exitosamente", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteVariant(String pid, String vid) {
        Optional<Product> product = repository.findById(pid);
        if (product.isPresent()) {
            Product produ = product.get();
            boolean removed = produ.getVariants().removeIf(v -> v.getVid().equals(vid));
            if (removed) {
                repository.save(produ);
                return new ResponseEntity<>("Variante eliminada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Variante no encontrada", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Optional<Product>> getProductById(String pid) {
        return repository.findById(pid)
            .map(product -> new ResponseEntity<>(Optional.of(product), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }

    public ResponseEntity<String> updateVariantStock(String pid, String vid, Integer stock){
        Optional<Product> product = repository.findById(pid);
        if (product.isPresent()) {
            Product produ = product.get();
            Variant variantToUpdate = produ.getVariants()
                    .stream()
                    .filter(v -> v.getVid().equals(vid))
                    .findFirst()
                    .orElse(null);
            if (variantToUpdate != null) {
                variantToUpdate.setStock(stock);
                repository.save(produ);
                return new ResponseEntity<>("Variante modificada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Variante no encontrada", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> checkAuth(@RequestHeader("Authorization") String authHeader, JwtService jwtService) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Token no proporcionado", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return new ResponseEntity<>("Token invalido", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Token valido", HttpStatus.UNAUTHORIZED);
    }
}
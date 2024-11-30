package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.repository.ProductRepository;
import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.Variant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }

    public Optional<Product> findById(String id) {
        return repository.findById(id);
    }

    public void saveProduct(Product product){
        repository.save(product);
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }
}
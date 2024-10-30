package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.repository.ProductRepository;
import com.tpIngSoft1.restApi.domain.Product;
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

    public Optional<Product> findByName(String name) { return repository.findByName(name); }

    public Optional<Product> findByBrand(String brand) { return repository.findByBrand(brand); }

    public Optional<Product> findByNameAndBrand(String name, String brand) {
        Optional<Product> product = repository.findByName(name);
        return product.filter(u -> u.getBrand().equals(brand));
    }
}
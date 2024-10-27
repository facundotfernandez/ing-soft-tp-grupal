package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.repository.ProductRepository;
import com.tpIngSoft1.restApi.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}

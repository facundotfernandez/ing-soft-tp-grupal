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

    public boolean saveStockProduct(String pid, String vid, int quantity){
        Optional<Product>  product = this.findById(pid);

        if (product.isPresent()) {
            Product productToUpdate = product.get();

            Variant variantToUpdate = productToUpdate.getVariants()
                .stream()
                .filter(v -> v.getVid().equals(vid))
                .findFirst()
                .orElse(null);

            if (variantToUpdate != null) {
                variantToUpdate.setStock(variantToUpdate.getStock() - quantity);
                this.saveProduct(productToUpdate);
                return true;
            }
        }
        return false;
    }
}
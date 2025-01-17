package com.tpIngSoft1.restApi.repository;
import java.util.Optional;
import com.tpIngSoft1.restApi.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);
    Optional<Product> findById(String id);
}

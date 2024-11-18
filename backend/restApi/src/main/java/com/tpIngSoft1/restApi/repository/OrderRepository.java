package com.tpIngSoft1.restApi.repository;

import com.tpIngSoft1.restApi.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUsername(String username);
    Optional<Order> findById(String id);
}

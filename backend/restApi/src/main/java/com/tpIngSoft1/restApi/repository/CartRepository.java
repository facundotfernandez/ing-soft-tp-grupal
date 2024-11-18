package com.tpIngSoft1.restApi.repository;

import com.tpIngSoft1.restApi.domain.Cart;
import com.tpIngSoft1.restApi.domain.Product; //Definir un productoDTO si es necesario
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserId(String userId);
}
package com.tpIngSoft1.restApi.repository;


import com.tpIngSoft1.restApi.domain.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
     Optional<User> findByUserNameAndPassword(String userName, String passwrord);
}

package com.tpIngSoft1.restApi.repository;


import com.tpIngSoft1.restApi.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

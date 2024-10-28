package com.tpIngSoft1.restApi.controller;

import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.User;
import com.tpIngSoft1.restApi.repository.UserRepository;
import org.springframework.validation.annotation.Validated;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/login")
    public Optional<User> getUser(@RequestBody User user) {
        return userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
    }
}

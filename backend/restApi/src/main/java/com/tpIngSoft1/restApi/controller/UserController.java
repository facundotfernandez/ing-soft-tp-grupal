package com.tpIngSoft1.restApi.controller;

import com.tpIngSoft1.restApi.domain.User;
import com.tpIngSoft1.restApi.repository.UserRepository;
import org.springframework.validation.annotation.Validated;
import com.tpIngSoft1.restApi.service.UserService;
import com.tpIngSoft1.restApi.dto.UserDTO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping()
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        Optional<User> foundUser = userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());

        if (foundUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundUser.get(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {

        if (userService.findByUsername(userDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>("El nombre de usuario ya está registrado", HttpStatus.CONFLICT);
        }

        if (userService.findByEmail(userDTO.getEmail()).isPresent()) {
            return new ResponseEntity<>("El correo electrónico ya está registrado", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setLastname(userDTO.getLastname());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setGender(userDTO.getGender());
        user.setProfilePic(userDTO.getProfilePic());

        userService.registerUser(user);

        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
    }
}
package com.tpIngSoft1.restApi.controller;

import com.tpIngSoft1.restApi.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.tpIngSoft1.restApi.domain.User;
import com.tpIngSoft1.restApi.service.JwtService;
import org.springframework.validation.annotation.Validated;
import com.tpIngSoft1.restApi.service.UserService;
import com.tpIngSoft1.restApi.dto.UserDTO;
import com.tpIngSoft1.restApi.utils.ApiResponse;

import java.util.*;


@RestController
@RequestMapping()
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private String[] decodeBasicAuth(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring(6);
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decodedString = new String(decodedBytes);
            return decodedString.split(":");
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginUser(@RequestHeader("Authorization") String authHeader) {
        String[] credentials = decodeBasicAuth(authHeader);
        if (credentials == null) {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Ah Ah Ah! No dijiste la palabra mágica", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        String username = credentials[0];
        String password = credentials[1];
        Optional<User> foundUser = userService.findUserByUsernameAndPassword(username, password);

        if (foundUser.isPresent()) {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Ha iniciado sesión", foundUser.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "error", "Contraseña incorrecta!", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody UserDTO userDTO) {

        if (userService.findByUsername(userDTO.getUsername()).isPresent()) {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "El nombre de usuario ya está registrado", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        if (userService.findByEmail(userDTO.getEmail()).isPresent()) {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "El correo electrónico ya está registrado", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
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
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
        userService.registerUser(user);

        ApiResponse<User> response = new ApiResponse<>(HttpStatus.CREATED.value(), "success", "Usuario registrado exitosamente", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Map<String, String>>> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (jwtService.validateToken(refreshToken)) {
            String username = jwtService.getUsernameFromToken(refreshToken);
            User user = userService.findByUsername(username).orElseThrow();
            String newAccessToken = jwtService.generateAccessToken(user);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Tokens actualizados exitosamente", tokens);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ApiResponse<Map<String, String>> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token inválido", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginWithToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Ah Ah Ah! No dijiste la palabra mágica", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        String username = jwtService.getUsernameFromToken(token);
        if (username == null || !jwtService.validateToken(token)) {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token Invalido", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        Optional<User> foundUser = userService.findUserByUsername(username);
        if (foundUser.isEmpty()) {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "error", "Token invalido", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Ha iniciado sesión", foundUser.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/recovery")
    public ResponseEntity<ApiResponse<String>> recoverPassword(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Ah Ah Ah! No dijiste la palabra mágica", "");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        String username = jwtService.getUsernameFromToken(token);

        if (username == null || !jwtService.validateToken(token)) {
            ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token Invalido", "");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        Optional<User> foundUser = userService.findUserByUsername(username);
        if (foundUser.isEmpty()) {
            ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "error", "No se encontró al usuario", "");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Contraseña recuperada", foundUser.get().getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
package com.tpIngSoft1.restApi.controller;

import com.tpIngSoft1.restApi.domain.User;
import com.tpIngSoft1.restApi.dto.UserDTO;
import com.tpIngSoft1.restApi.service.AuthService;
import com.tpIngSoft1.restApi.service.JwtService;
import com.tpIngSoft1.restApi.service.UserService;
import com.tpIngSoft1.restApi.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginUser(@RequestHeader("Authorization") String authHeader) {
        Optional<User> foundUser = authService.authenticateBasic(authHeader);
        if (foundUser.isEmpty()) {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Ah Ah Ah! No dijiste la palabra mágica", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Ha iniciado sesión", foundUser.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
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
        if (authService.validateRefreshToken(refreshToken)) {
            String username = jwtService.getUsernameFromToken(refreshToken);
            User user = userService.findByUsername(username).orElseThrow();
            String newAccessToken = jwtService.generateAccessToken(user);
            Map<String, String> tokens = Map.of("accessToken", newAccessToken);
            ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Tokens actualizados exitosamente", tokens);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ApiResponse<Map<String, String>> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token inválido", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginWithToken(@RequestHeader("Authorization") String authHeader) {
        Optional<User> foundUser = authService.authenticateBearer(authHeader);
        if (foundUser.isEmpty()) {
            ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token inválido", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Ha iniciado sesión", foundUser.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/recovery")
    public ResponseEntity<ApiResponse<String>> recoverPassword(@RequestHeader("Authorization") String authHeader) {
        Optional<User> foundUser = authService.authenticateBearer(authHeader);
        if (foundUser.isEmpty()) {
            ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token Invalido", "");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Contraseña recuperada", foundUser.get().getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

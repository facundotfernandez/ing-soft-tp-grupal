package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    public String[] decodeBasicAuth(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring(6);
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decodedString = new String(decodedBytes);
            return decodedString.split(":");
        }
        return null;
    }

    public Optional<User> authenticateBasic(String authHeader) {
        String[] credentials = decodeBasicAuth(authHeader);
        if (credentials == null) {
            return Optional.empty();
        }
        String username = credentials[0];
        String password = credentials[1];
        return userService.findUserByUsernameAndPassword(username, password);
    }

    public Optional<User> authenticateBearer(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }
        String token = authHeader.substring(7);
        String username = jwtService.getUsernameFromToken(token);
        if (username == null || !jwtService.validateToken(token)) {
            return Optional.empty();
        }
        return userService.findUserByUsername(username);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return jwtService.validateToken(refreshToken);
    }
}

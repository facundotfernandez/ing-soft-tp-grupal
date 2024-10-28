package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.domain.User;
import com.tpIngSoft1.restApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        user.setAccessToken(generateAccessToken(user));
        user.setProfilePic("https://avatar.iran.liara.run/public");
        user.setRole("client");
        return userRepository.save(user);
    }

    private String generateAccessToken(User user) {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJiNTZjMGMzYS1kYzRlLTRmM2UtOWUxYi0xYjQ0MjFhMDA1ZDAiLCJ1c2VybmFtZSI6Imd1ZXN0IiwicGFzc3dvcmQiOiJndWVzdCIsImlhdCI6MTY1NzAwNjc1OX0.3hIsSiIWOJ630WBJb7kn6tRtj49QXcTZX0D0neAIYgo";
    }

    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.filter(u -> u.getPassword().equals(password));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
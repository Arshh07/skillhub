package com.skillhub.hub.service;

import org.springframework.stereotype.Service;

import com.skillhub.hub.model.User;
import com.skillhub.hub.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(User user) {
        // Hash password using BCrypt before saving
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashed);
        return repo.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return BCrypt.checkpw(rawPassword, user.getPassword());
    }
}

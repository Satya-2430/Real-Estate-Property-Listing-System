package com.realestate.user.controller;

import com.realestate.user.entity.Role;
import com.realestate.user.entity.User;
import com.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "user-service");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create-test-user")
    public ResponseEntity<Map<String, Object>> createTestUser() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Check if test user already exists
            if (userRepository.findByEmail("test@example.com").isPresent()) {
                response.put("message", "Test user already exists");
                response.put("email", "test@example.com");
                return ResponseEntity.ok(response);
            }

            // Create test user
            User testUser = new User();
            testUser.setEmail("test@example.com");
            testUser.setPassword(passwordEncoder.encode("password"));
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            testUser.setContactNumber("1234567890");
            testUser.setRole(Role.BUYER);
            testUser.setIsActive(true);

            User saved = userRepository.save(testUser);
            
            response.put("message", "Test user created successfully");
            response.put("id", saved.getId());
            response.put("email", saved.getEmail());
            response.put("role", saved.getRole());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to create test user: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
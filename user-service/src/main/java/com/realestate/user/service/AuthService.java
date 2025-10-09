package com.realestate.user.service;

import com.realestate.user.dto.LoginRequest;
import com.realestate.user.dto.RegisterRequest;
import com.realestate.user.dto.UserResponse;
import com.realestate.user.entity.User;
import com.realestate.user.repository.UserRepository;
import com.realestate.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private String generateProfileImage(String name) {
        String encodedName = name.replace(" ", "+");
        return "https://ui-avatars.com/api/?name=" + encodedName + "&background=007bff&color=fff&size=200";
    }

    public UserResponse login(LoginRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials for user: " + request.getEmail());
            }

            String token = jwtUtil.generateTokenWithUserInfo(user.getEmail(), user.getId(), user.getRole().toString());
            
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setContactNumber(user.getContactNumber());
            response.setProfileImage(user.getProfileImage());
            response.setAddress(user.getAddress());
            response.setCity(user.getCity());
            response.setState(user.getState());
            response.setZip(user.getZip());
            response.setCompanyName(user.getCompanyName());
            response.setAbout(user.getAbout());
            response.setRole(user.getRole());
            response.setIsActive(user.getIsActive());
            response.setCreatedAt(user.getCreatedAt());
            response.setToken(token);
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }

    public UserResponse register(RegisterRequest request) {
        // Additional validation
        validateRegistrationRequest(request);
        
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        String[] nameParts = request.getName().split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setContactNumber(request.getContactNumber());
        user.setProfileImage(generateProfileImage(request.getName()));
        user.setRole(request.getRole());
        user.setIsActive(true);

        user = userRepository.save(user);

        String token = jwtUtil.generateTokenWithUserInfo(user.getEmail(), user.getId(), user.getRole().toString());
        
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setContactNumber(user.getContactNumber());
        response.setProfileImage(user.getProfileImage());
        response.setRole(user.getRole());
        response.setIsActive(user.getIsActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setToken(token);
        
        return response;
    }
    
    private void validateRegistrationRequest(RegisterRequest request) {
        // Validate name - only alphabets and spaces, 3-50 characters
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        if (!request.getName().matches("^[a-zA-Z\\s]{3,50}$")) {
            throw new RuntimeException("Name must contain only alphabets and spaces, 3-50 characters");
        }
        
        // Validate email format
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (!request.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new RuntimeException("Invalid email format");
        }
        
        // Validate password - at least 6 chars with letters, numbers, symbols
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }
        if (request.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }
        if (!request.getPassword().matches(".*[a-zA-Z].*")) {
            throw new RuntimeException("Password must contain at least one letter");
        }
        if (!request.getPassword().matches(".*\\d.*")) {
            throw new RuntimeException("Password must contain at least one number");
        }
        if (!request.getPassword().matches(".*[@$!%*?&].*")) {
            throw new RuntimeException("Password must contain at least one symbol (@$!%*?&)");
        }
        
        // Validate contact number - starts with 6/7/8/9 and exactly 10 digits
        if (request.getContactNumber() == null || request.getContactNumber().trim().isEmpty()) {
            throw new RuntimeException("Contact number is required");
        }
        if (!request.getContactNumber().matches("^[6-9]\\d{9}$")) {
            throw new RuntimeException("Contact number must start with 6/7/8/9 and be exactly 10 digits");
        }
        
        // Validate role
        if (request.getRole() == null) {
            throw new RuntimeException("Role is required");
        }
    }
}
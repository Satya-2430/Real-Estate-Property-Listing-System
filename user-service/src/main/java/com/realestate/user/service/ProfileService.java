package com.realestate.user.service;

import com.realestate.user.dto.ProfileUpdateRequest;
import com.realestate.user.dto.UserResponse;
import com.realestate.user.entity.User;
import com.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

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
        
        return response;
    }

    @Transactional
    public UserResponse updateUserProfile(Long userId, ProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setContactNumber(request.getContactNumber());
        user.setProfileImage(request.getProfileImage());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setZip(request.getZip());
        user.setCompanyName(request.getCompanyName());
        user.setAbout(request.getAbout());

        user = userRepository.save(user);

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
        
        return response;
    }
}
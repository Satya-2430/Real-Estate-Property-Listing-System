package com.realestate.user.controller;

import com.realestate.user.dto.ProfileUpdateRequest;
import com.realestate.user.dto.UserResponse;
import com.realestate.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable Long userId) {
        UserResponse response = profileService.getUserProfile(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserResponse> updateUserProfile(@PathVariable Long userId, @RequestBody ProfileUpdateRequest request) {
        UserResponse response = profileService.updateUserProfile(userId, request);
        return ResponseEntity.ok(response);
    }
}
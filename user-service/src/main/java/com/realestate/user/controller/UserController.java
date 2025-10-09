package com.realestate.user.controller;

import com.realestate.user.dto.UpdateProfileRequest;
import com.realestate.user.dto.UserResponse;
import com.realestate.user.entity.Role;
import com.realestate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<Page<UserResponse>> getUsersByRole(
            @PathVariable Role role,
            Pageable pageable) {
        Page<UserResponse> users = userService.getUsersByRole(role, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}/list")
    public ResponseEntity<List<UserResponse>> getUsersByRoleList(@PathVariable Role role) {
        List<UserResponse> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/toggle-status")
    public ResponseEntity<Void> toggleUserStatus(@PathVariable Long userId) {
        userService.toggleUserStatus(userId);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/preferences")
    public ResponseEntity<Map<String, Object>> getUserPreferences(@PathVariable Long userId) {
        // Return empty preferences for now - can be implemented later
        return ResponseEntity.ok(Map.of());
    }
}
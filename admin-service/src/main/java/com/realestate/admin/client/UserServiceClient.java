package com.realestate.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    
    @GetMapping("/users")
    List<Map<String, Object>> getAllUsers();
    
    @GetMapping("/users/{userId}/profile")
    Map<String, Object> getUserProfile(@PathVariable Long userId);
    
    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable Long userId);
    
    @PutMapping("/users/{userId}/toggle-status")
    void toggleUserStatus(@PathVariable Long userId);
    
    @GetMapping("/users/role/{role}/list")
    List<Map<String, Object>> getUsersByRole(@PathVariable String role);
}
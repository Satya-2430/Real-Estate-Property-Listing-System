package com.realestate.dashboard.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    
    @GetMapping("/users/{userId}/profile")
    Map<String, Object> getUserProfile(@PathVariable Long userId);
    
    @GetMapping("/users/{userId}/favorites")
    List<Map<String, Object>> getUserFavorites(@PathVariable Long userId);
    
    @GetMapping("/users/{userId}/preferences")
    Map<String, Object> getUserPreferences(@PathVariable Long userId);
}
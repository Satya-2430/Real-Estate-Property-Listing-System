package com.realestate.dashboard.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "property-service")
public interface PropertyServiceClient {
    
    @GetMapping("/properties/owner/{ownerId}")
    List<Map<String, Object>> getOwnerProperties(@PathVariable Long ownerId);
    
    @GetMapping("/properties/recommendations/{userId}")
    List<Map<String, Object>> getRecommendations(@PathVariable Long userId);
    
    @GetMapping("/bookings/user/{userId}")
    List<Map<String, Object>> getUserBookings(@PathVariable Long userId);
    
    @GetMapping("/bookings/owner/{ownerId}")
    List<Map<String, Object>> getOwnerBookings(@PathVariable Long ownerId);
    
    @GetMapping("/properties/favorites/user/{userId}")
    List<Map<String, Object>> getUserFavorites(@PathVariable Long userId);
    
    @GetMapping("/inquiries")
    List<Map<String, Object>> getAllInquiries();
    
    @GetMapping("/inquiries/buyer/{buyerId}")
    List<Map<String, Object>> getBuyerInquiries(@PathVariable Long buyerId);
    
    @GetMapping("/inquiries/seller/{sellerId}")
    List<Map<String, Object>> getSellerInquiries(@PathVariable Long sellerId);
}
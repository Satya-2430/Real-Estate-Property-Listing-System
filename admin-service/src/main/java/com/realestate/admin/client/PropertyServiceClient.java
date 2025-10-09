package com.realestate.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "property-service")
public interface PropertyServiceClient {
    
    @GetMapping("/properties")
    List<Map<String, Object>> getAllProperties();
    
    @GetMapping("/properties/{id}")
    Map<String, Object> getPropertyById(@PathVariable Long id);
    
    @DeleteMapping("/properties/admin/{id}")
    void deleteProperty(@PathVariable Long id);
    
    @GetMapping("/bookings")
    List<Map<String, Object>> getAllBookings();
    
    @GetMapping("/bookings/user/{userId}")
    List<Map<String, Object>> getUserBookings(@PathVariable Long userId);
    
    @GetMapping("/inquiries")
    List<Map<String, Object>> getAllInquiries();
}
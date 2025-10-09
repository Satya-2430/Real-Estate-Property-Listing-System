package com.realestate.admin.controller;

import com.realestate.admin.dto.AdminDashboardResponse;
import com.realestate.admin.dto.SystemStatsResponse;
import com.realestate.admin.entity.UserActivity;
import com.realestate.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AdminAnalyticsService analyticsService;
    private final AdminUserManagementService userManagementService;
    private final AdminPropertyManagementService propertyManagementService;



    @GetMapping("/stats")
    public ResponseEntity<SystemStatsResponse> getSystemStats() {
        SystemStatsResponse stats = analyticsService.getSystemStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<Map<String, Object>> users = userManagementService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Map<String, Object>> getUserDetails(@PathVariable Long userId) {
        Map<String, Object> user = userManagementService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<Map<String, Object>>> getUsersByRole(@PathVariable String role) {
        List<Map<String, Object>> users = userManagementService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userManagementService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}/toggle-status")
    public ResponseEntity<Void> toggleUserStatus(@PathVariable Long userId) {
        userManagementService.toggleUserStatus(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/properties")
    public ResponseEntity<List<Map<String, Object>>> getAllProperties() {
        List<Map<String, Object>> properties = propertyManagementService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<Map<String, Object>> getPropertyDetails(@PathVariable Long propertyId) {
        Map<String, Object> property = propertyManagementService.getPropertyDetails(propertyId);
        return ResponseEntity.ok(property);
    }

    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long propertyId) {
        propertyManagementService.deleteProperty(propertyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Map<String, Object>>> getAllBookings() {
        List<Map<String, Object>> bookings = propertyManagementService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/property-inquiries")
    public ResponseEntity<List<Map<String, Object>>> getAllPropertyInquiries() {
        List<Map<String, Object>> inquiries = propertyManagementService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<UserActivity>> getRecentActivities() {
        List<UserActivity> activities = analyticsService.getRecentActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/activities/user/{userId}")
    public ResponseEntity<List<UserActivity>> getUserActivities(@PathVariable Long userId) {
        List<UserActivity> activities = analyticsService.getUserActivities(userId);
        return ResponseEntity.ok(activities);
    }
    
    @DeleteMapping("/management/contact-inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteContactInquiry(@PathVariable Long inquiryId) {
        adminService.deleteContactInquiry(inquiryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        
        try {
            userManagementService.getAllUsers();
            status.put("user-service", "UP");
        } catch (Exception e) {
            status.put("user-service", "DOWN: " + e.getMessage());
        }
        
        try {
            propertyManagementService.getAllProperties();
            status.put("property-service", "UP");
        } catch (Exception e) {
            status.put("property-service", "DOWN: " + e.getMessage());
        }
        
        try {
            analyticsService.getSystemStats();
            status.put("analytics", "UP");
        } catch (Exception e) {
            status.put("analytics", "DOWN: " + e.getMessage());
        }
        
        return ResponseEntity.ok(status);
    }
}
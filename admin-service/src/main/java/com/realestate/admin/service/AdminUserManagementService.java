package com.realestate.admin.service;

import com.realestate.admin.client.PaymentServiceClient;
import com.realestate.admin.client.PropertyServiceClient;
import com.realestate.admin.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminUserManagementService {

    private final UserServiceClient userServiceClient;
    private final PropertyServiceClient propertyServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final AdminAnalyticsService analyticsService;

    public List<Map<String, Object>> getAllUsers() {
        try {
            return userServiceClient.getAllUsers();
        } catch (Exception e) {
            // Return empty list if user service is down
            return List.of();
        }
    }

    public Map<String, Object> getUserDetails(Long userId) {
        Map<String, Object> user;
        try {
            user = userServiceClient.getUserProfile(userId);
        } catch (Exception e) {
            // Return basic user info if service is down
            user = Map.of("id", userId, "error", "User service unavailable");
        }
        
        // Enrich with additional data
        try {
            List<Map<String, Object>> userBookings = propertyServiceClient.getUserBookings(userId);
            user.put("bookings", userBookings);
        } catch (Exception e) {
            user.put("bookings", List.of());
        }
        
        try {
            List<Map<String, Object>> userTransactions = paymentServiceClient.getUserTransactions(userId);
            user.put("transactions", userTransactions);
        } catch (Exception e) {
            user.put("transactions", List.of());
        }
        
        try {
            List<Map<String, Object>> userPaymentMethods = paymentServiceClient.getUserPaymentMethods(userId);
            user.put("paymentMethods", userPaymentMethods);
        } catch (Exception e) {
            user.put("paymentMethods", List.of());
        }
        
        try {
            user.put("activities", analyticsService.getUserActivities(userId));
        } catch (Exception e) {
            user.put("activities", List.of());
        }
        
        return user;
    }

    public void deleteUser(Long userId) {
        // Track activity
        analyticsService.trackUserActivity(null, "USER_DELETED", 
                "Admin deleted user: " + userId, null, null);
        
        userServiceClient.deleteUser(userId);
    }

    public void toggleUserStatus(Long userId) {
        // Track activity
        analyticsService.trackUserActivity(null, "USER_STATUS_CHANGED", 
                "Admin toggled status for user: " + userId, null, null);
        
        userServiceClient.toggleUserStatus(userId);
    }

    public List<Map<String, Object>> getUsersByRole(String role) {
        try {
            return userServiceClient.getUsersByRole(role);
        } catch (Exception e) {
            // Return empty list if user service is down
            return List.of();
        }
    }
}
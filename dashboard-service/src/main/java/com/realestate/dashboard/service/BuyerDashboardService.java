package com.realestate.dashboard.service;

import com.realestate.dashboard.client.*;
import com.realestate.dashboard.dto.BuyerDashboardResponse;
import com.realestate.dashboard.entity.SavedSearch;
import com.realestate.dashboard.repository.SavedSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BuyerDashboardService {

    private final UserServiceClient userServiceClient;
    private final PropertyServiceClient propertyServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final SavedSearchRepository savedSearchRepository;

    public BuyerDashboardResponse getBuyerDashboard(Long userId) {
        BuyerDashboardResponse dashboard = new BuyerDashboardResponse();
        
        // Initialize with default values
        BuyerDashboardResponse.BuyerMetrics metrics = new BuyerDashboardResponse.BuyerMetrics();
        metrics.setTotalBookings(0L);
        metrics.setCompletedVisits(0L);
        metrics.setPendingVisits(0L);
        metrics.setTotalSpent(BigDecimal.ZERO);
        dashboard.setMetrics(metrics);
        
        // Set default empty lists
        dashboard.setFavorites(List.of());
        dashboard.setRecentBookings(List.of());
        dashboard.setMyInquiries(List.of());
        dashboard.setRecommendations(List.of());
        dashboard.setRecentTransactions(List.of());
        
        try {
            // Get user profile and validate role
            Map<String, Object> userProfile = userServiceClient.getUserProfile(userId);
            if (!"BUYER".equals(userProfile.get("role"))) {
                throw new RuntimeException("Access denied: Only BUYER role can access buyer dashboard");
            }
            dashboard.setUserProfile(userProfile);
        } catch (Exception e) {
            // User profile failed, but continue with other data
        }
        
        try {
            dashboard.setPreferences(userServiceClient.getUserPreferences(userId));
        } catch (Exception e) {
            // Preferences failed, skip
        }
        
        try {
            List<Map<String, Object>> favorites = propertyServiceClient.getUserFavorites(userId);
            dashboard.setFavorites(favorites);
            System.out.println("Favorites retrieved: " + favorites.size());
        } catch (Exception e) {
            System.out.println("Favorites failed: " + e.getMessage());
        }
        
        List<Map<String, Object>> bookings = List.of();
        try {
            bookings = propertyServiceClient.getUserBookings(userId);
            dashboard.setRecentBookings(bookings.stream().limit(5).toList());
            
            // Update metrics with actual booking data
            metrics.setTotalBookings((long) bookings.size());
            metrics.setCompletedVisits(bookings.stream()
                    .filter(b -> "COMPLETED".equals(b.get("status")))
                    .count());
            metrics.setPendingVisits(bookings.stream()
                    .filter(b -> "PENDING".equals(b.get("status")))
                    .count());
            System.out.println("Bookings retrieved: " + bookings.size());
        } catch (Exception e) {
            System.out.println("Bookings failed: " + e.getMessage());
        }
        
        try {
            List<Map<String, Object>> inquiries = propertyServiceClient.getBuyerInquiries(userId);
            dashboard.setMyInquiries(inquiries);
            System.out.println("Inquiries retrieved: " + inquiries.size());
        } catch (Exception e) {
            System.out.println("Inquiries failed: " + e.getMessage());
        }
        
        try {
            List<Map<String, Object>> recommendations = propertyServiceClient.getRecommendations(userId);
            dashboard.setRecommendations(recommendations);
            System.out.println("Recommendations retrieved: " + recommendations.size());
        } catch (Exception e) {
            System.out.println("Recommendations failed: " + e.getMessage());
        }
        
        try {
            List<Map<String, Object>> transactions = paymentServiceClient.getUserTransactions(userId);
            dashboard.setRecentTransactions(transactions.stream().limit(5).toList());
            
            // Calculate total spent
            BigDecimal totalSpent = transactions.stream()
                    .filter(t -> "COMPLETED".equals(t.get("status")))
                    .map(t -> new BigDecimal(t.get("amount").toString()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            metrics.setTotalSpent(totalSpent);
            System.out.println("Transactions retrieved: " + transactions.size());
        } catch (Exception e) {
            System.out.println("Transactions failed: " + e.getMessage());
        }
        
        return dashboard;
    }
}
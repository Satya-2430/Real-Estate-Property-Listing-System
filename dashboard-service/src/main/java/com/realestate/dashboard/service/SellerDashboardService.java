package com.realestate.dashboard.service;

import com.realestate.dashboard.client.*;
import com.realestate.dashboard.dto.SellerDashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SellerDashboardService {

    private final UserServiceClient userServiceClient;
    private final PropertyServiceClient propertyServiceClient;
    private final PaymentServiceClient paymentServiceClient;

    public SellerDashboardResponse getSellerDashboard(Long userId) {
        SellerDashboardResponse dashboard = new SellerDashboardResponse();
        
        try {
            // Get user profile and validate role
            Map<String, Object> userProfile = userServiceClient.getUserProfile(userId);
            if (!"SELLER".equals(userProfile.get("role"))) {
                throw new RuntimeException("Access denied: Only SELLER role can access seller dashboard");
            }
            dashboard.setUserProfile(userProfile);
            
            // Get seller's properties
            List<Map<String, Object>> properties = propertyServiceClient.getOwnerProperties(userId);
            dashboard.setMyProperties(properties);
            
            // Get recent bookings for seller's properties
            List<Map<String, Object>> bookings = propertyServiceClient.getOwnerBookings(userId);
            dashboard.setRecentBookings(bookings.stream().limit(10).toList());
            
            // Get seller inquiries
            List<Map<String, Object>> inquiries = propertyServiceClient.getSellerInquiries(userId);
            dashboard.setMyInquiries(inquiries);
            // Also set as recentInquiries for frontend compatibility
            dashboard.setRecentInquiries(inquiries.stream().limit(10).toList());
            
            // Get all transactions to find earnings from property sales
            List<Map<String, Object>> allTransactions = paymentServiceClient.getAllTransactions();
            
            // Filter transactions for properties owned by this seller
            List<Long> propertyIds = properties.stream()
                    .map(p -> Long.valueOf(p.get("id").toString()))
                    .toList();
            
            List<Map<String, Object>> sellerTransactions = allTransactions.stream()
                    .filter(t -> t.get("propertyId") != null && propertyIds.contains(Long.valueOf(t.get("propertyId").toString())))
                    .toList();
            
            dashboard.setRecentTransactions(sellerTransactions.stream().limit(5).toList());
            
            // Calculate seller-specific metrics
            SellerDashboardResponse.SellerMetrics metrics = new SellerDashboardResponse.SellerMetrics();
            metrics.setTotalProperties((long) properties.size());
            metrics.setActiveListings(properties.stream()
                    .filter(p -> "AVAILABLE".equals(p.get("status")))
                    .count());
            metrics.setTotalInquiries((long) inquiries.size());
            
            // Calculate total earnings from property sales
            BigDecimal totalEarnings = sellerTransactions.stream()
                    .filter(t -> "COMPLETED".equals(t.get("status")))
                    .map(t -> new BigDecimal(t.get("amount").toString()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            metrics.setTotalEarnings(totalEarnings);
            
            dashboard.setMetrics(metrics);
            dashboard.setMyProperties(properties);
            
        } catch (Exception e) {
            // Handle service communication errors gracefully
            dashboard.setMetrics(new SellerDashboardResponse.SellerMetrics());
        }
        
        return dashboard;
    }
    
    private Map<String, Object> createPropertyAnalytics(Map<String, Object> property) {
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("propertyId", property.get("id"));
        analytics.put("title", property.get("title"));
        analytics.put("views", property.get("viewCount") != null ? property.get("viewCount") : 0);
        analytics.put("inquiries", 0); // Would be calculated from actual data
        analytics.put("bookings", 0); // Would be calculated from actual data
        analytics.put("status", property.get("status"));
        return analytics;
    }
}
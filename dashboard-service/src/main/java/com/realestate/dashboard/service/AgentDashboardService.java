package com.realestate.dashboard.service;

import com.realestate.dashboard.client.*;
import com.realestate.dashboard.dto.AgentDashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentDashboardService {

    private final UserServiceClient userServiceClient;
    private final PropertyServiceClient propertyServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final DashboardActivityService activityService;
    private final SavedSearchService savedSearchService;

    public AgentDashboardResponse getAgentDashboard(Long userId) {
        AgentDashboardResponse dashboard = new AgentDashboardResponse();
        
        try {
            // Log dashboard access
            activityService.logActivity(userId, "DASHBOARD_ACCESS", "Agent accessed dashboard");
            
            // Get user profile and validate role
            Map<String, Object> userProfile = userServiceClient.getUserProfile(userId);
            if (!"AGENT".equals(userProfile.get("role"))) {
                throw new RuntimeException("Access denied: Only AGENT role can access agent dashboard");
            }
            dashboard.setUserProfile(userProfile);
            
            // Get agent's properties
            List<Map<String, Object>> properties = propertyServiceClient.getOwnerProperties(userId);
            dashboard.setMyProperties(properties);
            
            // Get recent bookings for agent's properties
            List<Map<String, Object>> bookings = propertyServiceClient.getOwnerBookings(userId);
            dashboard.setRecentBookings(bookings.stream().limit(10).toList());
            
            // Get agent inquiries (same as seller inquiries)
            List<Map<String, Object>> agentInquiries = propertyServiceClient.getSellerInquiries(userId);
            dashboard.setRecentInquiries(agentInquiries.stream().limit(10).toList());
            
            // Get recent transactions
            List<Map<String, Object>> transactions = paymentServiceClient.getUserTransactions(userId);
            dashboard.setRecentTransactions(transactions.stream().limit(5).toList());
            
            // Calculate agent-specific metrics
            AgentDashboardResponse.AgentMetrics metrics = new AgentDashboardResponse.AgentMetrics();
            metrics.setTotalProperties((long) properties.size());
            metrics.setActiveListings(properties.stream()
                    .filter(p -> "AVAILABLE".equals(p.get("status")))
                    .count());
            metrics.setTotalInquiries((long) agentInquiries.size());
            
            // Calculate total earnings
            BigDecimal totalEarnings = transactions.stream()
                    .filter(t -> "COMPLETED".equals(t.get("status")))
                    .map(t -> new BigDecimal(t.get("amount").toString()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            metrics.setTotalEarnings(totalEarnings);
            
            dashboard.setMetrics(metrics);
            
        } catch (Exception e) {
            // Handle service communication errors gracefully
            dashboard.setMetrics(new AgentDashboardResponse.AgentMetrics());
        }
        
        return dashboard;
    }
}
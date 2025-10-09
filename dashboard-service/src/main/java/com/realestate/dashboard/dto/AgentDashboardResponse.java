package com.realestate.dashboard.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AgentDashboardResponse {
    private Map<String, Object> userProfile;
    private List<Map<String, Object>> myProperties;
    private List<Map<String, Object>> recentBookings;
    private List<Map<String, Object>> recentInquiries;
    private List<Map<String, Object>> recentTransactions;
    private AgentMetrics metrics;
    
    @Data
    public static class AgentMetrics {
        private Long totalProperties;
        private Long activeListings;
        private Long totalInquiries;
        private java.math.BigDecimal totalEarnings;
    }
}
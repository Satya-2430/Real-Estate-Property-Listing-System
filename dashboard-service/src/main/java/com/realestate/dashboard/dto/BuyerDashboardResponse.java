package com.realestate.dashboard.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BuyerDashboardResponse {
    private Map<String, Object> userProfile;
    private List<Map<String, Object>> favorites;
    private List<Map<String, Object>> recentBookings;
    private List<Map<String, Object>> myInquiries;
    private List<Map<String, Object>> recommendations;
    private List<Map<String, Object>> recentTransactions;
    private Map<String, Object> preferences;
    private BuyerMetrics metrics;
    
    @Data
    public static class BuyerMetrics {
        private Long totalBookings;
        private Long completedVisits;
        private Long pendingVisits;
        private java.math.BigDecimal totalSpent;
    }
}
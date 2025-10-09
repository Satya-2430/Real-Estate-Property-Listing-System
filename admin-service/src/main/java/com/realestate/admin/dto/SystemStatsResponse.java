package com.realestate.admin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class SystemStatsResponse {
    private Long totalUsers;
    private Long totalProperties;
    private Long totalBookings;
    private Long totalTransactions;
    private BigDecimal totalRevenue;
    private Long activeUsers;
    private Long pendingBookings;
    private Long completedTransactions;
    private Map<String, Long> usersByRole;
    private Map<String, Long> propertiesByType;
    private Map<String, Long> bookingsByStatus;
    private Map<String, Long> transactionsByStatus;
    private Map<String, Long> recentActivities;
}
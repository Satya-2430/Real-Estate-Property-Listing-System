package com.realestate.admin.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminDashboardResponse {
    private SystemStatsResponse stats;
    private List<Map<String, Object>> recentUsers;
    private List<Map<String, Object>> recentProperties;
    private List<Map<String, Object>> recentBookings;
    private List<Map<String, Object>> recentTransactions;
    private List<Map<String, Object>> recentContacts;
    private List<Map<String, Object>> recentVisitors;
    private Long totalContacts;
    private Long totalVisitors;
    private Long todayVisitors;
}
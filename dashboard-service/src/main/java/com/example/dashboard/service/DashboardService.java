package com.example.dashboard.service;

import com.example.dashboard.dto.SummaryStats;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    // TODO: replace Feign client calls (stubs now)
    public SummaryStats getSummary() {
        long totalUsers = 0; // call auth-user-service
        long totalProperties = 0; // call property-service
        long totalBookings = 0; // call booking-service
        long totalTransactions = 0; // call transaction-service
        return new SummaryStats(totalUsers, totalProperties, totalBookings, totalTransactions);
    }
}

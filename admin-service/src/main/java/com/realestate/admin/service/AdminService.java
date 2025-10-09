package com.realestate.admin.service;

import com.realestate.admin.client.*;
import com.realestate.admin.dto.AdminDashboardResponse;
import com.realestate.admin.dto.SystemStatsResponse;
import com.realestate.admin.entity.ContactInquiry;
import com.realestate.admin.repository.ContactInquiryRepository;
import com.realestate.admin.service.ContactInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserServiceClient userServiceClient;
    private final PropertyServiceClient propertyServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final AdminAnalyticsService analyticsService;
    private final ContactInquiryRepository contactInquiryRepository;
    private final ContactInquiryService contactInquiryService;

    public AdminDashboardResponse getDashboard() {
        AdminDashboardResponse dashboard = new AdminDashboardResponse();
        
        try {
            // Get system stats
            dashboard.setStats(analyticsService.getSystemStats());
            
            // Get recent data from all services
            List<Map<String, Object>> allUsers = userServiceClient.getAllUsers();
            List<Map<String, Object>> allProperties = propertyServiceClient.getAllProperties();
            List<Map<String, Object>> allBookings = propertyServiceClient.getAllBookings();
            List<Map<String, Object>> allTransactions = paymentServiceClient.getAllTransactions();
            
            // Get recent items (last 10)
            dashboard.setRecentUsers(allUsers.stream().limit(10).toList());
            dashboard.setRecentProperties(allProperties.stream().limit(10).toList());
            dashboard.setRecentBookings(allBookings.stream().limit(10).toList());
            dashboard.setRecentTransactions(allTransactions.stream().limit(10).toList());
            
            // Get recent contact inquiries
            dashboard.setRecentContacts(contactInquiryRepository.findAllByOrderByCreatedAtDesc()
                    .stream().limit(10).map(this::convertContactInquiryToMap).toList());
            dashboard.setRecentVisitors(contactInquiryRepository.findAllByOrderByCreatedAtDesc()
                    .stream().limit(10).map(this::convertContactInquiryToMap).toList());
            
        } catch (Exception e) {
            // Handle service communication errors gracefully
            dashboard.setStats(new SystemStatsResponse());
        }
        
        return dashboard;
    }

    private Map<String, Object> convertContactInquiryToMap(ContactInquiry inquiry) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", inquiry.getId());
        map.put("name", inquiry.getName());
        map.put("email", inquiry.getEmail());
        map.put("subject", inquiry.getSubject());
        map.put("message", inquiry.getMessage());
        map.put("status", inquiry.getStatus());
        map.put("createdAt", inquiry.getCreatedAt());
        return map;
    }

    public void deleteContactInquiry(Long inquiryId) {
        contactInquiryService.deleteInquiry(inquiryId);
    }
}
package com.realestate.admin.service;

import com.realestate.admin.client.PropertyServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminPropertyManagementService {

    private final PropertyServiceClient propertyServiceClient;
    private final AdminAnalyticsService analyticsService;

    public List<Map<String, Object>> getAllProperties() {
        return propertyServiceClient.getAllProperties();
    }

    public Map<String, Object> getPropertyDetails(Long propertyId) {
        return propertyServiceClient.getPropertyById(propertyId);
    }

    public void deleteProperty(Long propertyId) {
        // Track activity
        analyticsService.trackUserActivity(null, "PROPERTY_DELETED", 
                "Admin deleted property: " + propertyId, null, null);
        
        propertyServiceClient.deleteProperty(propertyId);
    }

    public List<Map<String, Object>> getAllBookings() {
        return propertyServiceClient.getAllBookings();
    }

    public List<Map<String, Object>> getAllInquiries() {
        return propertyServiceClient.getAllInquiries();
    }
}
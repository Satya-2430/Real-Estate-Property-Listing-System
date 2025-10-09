package com.realestate.dashboard.service;

import com.realestate.dashboard.client.PropertyServiceClient;
import com.realestate.dashboard.client.UserServiceClient;
import com.realestate.dashboard.client.PaymentServiceClient;
import com.realestate.dashboard.dto.SellerDashboardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerDashboardServiceTest {

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private PropertyServiceClient propertyServiceClient;

    @Mock
    private PaymentServiceClient paymentServiceClient;

    @InjectMocks
    private SellerDashboardService sellerDashboardService;

    private Map<String, Object> testUser;
    private List<Map<String, Object>> testProperties;

    @BeforeEach
    void setUp() {
        testUser = new HashMap<>();
        testUser.put("id", 1L);
        testUser.put("email", "seller@example.com");
        testUser.put("role", "SELLER");

        Map<String, Object> property = new HashMap<>();
        property.put("id", 1L);
        property.put("title", "Test Property");
        property.put("status", "AVAILABLE");
        testProperties = Arrays.asList(property);
    }

    @Test
    void getSellerDashboard_Success() {
        when(userServiceClient.getUserProfile(anyLong())).thenReturn(testUser);
        when(propertyServiceClient.getOwnerProperties(anyLong())).thenReturn(testProperties);
        when(propertyServiceClient.getOwnerBookings(anyLong())).thenReturn(Arrays.asList());
        when(propertyServiceClient.getSellerInquiries(anyLong())).thenReturn(Arrays.asList());
        when(paymentServiceClient.getAllTransactions()).thenReturn(Arrays.asList());

        SellerDashboardResponse result = sellerDashboardService.getSellerDashboard(1L);

        assertNotNull(result);
        assertNotNull(result.getUserProfile());
        assertNotNull(result.getMyProperties());
        verify(userServiceClient).getUserProfile(1L);
        verify(propertyServiceClient).getOwnerProperties(1L);
    }

    @Test
    void getSellerDashboard_NonSellerRole_ThrowsException() {
        testUser.put("role", "BUYER");
        when(userServiceClient.getUserProfile(anyLong())).thenReturn(testUser);

        assertThrows(RuntimeException.class, () -> sellerDashboardService.getSellerDashboard(1L));
    }
}
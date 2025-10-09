package com.realestate.admin.service;

import com.realestate.admin.client.PaymentServiceClient;
import com.realestate.admin.client.PropertyServiceClient;
import com.realestate.admin.client.UserServiceClient;
import com.realestate.admin.dto.SystemStatsResponse;
import com.realestate.admin.entity.UserActivity;
import com.realestate.admin.repository.ContactInquiryRepository;
import com.realestate.admin.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAnalyticsService {

    private final UserServiceClient userServiceClient;
    private final PropertyServiceClient propertyServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final UserActivityRepository userActivityRepository;
    private final ContactInquiryRepository contactInquiryRepository;

    public SystemStatsResponse getSystemStats() {
        SystemStatsResponse stats = new SystemStatsResponse();
        
        try {
            // Get data from all services
            List<Map<String, Object>> users = userServiceClient.getAllUsers();
            List<Map<String, Object>> properties = propertyServiceClient.getAllProperties();
            List<Map<String, Object>> bookings = propertyServiceClient.getAllBookings();
            List<Map<String, Object>> transactions = paymentServiceClient.getAllTransactions();
            
            // Basic counts
            stats.setTotalUsers((long) users.size());
            stats.setTotalProperties((long) properties.size());
            stats.setTotalBookings((long) bookings.size());
            stats.setTotalTransactions((long) transactions.size());
            
            // Calculate revenue
            BigDecimal totalRevenue = transactions.stream()
                    .filter(t -> "COMPLETED".equals(t.get("status")))
                    .map(t -> new BigDecimal(t.get("amount").toString()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            stats.setTotalRevenue(totalRevenue);
            
            // Active users (users with recent activity)
            LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
            List<UserActivity> recentActivities = userActivityRepository.findRecentActivities(weekAgo);
            stats.setActiveUsers((long) recentActivities.stream()
                    .map(UserActivity::getUserId)
                    .distinct()
                    .count());
            
            // Pending bookings
            stats.setPendingBookings(bookings.stream()
                    .filter(b -> "PENDING".equals(b.get("status")))
                    .count());
            
            // Completed transactions
            stats.setCompletedTransactions(transactions.stream()
                    .filter(t -> "COMPLETED".equals(t.get("status")))
                    .count());
            
            // Users by role
            Map<String, Long> usersByRole = users.stream()
                    .collect(Collectors.groupingBy(
                            u -> u.get("role").toString(),
                            Collectors.counting()
                    ));
            stats.setUsersByRole(usersByRole);
            
            // Properties by type
            Map<String, Long> propertiesByType = properties.stream()
                    .collect(Collectors.groupingBy(
                            p -> p.get("propertyType") != null ? p.get("propertyType").toString() : "UNKNOWN",
                            Collectors.counting()
                    ));
            stats.setPropertiesByType(propertiesByType);
            
            // Bookings by status
            Map<String, Long> bookingsByStatus = bookings.stream()
                    .collect(Collectors.groupingBy(
                            b -> b.get("status").toString(),
                            Collectors.counting()
                    ));
            stats.setBookingsByStatus(bookingsByStatus);
            
            // Transactions by status
            Map<String, Long> transactionsByStatus = transactions.stream()
                    .collect(Collectors.groupingBy(
                            t -> t.get("status").toString(),
                            Collectors.counting()
                    ));
            stats.setTransactionsByStatus(transactionsByStatus);
            
            // Recent activities
            List<Object[]> activityStats = userActivityRepository.getActivityTypeStats();
            Map<String, Long> recentActivitiesMap = new HashMap<>();
            for (Object[] stat : activityStats) {
                recentActivitiesMap.put(stat[0].toString(), (Long) stat[1]);
            }
            stats.setRecentActivities(recentActivitiesMap);
            
        } catch (Exception e) {
            // Handle service communication errors gracefully
            stats.setTotalUsers(0L);
            stats.setTotalProperties(0L);
            stats.setTotalBookings(0L);
            stats.setTotalTransactions(0L);
            stats.setTotalRevenue(BigDecimal.ZERO);
        }
        
        return stats;
    }

    public void trackUserActivity(Long userId, String activityType, String description, String ipAddress, String userAgent) {
        UserActivity activity = new UserActivity();
        activity.setUserId(userId);
        activity.setActivityType(activityType);
        activity.setDescription(description);
        activity.setIpAddress(ipAddress);
        activity.setUserAgent(userAgent);
        userActivityRepository.save(activity);
    }

    public List<UserActivity> getUserActivities(Long userId) {
        return userActivityRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<UserActivity> getRecentActivities() {
        return userActivityRepository.findTop10ByOrderByCreatedAtDesc();
    }
}
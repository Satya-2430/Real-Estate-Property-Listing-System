package com.realestate.dashboard.service;

import com.realestate.dashboard.entity.ActivityLog;
import com.realestate.dashboard.repository.ActivityLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardActivityServiceTest {

    @Mock
    private ActivityLogRepository activityLogRepository;

    @InjectMocks
    private DashboardActivityService dashboardActivityService;

    private ActivityLog testActivity;

    @BeforeEach
    void setUp() {
        testActivity = new ActivityLog();
        testActivity.setId(1L);
        testActivity.setUserId(1L);
        testActivity.setActivityType("LOGIN");
        testActivity.setActivityDescription("User logged in");
    }

    @Test
    void logActivity_Success() {
        when(activityLogRepository.save(any(ActivityLog.class))).thenReturn(testActivity);

        dashboardActivityService.logActivity(1L, "LOGIN", "User logged in");

        verify(activityLogRepository).save(any(ActivityLog.class));
    }

    @Test
    void getUserRecentActivities_Success() {
        List<ActivityLog> activities = Arrays.asList(testActivity);
        when(activityLogRepository.findTop10ByUserIdOrderByCreatedAtDesc(anyLong())).thenReturn(activities);

        List<ActivityLog> result = dashboardActivityService.getUserRecentActivities(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(activityLogRepository).findTop10ByUserIdOrderByCreatedAtDesc(1L);
    }

    @Test
    void getAllUserActivities_Success() {
        List<ActivityLog> activities = Arrays.asList(testActivity);
        when(activityLogRepository.findByUserIdOrderByCreatedAtDesc(anyLong())).thenReturn(activities);

        List<ActivityLog> result = dashboardActivityService.getAllUserActivities(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(activityLogRepository).findByUserIdOrderByCreatedAtDesc(1L);
    }
}
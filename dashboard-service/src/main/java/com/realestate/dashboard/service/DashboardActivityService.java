package com.realestate.dashboard.service;

import com.realestate.dashboard.entity.ActivityLog;
import com.realestate.dashboard.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardActivityService {

    private final ActivityLogRepository activityLogRepository;

    public void logActivity(Long userId, String activityType, String description) {
        ActivityLog activity = new ActivityLog();
        activity.setUserId(userId);
        activity.setActivityType(activityType);
        activity.setActivityDescription(description);
        activityLogRepository.save(activity);
    }

    public List<ActivityLog> getUserRecentActivities(Long userId) {
        return activityLogRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<ActivityLog> getAllUserActivities(Long userId) {
        return activityLogRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
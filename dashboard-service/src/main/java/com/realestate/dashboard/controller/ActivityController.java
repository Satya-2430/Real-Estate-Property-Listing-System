package com.realestate.dashboard.controller;

import com.realestate.dashboard.entity.ActivityLog;
import com.realestate.dashboard.service.DashboardActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/activities")
@RequiredArgsConstructor

public class ActivityController {

    private final DashboardActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityLog> logActivity(@RequestBody ActivityLog activity) {
        activityService.logActivity(
            activity.getUserId(), 
            activity.getActivityType(), 
            activity.getActivityDescription()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityLog>> getUserActivities(@PathVariable Long userId) {
        List<ActivityLog> activities = activityService.getAllUserActivities(userId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<ActivityLog>> getRecentActivities(@PathVariable Long userId) {
        List<ActivityLog> activities = activityService.getUserRecentActivities(userId);
        return ResponseEntity.ok(activities);
    }
}
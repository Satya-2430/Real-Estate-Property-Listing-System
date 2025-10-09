package com.realestate.admin.repository;

import com.realestate.admin.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    List<UserActivity> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<UserActivity> findTop10ByOrderByCreatedAtDesc();
    
    @Query("SELECT ua FROM UserActivity ua WHERE ua.createdAt >= :startDate ORDER BY ua.createdAt DESC")
    List<UserActivity> findRecentActivities(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT ua.activityType, COUNT(ua) FROM UserActivity ua GROUP BY ua.activityType")
    List<Object[]> getActivityTypeStats();
}
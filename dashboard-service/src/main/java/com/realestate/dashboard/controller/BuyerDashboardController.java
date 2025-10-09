package com.realestate.dashboard.controller;

import com.realestate.dashboard.dto.BuyerDashboardResponse;
import com.realestate.dashboard.service.BuyerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard/buyer")
@RequiredArgsConstructor

public class BuyerDashboardController {

    private final BuyerDashboardService buyerDashboardService;

    @GetMapping("/{userId}")
    public ResponseEntity<BuyerDashboardResponse> getBuyerDashboard(
            @PathVariable Long userId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        // Simple JWT validation for testing
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        
        BuyerDashboardResponse dashboard = buyerDashboardService.getBuyerDashboard(userId);
        return ResponseEntity.ok(dashboard);
    }
}
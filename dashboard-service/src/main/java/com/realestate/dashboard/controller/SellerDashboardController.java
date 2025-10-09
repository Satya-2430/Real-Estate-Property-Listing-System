package com.realestate.dashboard.controller;

import com.realestate.dashboard.dto.SellerDashboardResponse;
import com.realestate.dashboard.service.SellerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard/seller")
@RequiredArgsConstructor

public class SellerDashboardController {

    private final SellerDashboardService sellerDashboardService;

    @GetMapping("/{userId}")
    public ResponseEntity<SellerDashboardResponse> getSellerDashboard(@PathVariable Long userId) {
        SellerDashboardResponse dashboard = sellerDashboardService.getSellerDashboard(userId);
        return ResponseEntity.ok(dashboard);
    }
}
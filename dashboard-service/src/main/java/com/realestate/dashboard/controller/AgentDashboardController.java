package com.realestate.dashboard.controller;

import com.realestate.dashboard.dto.AgentDashboardResponse;
import com.realestate.dashboard.service.AgentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard/agent")
@RequiredArgsConstructor

public class AgentDashboardController {

    private final AgentDashboardService agentDashboardService;

    @GetMapping("/{userId}")
    public ResponseEntity<AgentDashboardResponse> getAgentDashboard(@PathVariable Long userId) {
        AgentDashboardResponse dashboard = agentDashboardService.getAgentDashboard(userId);
        return ResponseEntity.ok(dashboard);
    }
}
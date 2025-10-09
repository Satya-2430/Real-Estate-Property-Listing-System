package com.example.dashboard.controller;

import com.example.dashboard.dto.SummaryStats;
import com.example.dashboard.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService service){this.service=service;}

    @GetMapping("/summary")
    public SummaryStats summary(){ return service.getSummary(); }
}

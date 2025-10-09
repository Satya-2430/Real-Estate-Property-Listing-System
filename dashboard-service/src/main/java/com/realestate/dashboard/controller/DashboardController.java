package com.realestate.dashboard.controller;

import com.realestate.dashboard.entity.ActivityLog;
import com.realestate.dashboard.service.DashboardActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardActivityService activityService;




}
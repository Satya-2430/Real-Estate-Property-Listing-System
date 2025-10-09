package com.realestate.admin.service;

import com.realestate.admin.dto.AdminDashboardResponse;
import com.realestate.admin.entity.InquiryStatus;
import com.realestate.admin.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final AdminService adminService;
    private final ContactInquiryRepository contactInquiryRepository;

    public AdminDashboardResponse getAdminDashboard() {
        // Get complete dashboard data from AdminService
        AdminDashboardResponse response = adminService.getDashboard();
        
        // Add contact-specific data
        response.setTotalContacts(contactInquiryRepository.count());
        response.setTotalVisitors(contactInquiryRepository.count());
        response.setTodayVisitors(contactInquiryRepository.countByStatus(InquiryStatus.NEW));
        
        return response;
    }
}
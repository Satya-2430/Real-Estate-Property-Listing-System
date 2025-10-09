package com.realestate.admin.controller;

import com.realestate.admin.dto.ContactInquiryRequest;
import com.realestate.admin.dto.ContactInquiryResponse;
import com.realestate.admin.entity.InquiryStatus;
import com.realestate.admin.service.ContactInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/admin/contact")
@RequiredArgsConstructor

public class ContactInquiryController {

    private final ContactInquiryService contactInquiryService;

    // Public endpoint for visitors to submit inquiries
    @PostMapping("/submit")
    public ResponseEntity<String> submitInquiry(@Valid @RequestBody ContactInquiryRequest request) {
        String message = contactInquiryService.submitInquiry(request);
        return ResponseEntity.ok(message);
    }

    // Admin endpoints
    @GetMapping("/inquiries")
    public ResponseEntity<List<ContactInquiryResponse>> getAllInquiries() {
        List<ContactInquiryResponse> inquiries = contactInquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/inquiries/status/{status}")
    public ResponseEntity<List<ContactInquiryResponse>> getInquiriesByStatus(@PathVariable InquiryStatus status) {
        List<ContactInquiryResponse> inquiries = contactInquiryService.getInquiriesByStatus(status);
        return ResponseEntity.ok(inquiries);
    }

    @PutMapping("/inquiries/{id}/status")
    public ResponseEntity<ContactInquiryResponse> updateInquiryStatus(
            @PathVariable Long id,
            @RequestParam InquiryStatus status,
            @RequestParam(required = false) String adminResponse) {
        ContactInquiryResponse response = contactInquiryService.updateInquiryStatus(id, status, adminResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inquiries/count/new")
    public ResponseEntity<Long> getNewInquiriesCount() {
        long count = contactInquiryService.getNewInquiriesCount();
        return ResponseEntity.ok(count);
    }
    
}